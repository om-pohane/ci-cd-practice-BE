package com.thinkitive.practice2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "diwali_wishes")
public class DiwaliWish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String wishCode;

    @Column(nullable = false, length = 100)
    private String fromName;

    @Column(nullable = false, length = 100)
    private String toName;

    @Column(length = 1000)
    private String customMessage;

    @Column(nullable = false)
    private Integer targetScore = 1000;

    @Column(nullable = false)
    private Integer viewsCount = 0;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public DiwaliWish() {
    }

    public DiwaliWish(String wishCode, String fromName, String toName, String customMessage, Integer targetScore) {
        this.wishCode = wishCode;
        this.fromName = fromName;
        this.toName = toName;
        this.customMessage = customMessage;
        this.targetScore = targetScore != null ? targetScore : 1000;
        this.viewsCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWishCode() {
        return wishCode;
    }

    public void setWishCode(String wishCode) {
        this.wishCode = wishCode;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public Integer getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(Integer targetScore) {
        this.targetScore = targetScore;
    }

    public Integer getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Integer viewsCount) {
        this.viewsCount = viewsCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
