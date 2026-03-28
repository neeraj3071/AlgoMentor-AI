package com.dsavisualizer.controller;

import com.dsavisualizer.dto.ProblemDTO;
import com.dsavisualizer.model.Problem;
import com.dsavisualizer.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/problems")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:3000" }, maxAge = 3600)
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping
    public ResponseEntity<List<ProblemDTO>> getAllProblems() {
        List<ProblemDTO> problems = problemService.getAllProblems();
        return ResponseEntity.ok(problems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProblemDTO> getProblemById(@PathVariable Long id) {
        ProblemDTO problem = problemService.getProblemById(id);
        return ResponseEntity.ok(problem);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProblemDTO>> getProblemsByCategory(
            @PathVariable Problem.Category category) {
        List<ProblemDTO> problems = problemService.getProblemsByCategory(category);
        return ResponseEntity.ok(problems);
    }

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<ProblemDTO>> getProblemsByDifficulty(
            @PathVariable Problem.Difficulty difficulty) {
        List<ProblemDTO> problems = problemService.getProblemsByDifficulty(difficulty);
        return ResponseEntity.ok(problems);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProblemDTO>> filterProblems(
            @RequestParam(required = false) Problem.Category category,
            @RequestParam(required = false) Problem.Difficulty difficulty) {
        List<ProblemDTO> problems = problemService.getProblemsByFilter(category, difficulty);
        return ResponseEntity.ok(problems);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProblemDTO>> searchProblems(
            @RequestParam String query) {
        List<ProblemDTO> problems = problemService.searchProblems(query);
        return ResponseEntity.ok(problems);
    }

    @PostMapping
    public ResponseEntity<ProblemDTO> createProblem(@RequestBody Problem problem) {
        ProblemDTO createdProblem = problemService.createProblem(problem);
        return ResponseEntity.ok(createdProblem);
    }
}
