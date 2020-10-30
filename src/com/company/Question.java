package com.company;

public class Question {
    private String questionText;
    private String[] answers;
    private int correctAnswer;

    public Question(String questionText, String[] answers, int correctAnswer) {
        this.questionText = questionText;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }
}
