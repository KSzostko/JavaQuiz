package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private final String type;
    private List<Question> questions = new ArrayList<>();

    public Quiz(String type) {
        this.type = type;
        // question list will be retrieved from a file
    }

    public boolean loadQuestions() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("questions/" + type + ".txt"));

            String fileLine;
            while((fileLine = br.readLine()) != null) {
                System.out.println(fileLine);
                // @TODO how to input questions in files
            }

            return true;
        } catch(IOException e) {
            System.out.println("Reading from file error!");
            e.printStackTrace();
            return false;
        }
    }

    // mostly for testing for now
    public void addQuestion(Question question) {
        questions.add(question);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        String typeString = "Quiz Type: " + type + "\nQuestions:\n";
        result.append(typeString);

        for(Question question : questions) {
            String questionString = "- " + question.getQuestionText() + "\n";
            result.append(questionString);
        }

        return result.toString();
    }
}
