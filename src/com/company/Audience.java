package com.company;

import java.util.Random;

public class Audience {
    public static String vote(String[] answers, int correctAnswer) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        // correct answer will have min 20% of votes
        int votes;
        int leftVotes = 80;
        for(int i = 0; i < answers.length; i++) {
            if(i == correctAnswer) {
                votes = 20;
            } else {
                votes = 0;
            }

            int additionalVotes = leftVotes != 0 ? random.nextInt(leftVotes) : 0;
            votes += additionalVotes;
            leftVotes -= additionalVotes;

            char answerChar = (char) (i + 65);
            builder.append(answerChar);
            builder.append(": ");
            builder.append(votes);
            builder.append("% votes\n");
        }

        return builder.toString();
    }
}
