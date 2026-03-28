package com.dsavisualizer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "problems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String examples;

    @Column(columnDefinition = "TEXT")
    private String constraints;

    @Column(columnDefinition = "TEXT")
    private String pseudocode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(columnDefinition = "TEXT")
    private String boilerplateCode;

    @Column(columnDefinition = "TEXT")
    private String solutionCode;

    @Column(nullable = false)
    @Builder.Default
    private Integer timeLimitMs = 5000;

    @Column(nullable = false)
    @Builder.Default
    private Integer memoryLimitMb = 256;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @Builder.Default
    private Integer submissionsCount = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer acceptedCount = 0;

    public enum Difficulty {
        EASY, MEDIUM, HARD, EXPERT
    }

    public enum Category {
        ARRAYS, STRINGS, LINKED_LIST, TREES, GRAPHS, 
        DYNAMIC_PROGRAMMING, SORTING, SEARCHING, HASHING, GREEDY
    }
}
