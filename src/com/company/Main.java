package com.company;

public class Main {

    public static void main(String[] args) {
//        Leaderboard leaderboard = new Leaderboard();
//        leaderboard.addScore(new Score("Ktos", "Test", 20000));
//        leaderboard.addScore(new Score("asd", "nowy", 40000));
//        leaderboard.addScore(new Score("asd", "nowy", 25000));
        TextView textView = new TextView();
        Question question = new Question("TEST", new String[] { "tesa", "tesaaaa", "aaaaaaaaaaaaaaaaaaaaaaa", "cos" }, 0);
        textView.displayQuestionView(question);
//        textView.displayLeadersView();
//        textView.displayEndView(new Score("asd", "nowy", 25000));
    }
}
