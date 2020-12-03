package com.company;

public class PointsCalculator {
    private int points = 0;

    public int getPoints() {
        return points;
    }

    public void resetPoints() {
        points = 0;
    }

    public void calculatePoints(int questionNumber, int time) {
        int basePoints = 5 * (questionNumber + 1);
        int bonus = Math.max(basePoints - time, 0);

        int finalPoints = basePoints + bonus;
        points += finalPoints;
    }
}
