package com.dsavisualizer.dto;

import com.dsavisualizer.model.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemDTO {
    private Long id;
    private String title;
    private String description;
    private String examples;
    private String constraints;
    private String pseudocode;
    private Problem.Difficulty difficulty;
    private Problem.Category category;
    private String boilerplateCode;
    private Integer timeLimitMs;
    private Integer memoryLimitMb;
    private Integer submissionsCount;
    private Integer acceptedCount;
    private Double acceptanceRate;
    private LocalDateTime createdAt;
}
