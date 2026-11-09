package com.mathwhiz.service;

import com.mathwhiz.model.GameQuestion;
import com.mathwhiz.model.GameResult;
import com.mathwhiz.model.QuestionType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameServiceTest {
    private final GameService gameService = new GameService();
    private final List<GameQuestion> questions = List.of(
            new GameQuestion(1, QuestionType.PRIME, "prime", 2, 7, 0, null, 0),
            new GameQuestion(2, QuestionType.MODULUS, "mod", 2, 17, 5, null, 0),
            new GameQuestion(3, QuestionType.TRIGONOMETRY, "sin", 3, 0, 0, "SIN", 30),
            new GameQuestion(4, QuestionType.TRIGONOMETRY, "tan", 3, 0, 0, "TAN", 45));

    @Test
    void generatedQuizContainsFourQuestionsAndTenMaximumMarks() {
        List<GameQuestion> generated = gameService.createQuestions();
        assertEquals(4, generated.size());
        assertEquals(10, generated.stream().mapToInt(GameQuestion::getMarks).sum());
        assertTrue(generated.get(1).getSecondOperand() != 0);
    }

    @Test
    void awardsTenPointsForAllCorrectAnswers() throws AnswerValidationException {
        GameResult result = gameService.evaluate(
                "Ada", questions, Map.of("answer1", "1", "answer2", "2", "answer3", "0.5", "answer4", "1"));
        assertEquals(10, result.getScore());
    }

    @Test
    void awardsTwoPointsWhenOnlyPrimeAnswerIsCorrect() throws AnswerValidationException {
        GameResult result = gameService.evaluate(
                "Ada", questions, Map.of("answer1", "1", "answer2", "0", "answer3", "0", "answer4", "0"));
        assertEquals(2, result.getScore());
    }

    @Test
    void awardsFourPointsWhenPrimeAndModulusAnswersAreCorrect() throws AnswerValidationException {
        GameResult result = gameService.evaluate(
                "Ada", questions, Map.of("answer1", "1", "answer2", "2", "answer3", "0", "answer4", "0"));
        assertEquals(4, result.getScore());
    }

    @Test
    void awardsZeroPointsWhenAllAnswersAreWrong() throws AnswerValidationException {
        GameResult result = gameService.evaluate(
                "Ada", questions, Map.of("answer1", "0", "answer2", "0", "answer3", "0", "answer4", "0"));
        assertEquals(0, result.getScore());
    }

    @Test
    void rejectsMissingAndMalformedAnswers() {
        AnswerValidationException exception = assertThrows(
                AnswerValidationException.class,
                () -> gameService.evaluate("Ada", questions, Map.of(
                        "answer1", "5", "answer2", "not-an-integer", "answer3", "NaN", "answer4", "")));
        assertEquals(4, exception.getErrors().size());
        assertTrue(exception.getErrors().containsKey(1));
        assertTrue(exception.getErrors().containsKey(4));
    }
}
