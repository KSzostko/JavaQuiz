package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Quiz {
    private final String type;
    private List<Question> questions = new ArrayList<>();
    private int currentQuestionNumber;

    public Quiz(String type) {
        this.currentQuestionNumber = 0;
        this.type = type;

        if(!loadQuestions()) {
            throw new Error("Couldn't load questions from file");
        }
    }

    public static List<String> getTypes() {
        List<String> files = new ArrayList<>();

        try(Stream<Path> paths = Files.walk(Paths.get("questions"))) {
            paths.forEach(filePath -> {
                if(Files.isRegularFile(filePath)) {
                    String fileString = filePath.toString();
                    fileString = fileString.substring(10).replaceAll(".txt", "");

                    files.add(fileString);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
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

    public void nextQuestion() {
        currentQuestionNumber++;
        if(currentQuestionNumber == questions.size()) {
            currentQuestionNumber = -1;
        }
    }

    public String getType() {
        return type;
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public Question getQuestion() {
        return questions.get(currentQuestionNumber);
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
