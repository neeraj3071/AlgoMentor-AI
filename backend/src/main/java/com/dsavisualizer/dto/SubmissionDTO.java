package com.dsavisualizer.dto;

import com.dsavisualizer.model.Submission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionDTO {
    private Long id;
    private Long problemId;
    private Submission.Status status;
    private String output;
    private String errorMessage;
    private Long executionTimeMs;
    private Long memoryUsedMb;
    private LocalDateTime submittedAt;
}
