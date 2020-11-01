package com.company;

public class Score {
    private final String username;
    private final String quizType;
    private final int points;

    public Score(String username, String quizType, int points) {
        this.username = username;
        this.quizType = quizType;
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public String getQuizType() {
        return quizType;
    }

    public int getPoints() {
        return points;
    }
}
