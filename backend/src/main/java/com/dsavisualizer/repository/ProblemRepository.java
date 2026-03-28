package com.dsavisualizer.repository;

import com.dsavisualizer.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByCategory(Problem.Category category);
    List<Problem> findByDifficulty(Problem.Difficulty difficulty);
    List<Problem> findByCategoryAndDifficulty(Problem.Category category, Problem.Difficulty difficulty);
    
    @Query("SELECT p FROM Problem p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Problem> searchProblems(String search);
}
