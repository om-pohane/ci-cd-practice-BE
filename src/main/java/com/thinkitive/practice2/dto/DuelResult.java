package com.thinkitive.practice2.dto;

import com.thinkitive.practice2.entity.CombatBot;
import java.util.List;

public class DuelResult {
    private CombatBot winner;
    private CombatBot loser;
    private boolean isDraw;
    private int totalRounds;
    private String arenaEnvironment;
    private List<BattleRoundLog> combatLog;
    private String victoryQuote;

    public DuelResult() {
    }

    public CombatBot getWinner() {
        return winner;
    }

    public void setWinner(CombatBot winner) {
        this.winner = winner;
    }

    public CombatBot getLoser() {
        return loser;
    }

    public void setLoser(CombatBot loser) {
        this.loser = loser;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }

    public String getArenaEnvironment() {
        return arenaEnvironment;
    }

    public void setArenaEnvironment(String arenaEnvironment) {
        this.arenaEnvironment = arenaEnvironment;
    }

    public List<BattleRoundLog> getCombatLog() {
        return combatLog;
    }

    public void setCombatLog(List<BattleRoundLog> combatLog) {
        this.combatLog = combatLog;
    }

    public String getVictoryQuote() {
        return victoryQuote;
    }

    public void setVictoryQuote(String victoryQuote) {
        this.victoryQuote = victoryQuote;
    }
}
