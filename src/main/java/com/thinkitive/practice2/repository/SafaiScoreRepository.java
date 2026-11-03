package com.thinkitive.practice2.repository;

import com.thinkitive.practice2.entity.SafaiScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SafaiScoreRepository extends JpaRepository<SafaiScore, Long> {
    List<SafaiScore> findTop20ByOrderByScoreDesc();
    List<SafaiScore> findByWishCodeOrderByScoreDesc(String wishCode);
}
