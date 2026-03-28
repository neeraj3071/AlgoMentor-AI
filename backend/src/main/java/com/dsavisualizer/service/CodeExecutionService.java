package com.dsavisualizer.service;

import com.dsavisualizer.dto.CodeSubmissionRequest;
import com.dsavisualizer.dto.SubmissionDTO;
import com.dsavisualizer.exception.ResourceNotFoundException;
import com.dsavisualizer.model.Problem;
import com.dsavisualizer.model.Submission;
import com.dsavisualizer.model.Submission.Status;
import com.dsavisualizer.model.User;
import com.dsavisualizer.repository.ProblemRepository;
import com.dsavisualizer.repository.SubmissionRepository;
import com.dsavisualizer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.UUID;

@Service
public class CodeExecutionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProblemRepository problemRepository;

    public SubmissionDTO executeCode(String username, CodeSubmissionRequest request) {
        if (!"java".equalsIgnoreCase(request.getLanguage())) {
            throw new IllegalArgumentException("Only Java is currently supported for code execution");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        Problem problem = problemRepository.findById(request.getProblemId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Problem not found with id: " + request.getProblemId()));

        Submission submission = new Submission();
        submission.setUser(user);
        submission.setProblem(problem);
        submission.setCode(request.getCode());
        submission.setStatus(Status.PENDING);

        try {
            // Create temporary directory for code
            String tempDir = System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID();
            Files.createDirectories(Paths.get(tempDir));

            // Write Java file
            String className = "Solution";
            String javaFile = tempDir + File.separator + className + ".java";
            String normalizedCode = normalizeJavaSource(request.getCode(), className);
            Files.write(Paths.get(javaFile), normalizedCode.getBytes(StandardCharsets.UTF_8));

            // Compile
            ProcessBuilder compileBuilder = new ProcessBuilder("javac", javaFile);
            compileBuilder.directory(new File(tempDir));
            Process compileProcess = compileBuilder.start();
            compileProcess.waitFor();

            if (compileProcess.exitValue() != 0) {
                BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(compileProcess.getErrorStream()));
                StringBuilder error = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    error.append(line).append("\n");
                }
                submission.setStatus(Status.COMPILATION_ERROR);
                submission.setErrorMessage(error.toString());
                return persistSubmissionWithStats(submission);
            }

            if (!containsMainMethod(normalizedCode)) {
                submission.setStatus(Status.ACCEPTED);
                submission.setOutput("Compiled successfully");
                submission.setExecutionTimeMs(0L);
                cleanupDirectory(tempDir);
                return persistSubmissionWithStats(submission);
            }

            // Execute when main method is present
            long startTime = System.currentTimeMillis();
            ProcessBuilder runBuilder = new ProcessBuilder("java", "-cp", tempDir, className);
            runBuilder.directory(new File(tempDir));
            Process runProcess = runBuilder.start();

            boolean completed = runProcess.waitFor(5, java.util.concurrent.TimeUnit.SECONDS);
            long executionTime = System.currentTimeMillis() - startTime;

            if (!completed) {
                runProcess.destroy();
                submission.setStatus(Status.TIME_LIMIT_EXCEEDED);
                submission.setErrorMessage("Execution time exceeded 5 seconds");
            } else if (runProcess.exitValue() == 0) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(runProcess.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                submission.setStatus(Status.ACCEPTED);
                submission.setOutput(output.toString());
                submission.setExecutionTimeMs(executionTime);
            } else {
                BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(runProcess.getErrorStream()));
                StringBuilder error = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    error.append(line).append("\n");
                }
                submission.setStatus(Status.RUNTIME_ERROR);
                submission.setErrorMessage(error.toString());
            }

            // Cleanup
            cleanupDirectory(tempDir);

        } catch (IOException e) {
            submission.setStatus(Status.RUNTIME_ERROR);
            submission.setErrorMessage(e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            submission.setStatus(Status.RUNTIME_ERROR);
            submission.setErrorMessage("Execution was interrupted");
        }

        return persistSubmissionWithStats(submission);
    }

    private SubmissionDTO persistSubmissionWithStats(Submission submission) {
        Submission savedSubmission = submissionRepository.save(submission);
        submissionRepository.flush();
        recalculateUserStats(savedSubmission.getUser());
        recalculateProblemStats(savedSubmission.getProblem());
        return convertToDTO(savedSubmission);
    }

    private void recalculateUserStats(User user) {
        long totalSubmissions = submissionRepository.countByUserId(user.getId());
        long acceptedCount = submissionRepository.countByUserIdAndStatus(user.getId(), Status.ACCEPTED);
        long solvedCount = submissionRepository.countDistinctSolvedProblems(user.getId(), Status.ACCEPTED);

        double accuracy = totalSubmissions == 0
            ? 0.0
            : ((double) acceptedCount / totalSubmissions) * 100.0;

        user.setProblemsSolved((int) solvedCount);
        user.setAverageAccuracy(accuracy);
        userRepository.save(user);
    }

    private void recalculateProblemStats(Problem problem) {
        long totalSubmissions = submissionRepository.countByProblemId(problem.getId());
        long acceptedCount = submissionRepository.countByProblemIdAndStatus(problem.getId(), Status.ACCEPTED);

        problem.setSubmissionsCount((int) totalSubmissions);
        problem.setAcceptedCount((int) acceptedCount);
        problemRepository.save(problem);
    }

    private String normalizeJavaSource(String code, String className) {
        String source = code == null ? "" : code.trim();
        if (source.isEmpty()) {
            return "public class " + className + " { }";
        }

        if (source.matches("(?s).*\\bclass\\s+" + className + "\\b.*")) {
            return source;
        }

        if (source.matches("(?s).*\\bclass\\s+\\w+\\b.*")) {
            return source;
        }

        String packageLine = "";
        StringBuilder importLines = new StringBuilder();
        StringBuilder body = new StringBuilder();
        boolean encounteredBody = false;

        String[] lines = source.split("\\R");
        for (String line : lines) {
            String trimmed = line.trim();

            if (!encounteredBody && packageLine.isEmpty() && trimmed.startsWith("package ") && trimmed.endsWith(";")) {
                packageLine = trimmed;
                continue;
            }

            if (!encounteredBody && trimmed.startsWith("import ") && trimmed.endsWith(";")) {
                importLines.append(trimmed).append("\n");
                continue;
            }

            encounteredBody = true;
            body.append(line).append("\n");
        }

        StringBuilder wrapped = new StringBuilder();
        if (!packageLine.isEmpty()) {
            wrapped.append(packageLine).append("\n\n");
        }
        if (importLines.length() > 0) {
            wrapped.append(importLines).append("\n");
        }

        wrapped.append("public class ").append(className).append(" {\n");
        if (body.length() > 0) {
            wrapped.append(body);
            if (body.charAt(body.length() - 1) != '\n') {
                wrapped.append('\n');
            }
        }
        wrapped.append("}");
        return wrapped.toString();
    }

    private boolean containsMainMethod(String source) {
        return source != null && source.matches("(?s).*public\\s+static\\s+void\\s+main\\s*\\(\\s*String\\s*\\[\\]\\s*\\w+\\s*\\).*" );
    }

    private SubmissionDTO convertToDTO(Submission submission) {
        return SubmissionDTO.builder()
                .id(submission.getId())
                .problemId(submission.getProblem() != null ? submission.getProblem().getId() : null)
                .status(submission.getStatus())
                .output(submission.getOutput())
                .errorMessage(submission.getErrorMessage())
                .executionTimeMs(submission.getExecutionTimeMs())
                .memoryUsedMb(submission.getMemoryUsedMb())
                .submittedAt(submission.getSubmittedAt())
                .build();
    }

    private void cleanupDirectory(String tempDir) {
        try {
            Files.walk(Paths.get(tempDir))
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException ignored) {
                            // best-effort cleanup
                        }
                    });
        } catch (IOException ignored) {
            // best-effort cleanup
        }
    }
}
