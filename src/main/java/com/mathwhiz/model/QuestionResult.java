package com.mathwhiz.model;

public class QuestionResult {
    private final GameQuestion question;
    private final String submittedAnswer;
    private final String correctAnswer;
    private final boolean correct;
    private final int earnedMarks;

    public QuestionResult(
            GameQuestion question,
            String submittedAnswer,
            String correctAnswer,
            boolean correct,
            int earnedMarks) {
        this.question = question;
        this.submittedAnswer = submittedAnswer;
        this.correctAnswer = correctAnswer;
        this.correct = correct;
        this.earnedMarks = earnedMarks;
    }

    public GameQuestion getQuestion() {
        return question;
    }

    public String getSubmittedAnswer() {
        return submittedAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public int getEarnedMarks() {
        return earnedMarks;
    }
}
