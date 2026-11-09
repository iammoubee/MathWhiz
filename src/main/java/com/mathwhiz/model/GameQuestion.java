package com.mathwhiz.model;

public class GameQuestion {
    private final int number;
    private final QuestionType type;
    private final String prompt;
    private final int marks;
    private final int firstOperand;
    private final int secondOperand;
    private final String trigFunction;
    private final double angle;

    public GameQuestion(
            int number,
            QuestionType type,
            String prompt,
            int marks,
            int firstOperand,
            int secondOperand,
            String trigFunction,
            double angle) {
        this.number = number;
        this.type = type;
        this.prompt = prompt;
        this.marks = marks;
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.trigFunction = trigFunction;
        this.angle = angle;
    }

    public int getNumber() {
        return number;
    }

    public QuestionType getType() {
        return type;
    }

    public String getTypeName() {
        return type.name();
    }

    public String getPrompt() {
        return prompt;
    }

    public int getMarks() {
        return marks;
    }

    public int getFirstOperand() {
        return firstOperand;
    }

    public int getSecondOperand() {
        return secondOperand;
    }

    public String getTrigFunction() {
        return trigFunction;
    }

    public double getAngle() {
        return angle;
    }
}
