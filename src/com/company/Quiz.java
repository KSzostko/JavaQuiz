package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private final String type;
    private List<Question> questions = new ArrayList<>();

    public Quiz(String type) {
        this.type = type;
    }

    public boolean loadQuestions() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("questions/" + type + ".txt"));

            String[] answers = new String[4];
            String fileLine, questionText = "";
            int lineCount = 0, i = 0, correctAnswer = -1;

            while((fileLine = br.readLine()) != null) {
                if(lineCount % 6 == 0) {
                    if(lineCount != 0) {
                        if(correctAnswer == -1) {
                            System.out.println("Something went wrong");
                            return false;
                        }

                        Question question = new Question(questionText, answers, correctAnswer);
                        questions.add(question);
                        answers = new String[4];
                    }

                    questionText = fileLine;
                } else if(lineCount % 6 == 5) {
                    i = 0;
                    correctAnswer = Integer.parseInt(fileLine);
                } else {
                    answers[i] = fileLine;
                    i++;
                }
                lineCount++;
            }

            if(lineCount != 0) {
                Question question = new Question(questionText, answers, correctAnswer);
                questions.add(question);
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
            String questionString = "\t" + question + "\n";
            result.append(questionString);
        }

        return result.toString();
    }
}
