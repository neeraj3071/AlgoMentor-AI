package com.dsavisualizer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeSubmissionRequest {
    @NotNull(message = "Problem id is required")
    private Long problemId;

    @NotBlank(message = "Code is required")
    @Size(min = 10, max = 50000, message = "Code must be between 10 and 50000 characters")
    private String code;

    @NotBlank(message = "Language is required")
    private String language;
}
