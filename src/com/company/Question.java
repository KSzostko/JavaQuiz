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

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean checkAnswer(int id) {
        return id == correctAnswer;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Question: ");
        builder.append(questionText);
        builder.append("\n");

        for(int i = 0; i < answers.length; i++) {
            builder.append("\t- Answer ");
            builder.append(i);
            builder.append(": ");
            builder.append(answers[i]);
            builder.append("\n");
        }

        builder.append("\tCorrect answer: ");
        builder.append(correctAnswer);

        return builder.toString();
    }
}
