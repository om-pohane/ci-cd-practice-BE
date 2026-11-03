package com.thinkitive.practice2.repository;

import com.thinkitive.practice2.entity.CombatBot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CombatBotRepository extends JpaRepository<CombatBot, Long> {

    List<CombatBot> findByScrappedFalseOrderByIdDesc();

    List<CombatBot> findByScrappedTrueOrderByIdDesc();

    CombatBot findByIdAndScrappedFalse(Long id);

    CombatBot findByIdAndScrappedTrue(Long id);

    List<CombatBot> findByWeightClassAndScrappedFalse(String weightClass);

    List<CombatBot> findByWeaponTypeAndScrappedFalse(String weaponType);

    List<CombatBot> findTop10ByScrappedFalseOrderByWinsDesc();
}
