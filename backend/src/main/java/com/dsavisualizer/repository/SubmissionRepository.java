package com.dsavisualizer.repository;

import com.dsavisualizer.model.Submission;
import com.dsavisualizer.model.Submission.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUserId(Long userId);
    List<Submission> findByUserIdAndProblemId(Long userId, Long problemId);
    List<Submission> findByProblemIdOrderBySubmittedAtDesc(Long problemId);

    long countByUserId(Long userId);

    long countByUserIdAndStatus(Long userId, Status status);

    long countByProblemId(Long problemId);

    long countByProblemIdAndStatus(Long problemId, Status status);

    @Query("SELECT COUNT(DISTINCT s.problem.id) FROM Submission s WHERE s.user.id = :userId AND s.status = :status")
    long countDistinctSolvedProblems(@Param("userId") Long userId, @Param("status") Status status);
}
