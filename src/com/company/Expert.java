package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Expert {
    private final List<String> expertAnswers = new ArrayList<>();

    public Expert() {
        populateAnswers();
    }

    private void populateAnswers() {
        expertAnswers.add("This is just trivial! It is of course ");
        expertAnswers.add("This must be ");
        expertAnswers.add("Why do you bother me with such an easy question? Everybody knows that the answer is ");
        expertAnswers.add("Oh man this is a tough one. I guess the most sensible seems ");
        expertAnswers.add("I'm sure the right answer is ");
        expertAnswers.add("Honestly I have no idea. If I were you, I would try ");
        expertAnswers.add("I can't quite remember it, but I would go with ");
        expertAnswers.add("If you were present on the last lecture, you would surely know that the right answer is ");
        expertAnswers.add("I bet the right answer is ");
        expertAnswers.add("I am no expert in this area, but I would risk with the answer ");
    }

    public String chooseExpertAnswer() {
        Random random = new Random();
        int answerId = random.nextInt(expertAnswers.size());

        return expertAnswers.get(answerId);
    }
}
