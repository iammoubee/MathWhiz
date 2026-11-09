package com.mathwhiz.model;

import java.util.List;

public class GameResult {
    private final String playerName;
    private final List<QuestionResult> questionResults;
    private final int score;
    private boolean saved;
    private String saveMessage;

    public GameResult(String playerName, List<QuestionResult> questionResults) {
        this.playerName = playerName;
        this.questionResults = List.copyOf(questionResults);
        this.score = questionResults.stream().mapToInt(QuestionResult::getEarnedMarks).sum();
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<QuestionResult> getQuestionResults() {
        return questionResults;
    }

    public int getScore() {
        return score;
    }

    public int getMaximumScore() {
        return 10;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public String getSaveMessage() {
        return saveMessage;
    }

    public void setSaveMessage(String saveMessage) {
        this.saveMessage = saveMessage;
    }

    public String getPerformanceMessage() {
        if (score >= 9) {
            return "Outstanding work! You are a MathWhiz.";
        }
        if (score >= 6) {
            return "Great job! Keep your mathematical skills sharp.";
        }
        if (score >= 3) {
            return "Good effort. A little more practice will help.";
        }
        return "Thanks for playing. Try again and beat your score.";
    }
}
