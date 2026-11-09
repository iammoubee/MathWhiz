package com.mathwhiz.service;

import com.mathwhiz.model.GameQuestion;
import com.mathwhiz.model.GameResult;
import com.mathwhiz.model.QuestionResult;
import com.mathwhiz.model.QuestionType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GameService {
    private static final double ANSWER_TOLERANCE = 0.001;
    private static final int[] ANGLES = {
            -60, -45, -30, 0, 30, 45, 60, 120, 135, 150,
            180, 210, 225, 240, 300, 315, 330
    };
    private static final String[] TRIG_FUNCTIONS = {"SIN", "COS", "TAN"};

    public List<GameQuestion> createQuestions() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int primeNumber = random.nextInt(0, 101);
        int dividend = random.nextInt(10, 100);
        int divisor = random.nextInt(1, 13);

        List<GameQuestion> questions = new ArrayList<>(4);
        questions.add(new GameQuestion(
                1, QuestionType.PRIME,
                "Is " + primeNumber + " a prime number? Enter 1 for yes or 0 for no.",
                2, primeNumber, 0, null, 0));
        questions.add(new GameQuestion(
                2, QuestionType.MODULUS,
                "What is the remainder when " + dividend + " is divided by " + divisor + "?",
                2, dividend, divisor, null, 0));
        questions.add(createTrigonometryQuestion(random, 3));
        questions.add(createTrigonometryQuestion(random, 4));
        return List.copyOf(questions);
    }

    public GameResult evaluate(String playerName, List<GameQuestion> questions, Map<String, String> submitted)
            throws AnswerValidationException {
        Map<Integer, String> errors = new LinkedHashMap<>();
        List<QuestionResult> results = new ArrayList<>(questions.size());
        for (GameQuestion question : questions) {
            int questionNumber = question.getNumber();
            String rawAnswer = submitted.get("answer" + questionNumber);
            if (rawAnswer == null || rawAnswer.isBlank()) {
                errors.put(questionNumber, "Enter an answer for this question.");
                continue;
            }

            String answer = rawAnswer.trim();
            try {
                results.add(evaluateQuestion(question, answer));
            } catch (IllegalArgumentException exception) {
                errors.put(questionNumber, exception.getMessage());
            }
        }
        if (!errors.isEmpty()) {
            throw new AnswerValidationException(errors);
        }
        return new GameResult(playerName, results);
    }

    private QuestionResult evaluateQuestion(GameQuestion question, String answer) {
        return switch (question.getType()) {
            case PRIME -> evaluateIntegerQuestion(question, answer, MathService.isPrime(question.getFirstOperand()));
            case MODULUS -> evaluateIntegerQuestion(
                    question, answer,
                    MathService.calculateMod(question.getFirstOperand(), question.getSecondOperand()));
            case TRIGONOMETRY -> evaluateTrigonometryQuestion(question, answer);
        };
    }

    private QuestionResult evaluateIntegerQuestion(GameQuestion question, String answer, int expected) {
        final int submitted;
        try {
            submitted = Integer.parseInt(answer);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Enter a whole number.");
        }
        if (question.getType() == QuestionType.PRIME && submitted != 0 && submitted != 1) {
            throw new IllegalArgumentException("Enter 1 for prime or 0 for not prime.");
        }
        boolean correct = submitted == expected;
        return new QuestionResult(
                question, answer, Integer.toString(expected), correct, correct ? question.getMarks() : 0);
    }

    private QuestionResult evaluateTrigonometryQuestion(GameQuestion question, String answer) {
        final double submitted;
        try {
            submitted = Double.parseDouble(answer);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Enter a valid decimal number.");
        }
        if (!Double.isFinite(submitted)) {
            throw new IllegalArgumentException("Enter a finite decimal number.");
        }
        double expected = switch (question.getTrigFunction()) {
            case "SIN" -> MathService.calculateSin(question.getAngle());
            case "COS" -> MathService.calculateCos(question.getAngle());
            case "TAN" -> MathService.calculateTan(question.getAngle());
            default -> throw new IllegalStateException("Unsupported trigonometric function.");
        };
        boolean correct = Math.abs(submitted - expected) <= ANSWER_TOLERANCE;
        return new QuestionResult(
                question,
                answer,
                String.format(Locale.ROOT, "%.3f", expected),
                correct,
                correct ? question.getMarks() : 0);
    }

    private GameQuestion createTrigonometryQuestion(ThreadLocalRandom random, int number) {
        String function = TRIG_FUNCTIONS[random.nextInt(TRIG_FUNCTIONS.length)];
        int angle = ANGLES[random.nextInt(ANGLES.length)];
        return new GameQuestion(
                number,
                QuestionType.TRIGONOMETRY,
                function + "(" + angle + " degrees)",
                3,
                0,
                0,
                function,
                angle);
    }
}
