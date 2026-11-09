package com.mathwhiz.controller;

import com.mathwhiz.dao.ScoreDAO;
import com.mathwhiz.model.GameQuestion;
import com.mathwhiz.model.GameResult;
import com.mathwhiz.model.GameScore;
import com.mathwhiz.service.AnswerValidationException;
import com.mathwhiz.service.GameService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/game")
public class GameServlet extends HttpServlet {
    private static final String PLAYER_NAME = "playerName";
    private static final String QUESTIONS = "gameQuestions";
    private static final String RESULT = "gameResult";
    private static final String VALIDATION_ERRORS = "validationErrors";
    private static final int MAX_PLAYER_NAME_LENGTH = 100;

    private final GameService gameService = new GameService();
    private final ScoreDAO scoreDAO = new ScoreDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store");
        String action = request.getParameter("action");
        if ("play".equals(action)) {
            showGame(request, response);
        } else if ("result".equals(action)) {
            showResult(request, response);
        } else if ("leaderboard".equals(action)) {
            showLeaderboard(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        String action = request.getParameter("action");
        if ("start".equals(action)) {
            startGame(request, response);
        } else if ("submit".equals(action)) {
            submitAnswers(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    private void startGame(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String playerName = request.getParameter(PLAYER_NAME);
        if (playerName == null || playerName.isBlank()) {
            request.setAttribute("nameError", "Please enter your name to start.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        playerName = playerName.trim();
        if (playerName.length() > MAX_PLAYER_NAME_LENGTH) {
            request.setAttribute("nameError", "Your name must be 100 characters or fewer.");
            request.setAttribute(PLAYER_NAME, playerName);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        request.changeSessionId();
        session.removeAttribute(RESULT);
        session.setAttribute(PLAYER_NAME, playerName);
        session.setAttribute(QUESTIONS, gameService.createQuestions());
        response.sendRedirect(request.getContextPath() + "/game?action=play");
    }

    @SuppressWarnings("unchecked")
    private void showGame(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(QUESTIONS) == null) {
            if (session != null && session.getAttribute(RESULT) != null) {
                response.sendRedirect(request.getContextPath() + "/game?action=result");
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
            return;
        }
        request.setAttribute(PLAYER_NAME, session.getAttribute(PLAYER_NAME));
        request.setAttribute(QUESTIONS, session.getAttribute(QUESTIONS));
        Object errors = session.getAttribute(VALIDATION_ERRORS);
        if (errors instanceof Map<?, ?>) {
            request.setAttribute(VALIDATION_ERRORS, errors);
            session.removeAttribute(VALIDATION_ERRORS);
        }
        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }

    @SuppressWarnings("unchecked")
    private void submitAnswers(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        synchronized (session) {
            Object existingResult = session.getAttribute(RESULT);
            if (existingResult != null) {
                response.sendRedirect(request.getContextPath() + "/game?action=result");
                return;
            }
            Object storedQuestions = session.getAttribute(QUESTIONS);
            Object storedName = session.getAttribute(PLAYER_NAME);
            if (!(storedQuestions instanceof List<?>) || !(storedName instanceof String)) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }

            List<GameQuestion> questions = (List<GameQuestion>) storedQuestions;
            try {
                GameResult result = gameService.evaluate(
                        (String) storedName, questions, request.getParameterMap().entrySet().stream()
                                .collect(java.util.stream.Collectors.toMap(
                                        Map.Entry::getKey,
                                        entry -> entry.getValue().length == 0 ? "" : entry.getValue()[0])));
                session.setAttribute(RESULT, result);
                session.removeAttribute(QUESTIONS);
                try {
                    scoreDAO.saveScore(new GameScore(
                            0, result.getPlayerName(), result.getScore(), "NORMAL", null));
                    result.setSaved(true);
                    result.setSaveMessage("Your score has been saved to the leaderboard.");
                } catch (SQLException exception) {
                    getServletContext().log("Unable to save MathWhiz score.", exception);
                    result.setSaveMessage(
                            "Your result was calculated, but it could not be saved. Check the database configuration and try again next time.");
                }
                response.sendRedirect(request.getContextPath() + "/game?action=result");
            } catch (AnswerValidationException exception) {
                session.setAttribute(VALIDATION_ERRORS, exception.getErrors());
                response.sendRedirect(request.getContextPath() + "/game?action=play");
            }
        }
    }

    private void showResult(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !(session.getAttribute(RESULT) instanceof GameResult)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        request.setAttribute(RESULT, session.getAttribute(RESULT));
        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

    private void showLeaderboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("scores", scoreDAO.getTop10Scores());
        } catch (SQLException exception) {
            getServletContext().log("Unable to load MathWhiz leaderboard.", exception);
            request.setAttribute(
                    "databaseError",
                    "The leaderboard is temporarily unavailable. Check that MySQL is running and configured.");
        }
        request.getRequestDispatcher("/leaderboard.jsp").forward(request, response);
    }
}
