package com.mathwhiz.dao;

import com.mathwhiz.model.GameScore;
import com.mathwhiz.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScoreDAO {
    private static final String INSERT_SCORE = """
            INSERT INTO game_scores (player_name, score, difficulty)
            VALUES (?, ?, ?)
            """;
    private static final String TOP_TEN = """
            SELECT id, player_name, score, difficulty, played_at
            FROM game_scores
            ORDER BY score DESC, played_at ASC
            LIMIT 10
            """;

    public void saveScore(GameScore score) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SCORE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, score.getPlayerName());
            statement.setInt(2, score.getScore());
            statement.setString(3, "NORMAL");
            statement.executeUpdate();
        }
    }

    public List<GameScore> getTop10Scores() throws SQLException {
        List<GameScore> scores = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(TOP_TEN);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                scores.add(new GameScore(
                        resultSet.getLong("id"),
                        resultSet.getString("player_name"),
                        resultSet.getInt("score"),
                        resultSet.getString("difficulty"),
                        resultSet.getTimestamp("played_at")));
            }
        }
        return scores;
    }
}
