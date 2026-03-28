package com.dsavisualizer.controller;

import com.dsavisualizer.service.AITutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:3000" }, maxAge = 3600)
public class AITutorController {

    @Autowired
    private AITutorService aiTutorService;

    @PostMapping("/explain")
    public ResponseEntity<Map<String, String>> explainCode(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        String explanation = aiTutorService.explainCode(code);

        Map<String, String> response = new HashMap<>();
        response.put("explanation", explanation);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/hint")
    public ResponseEntity<Map<String, String>> getHint(@RequestBody Map<String, String> request) {
        String problemDescription = request.get("problemDescription");
        String hint = aiTutorService.getHint(problemDescription);

        Map<String, String> response = new HashMap<>();
        response.put("hint", hint);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/complexity")
    public ResponseEntity<Map<String, String>> analyzeComplexity(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        String analysis = aiTutorService.analyzeComplexity(code);

        Map<String, String> response = new HashMap<>();
        response.put("analysis", analysis);
        return ResponseEntity.ok(response);
    }
}
