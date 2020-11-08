package com.company;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
//        Leaderboard leaderboard = new Leaderboard();
//        leaderboard.addScore(new Score("Ktos", "Test", 20000));
//        leaderboard.addScore(new Score("asd", "nowy", 40000));
//        leaderboard.addScore(new Score("asd", "nowy", 25000));
//        TextView textView = new TextView();
//        Question question = new Question("TEST", new String[] { "tesa", "tesaaaa", "aaaaaaaaaaaaaaaaaaaaaaa", "cos" }, 0);
//        textView.displayQuestionView(question);
        test1();
    }

    public static void test1() throws IOException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();

        Terminal terminal = defaultTerminalFactory.createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        BasicWindow window = new BasicWindow("Hello World!");
        window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
        window.setHints(Arrays.asList(Window.Hint.NO_DECORATIONS));

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));

        Panel contentPanel = new Panel();
        contentPanel.addComponent(new Label("Text Box"));
        contentPanel.addComponent(new Button("Text Box"));
        contentPanel.addComponent(new Button("Another one"));


        window.setComponent(contentPanel);

        gui.addWindowAndWait(window);
        screen.refresh();
    }
}
