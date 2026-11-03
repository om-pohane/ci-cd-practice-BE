package com.thinkitive.practice2.repository;

import com.thinkitive.practice2.entity.DiwaliWish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiwaliWishRepository extends JpaRepository<DiwaliWish, Long> {
    Optional<DiwaliWish> findByWishCode(String wishCode);
}
