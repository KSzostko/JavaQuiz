package com.company;

public class Main {

    public static void main(String[] args) {
//        TextView textView = new TextView();
//        textView.displayStartView();
        Quiz quiz = new Quiz("com/company/test");
        System.out.println(quiz.loadQuestions());
        System.out.println(quiz);
    }
}
