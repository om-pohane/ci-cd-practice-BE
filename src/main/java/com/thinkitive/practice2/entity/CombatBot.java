package com.thinkitive.practice2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "combat_bots")
public class CombatBot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Weight class: LIGHTWEIGHT, MIDDLEWEIGHT, HEAVYWEIGHT, TITAN
    private String weightClass;

    // Weapon: PLASMA_SAW, TESLA_COIL, HYDRAULIC_CRUSHER, FLAMETHROWER, RAILGUN, SPINNING_BLADE
    private String weaponType;

    private Integer maxHealth;

    private Integer currentHealth;

    private Integer attack;

    private Integer defense;

    private Integer speed;

    private Integer powerRating;

    private Integer wins = 0;

    private Integer losses = 0;

    private Integer draws = 0;

    // Status: READY, IN_ARENA, DAMAGED, SCRAPPED
    private String status = "READY";

    // Soft delete: true = scrapped into the scrapyard
    private boolean scrapped = false;

    private String avatarColor;

    private LocalDateTime createdAt = LocalDateTime.now();

    public CombatBot() {
    }

    public void recalculatePowerRating() {
        int hp = (maxHealth != null ? maxHealth : 100);
        int atk = (attack != null ? attack : 30);
        int def = (defense != null ? defense : 20);
        int spd = (speed != null ? speed : 25);
        this.powerRating = (hp / 2) + (atk * 2) + def + spd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeightClass() {
        return weightClass;
    }

    public void setWeightClass(String weightClass) {
        this.weightClass = weightClass;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public Integer getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(Integer maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Integer getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(Integer currentHealth) {
        this.currentHealth = currentHealth;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getPowerRating() {
        return powerRating;
    }

    public void setPowerRating(Integer powerRating) {
        this.powerRating = powerRating;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getDraws() {
        return draws;
    }

    public void setDraws(Integer draws) {
        this.draws = draws;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isScrapped() {
        return scrapped;
    }

    public void setScrapped(boolean scrapped) {
        this.scrapped = scrapped;
    }

    public String getAvatarColor() {
        return avatarColor;
    }

    public void setAvatarColor(String avatarColor) {
        this.avatarColor = avatarColor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
