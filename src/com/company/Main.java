package com.company;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        Leaderboard leaderboard = new Leaderboard();
//        leaderboard.addScore(new Score("Ktos", "Test", 20000));
//        leaderboard.addScore(new Score("asd", "nowy", 40000));
//        leaderboard.addScore(new Score("asd", "nowy", 25000));
        TextView textView = new TextView();
//        Question question = new Question("TEST", new String[] { "tesa", "tesaaaa", "aaaaaaaaaaaaaaaaaaaaaaa", "cos" }, 0);
//        textView.displayQuestionView(question);
//        textView.displayLeadersView();
        textView.displayEndView(new Score("asd", "nowy", 25000));
    }
}
