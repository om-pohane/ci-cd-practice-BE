package com.thinkitive.practice2.dto;

public class SubmitScoreRequest {
    private String playerName;
    private String wishCode;
    private Integer score;
    private Integer kajuKatlis;
    private Integer diyas;
    private Integer chappalsDodged;

    public SubmitScoreRequest() {
    }

    public SubmitScoreRequest(String playerName, String wishCode, Integer score, Integer kajuKatlis, Integer diyas, Integer chappalsDodged) {
        this.playerName = playerName;
        this.wishCode = wishCode;
        this.score = score;
        this.kajuKatlis = kajuKatlis;
        this.diyas = diyas;
        this.chappalsDodged = chappalsDodged;
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
}
