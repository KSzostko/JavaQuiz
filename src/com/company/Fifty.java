package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fifty {
    public static String[] chooseRemoved(String[] answers, int correctAnswer) {
        List<String> badAnswers = new ArrayList<>();
        for(int i = 0; i < answers.length; i++) {
            if(i != correctAnswer) badAnswers.add(answers[i]);
        }

        Random random = new Random();

        int removedIndex = random.nextInt(badAnswers.size());
        String firstRemoved = badAnswers.get(removedIndex);
        badAnswers.remove(removedIndex);

        removedIndex = random.nextInt(badAnswers.size());
        String secondRemoved = badAnswers.get(removedIndex);

        return new String[] { firstRemoved, secondRemoved };
    }
}
