package com.dsavisualizer.controller;

import com.dsavisualizer.dto.CodeSubmissionRequest;
import com.dsavisualizer.dto.SubmissionDTO;
import com.dsavisualizer.service.CodeExecutionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/execute")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:5174", "http://localhost:3000" }, maxAge = 3600)
public class CodeExecutionController {

    @Autowired
    private CodeExecutionService codeExecutionService;

    @PostMapping
    public ResponseEntity<SubmissionDTO> executeCode(
            Authentication authentication,
            @Valid @RequestBody CodeSubmissionRequest request) {
        SubmissionDTO result = codeExecutionService.executeCode(authentication.getName(), request);
        return ResponseEntity.ok(result);
    }
}
