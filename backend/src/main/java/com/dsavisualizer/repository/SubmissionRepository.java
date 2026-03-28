package com.dsavisualizer.repository;

import com.dsavisualizer.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUserId(Long userId);
    List<Submission> findByUserIdAndProblemId(Long userId, Long problemId);
    List<Submission> findByProblemIdOrderBySubmittedAtDesc(Long problemId);
}
