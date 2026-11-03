package com.thinkitive.practice2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "safai_scores")
public class SafaiScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String playerName;

    @Column(length = 64)
    private String wishCode;

    @Column(nullable = false)
    private Integer score = 0;

    @Column(nullable = false)
    private Integer kajuKatlis = 0;

    @Column(nullable = false)
    private Integer diyas = 0;

    @Column(nullable = false)
    private Integer chappalsDodged = 0;

    @Column(length = 100)
    private String rankTitle;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public SafaiScore() {
    }

    public SafaiScore(String playerName, String wishCode, Integer score, Integer kajuKatlis, Integer diyas, Integer chappalsDodged, String rankTitle) {
        this.playerName = playerName;
        this.wishCode = wishCode;
        this.score = score != null ? score : 0;
        this.kajuKatlis = kajuKatlis != null ? kajuKatlis : 0;
        this.diyas = diyas != null ? diyas : 0;
        this.chappalsDodged = chappalsDodged != null ? chappalsDodged : 0;
        this.rankTitle = rankTitle;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getWishCode() {
        return wishCode;
    }

    public void setWishCode(String wishCode) {
        this.wishCode = wishCode;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getKajuKatlis() {
        return kajuKatlis;
    }

    public void setKajuKatlis(Integer kajuKatlis) {
        this.kajuKatlis = kajuKatlis;
    }

    public Integer getDiyas() {
        return diyas;
    }

    public void setDiyas(Integer diyas) {
        this.diyas = diyas;
    }

    public Integer getChappalsDodged() {
        return chappalsDodged;
    }

    public void setChappalsDodged(Integer chappalsDodged) {
        this.chappalsDodged = chappalsDodged;
    }

    public String getRankTitle() {
        return rankTitle;
    }

    public void setRankTitle(String rankTitle) {
        this.rankTitle = rankTitle;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
