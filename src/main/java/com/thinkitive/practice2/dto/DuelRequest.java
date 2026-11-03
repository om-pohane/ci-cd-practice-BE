package com.thinkitive.practice2.dto;

public class DuelRequest {
    private Long bot1Id;
    private Long bot2Id;
    private String arenaEnvironment = "NEON_CAGE";

    public DuelRequest() {
    }

    public Long getBot1Id() {
        return bot1Id;
    }

    public void setBot1Id(Long bot1Id) {
        this.bot1Id = bot1Id;
    }

    public Long getBot2Id() {
        return bot2Id;
    }

    public void setBot2Id(Long bot2Id) {
        this.bot2Id = bot2Id;
    }

    public String getArenaEnvironment() {
        return arenaEnvironment;
    }

    public void setArenaEnvironment(String arenaEnvironment) {
        this.arenaEnvironment = arenaEnvironment;
    }
}
