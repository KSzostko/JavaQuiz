package com.company;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
//        Leaderboard leaderboard = new Leaderboard();
//        leaderboard.addScore(new Score("Ktos", "Test", 20000));
//        leaderboard.addScore(new Score("asd", "nowy", 40000));
//        leaderboard.addScore(new Score("asd", "nowy", 25000));
//        TextView textView = new TextView();
//        Question question = new Question("TEST", new String[] { "tesa", "tesaaaa", "aaaaaaaaaaaaaaaaaaaaaaa", "cos" }, 0);
//        textView.displayQuestionView(question);
        test1();
    }

    public static void test1() {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();

        Terminal terminal = null;
        try {
            terminal = defaultTerminalFactory.createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();

            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));

            Window window = new BasicWindow();
            window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));

            Panel contentPanel = new Panel();
            contentPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

            LinearLayout linearLayout = (LinearLayout) contentPanel.getLayoutManager();
            linearLayout.setSpacing(1);

            Label welcomeLabel = new Label("Welcome to the Quiz App!");
            welcomeLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            contentPanel.addComponent(welcomeLabel);

            contentPanel.addComponent(new EmptySpace());

            Button button = new Button("New Game");
            button.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            contentPanel.addComponent(button);

            Button button2 = new Button("Leaderboards");
            button2.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            contentPanel.addComponent(button2);

            Button button3 = new Button("Exit");
            button3.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            contentPanel.addComponent(button3);

            window.setComponent(contentPanel);

            gui.addWindowAndWait(window);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(terminal != null) {
                try {
                    terminal.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
