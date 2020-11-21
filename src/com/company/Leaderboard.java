package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Leaderboard {
    private List<Score> ranking = new ArrayList<>();

    public Leaderboard() {
        loadRanking();
    }

    private void loadRanking() {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("ranking/ranking.txt"));

            String fileLine, username = "", quizType = "";
            int points = -1, lineCount = 0;

            while((fileLine = br.readLine()) != null) {
                if(lineCount % 3 == 0) {
                    if(lineCount != 0) {
                        if(points == -1) {
                            throw new Error("Something is wrong with the data order");
                        }

                        Score score = new Score(username, quizType, points);
                        ranking.add(score);
                    }

                    username = fileLine;
                } else if(lineCount % 3 == 1) {
                    quizType = fileLine;
                } else {
                    points = Integer.parseInt(fileLine);
                }
                lineCount++;
            }
            Score score = new Score(username, quizType, points);
            ranking.add(score);
            br.close();

        } catch(IOException e) {
            e.printStackTrace();
            throw new Error("Ranking could not be loaded");
        }
    }

    public void addScore(Score score) {
        ranking.add(score);
        Collections.sort(ranking, new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return Integer.compare(o2.getPoints(), o1.getPoints());
            }
        });

        if (!updateFile()) {
            throw new Error("File update was not succesfull");
        }
    }

    private boolean updateFile() {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter("ranking/ranking.txt"));

            for(Score score : ranking) {
                writer.append(score.getUsername());
                writer.append("\n");
                writer.append(score.getQuizType());
                writer.append("\n");
                writer.append(Integer.toString(score.getPoints()));
                writer.append("\n");
            }
            writer.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Score> getRanking() {
        return ranking;
    }

    public List<Score> getTop5() {
        List<Score> top5 = new ArrayList<>();
        int size = Integer.min(5, ranking.size());

        for(int i = 0; i < size; i++) {
            top5.add(ranking.get(i));
        }

        return top5;
    }
}
