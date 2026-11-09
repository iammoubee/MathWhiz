package com.mathwhiz.model;

import java.sql.Timestamp;

public class GameScore {
    private final long id;
    private final String playerName;
    private final int score;
    private final String difficulty;
    private final Timestamp playedAt;

    public GameScore(long id, String playerName, int score, String difficulty, Timestamp playedAt) {
        this.id = id;
        this.playerName = playerName;
        this.score = score;
        this.difficulty = difficulty;
        this.playedAt = playedAt;
    }

    public long getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Timestamp getPlayedAt() {
        return playedAt;
    }
}
