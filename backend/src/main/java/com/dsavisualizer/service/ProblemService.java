package com.dsavisualizer.service;

import com.dsavisualizer.dto.ProblemDTO;
import com.dsavisualizer.exception.ResourceNotFoundException;
import com.dsavisualizer.model.Problem;
import com.dsavisualizer.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    public List<ProblemDTO> getAllProblems() {
        return problemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProblemDTO getProblemById(Long id) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Problem not found with id: " + id));
        return convertToDTO(problem);
    }

    public List<ProblemDTO> getProblemsByCategory(Problem.Category category) {
        return problemRepository.findByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProblemDTO> getProblemsByDifficulty(Problem.Difficulty difficulty) {
        return problemRepository.findByDifficulty(difficulty).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProblemDTO> getProblemsByFilter(Problem.Category category, Problem.Difficulty difficulty) {
        List<Problem> problems;

        if (category == null && difficulty == null) {
            problems = problemRepository.findAll();
        } else if (category != null && difficulty != null) {
            problems = problemRepository.findByCategoryAndDifficulty(category, difficulty);
        } else if (category != null) {
            problems = problemRepository.findByCategory(category);
        } else {
            problems = problemRepository.findByDifficulty(difficulty);
        }

        return problems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProblemDTO> searchProblems(String query) {
        return problemRepository.searchProblems(query).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProblemDTO createProblem(Problem problem) {
        Problem savedProblem = problemRepository.save(problem);
        return convertToDTO(savedProblem);
    }

    public ProblemDTO updateProblem(Long id, Problem problemDetails) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Problem not found with id: " + id));

        if (problemDetails.getTitle() != null) {
            problem.setTitle(problemDetails.getTitle());
        }
        if (problemDetails.getDescription() != null) {
            problem.setDescription(problemDetails.getDescription());
        }

        Problem updatedProblem = problemRepository.save(problem);
        return convertToDTO(updatedProblem);
    }

    private ProblemDTO convertToDTO(Problem problem) {
        double acceptanceRate = problem.getSubmissionsCount() > 0
                ? (double) problem.getAcceptedCount() / problem.getSubmissionsCount() * 100
                : 0;

        return ProblemDTO.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .description(problem.getDescription())
                .examples(problem.getExamples())
                .constraints(problem.getConstraints())
                .pseudocode(problem.getPseudocode())
                .difficulty(problem.getDifficulty())
                .category(problem.getCategory())
                .boilerplateCode(problem.getBoilerplateCode())
                .timeLimitMs(problem.getTimeLimitMs())
                .memoryLimitMb(problem.getMemoryLimitMb())
                .submissionsCount(problem.getSubmissionsCount())
                .acceptedCount(problem.getAcceptedCount())
                .acceptanceRate(acceptanceRate)
                .createdAt(problem.getCreatedAt())
                .build();
    }
}
