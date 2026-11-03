package com.thinkitive.practice2.dto;

public class BattleRoundLog {
    private int round;
    private Long attackerId;
    private String attackerName;
    private Long defenderId;
    private String defenderName;
    private int damageDealt;
    private boolean isCritical;
    private boolean isDodge;
    private int defenderHpRemaining;
    private String commentary;

    public BattleRoundLog() {
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Long getAttackerId() {
        return attackerId;
    }

    public void setAttackerId(Long attackerId) {
        this.attackerId = attackerId;
    }

    public String getAttackerName() {
        return attackerName;
    }

    public void setAttackerName(String attackerName) {
        this.attackerName = attackerName;
    }

    public Long getDefenderId() {
        return defenderId;
    }

    public void setDefenderId(Long defenderId) {
        this.defenderId = defenderId;
    }

    public String getDefenderName() {
        return defenderName;
    }

    public void setDefenderName(String defenderName) {
        this.defenderName = defenderName;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }

    public boolean isDodge() {
        return isDodge;
    }

    public void setDodge(boolean dodge) {
        isDodge = dodge;
    }

    public int getDefenderHpRemaining() {
        return defenderHpRemaining;
    }

    public void setDefenderHpRemaining(int defenderHpRemaining) {
        this.defenderHpRemaining = defenderHpRemaining;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }
}
