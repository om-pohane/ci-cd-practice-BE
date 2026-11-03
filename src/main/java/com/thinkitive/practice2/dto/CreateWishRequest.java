package com.thinkitive.practice2.dto;

public class CreateWishRequest {
    private String fromName;
    private String toName;
    private String customMessage;
    private Integer targetScore;

    public CreateWishRequest() {
    }

    public CreateWishRequest(String fromName, String toName, String customMessage, Integer targetScore) {
        this.fromName = fromName;
        this.toName = toName;
        this.customMessage = customMessage;
        this.targetScore = targetScore;
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
}
