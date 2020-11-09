package com.company;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TextView extends View {
    @Override
    public void displayStartView() {
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

    @Override
    public void displayLeadersView() {
        // @TODO: Change layout to Grid layout and create table with column names
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

            Label leaderLabel = new Label("Leaderboard");
            leaderLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            contentPanel.addComponent(leaderLabel);

            contentPanel.addComponent(new EmptySpace());

            Button button = new Button("New Game");

            Leaderboard leaderboard = new Leaderboard();
            List<Score> ranking = leaderboard.getRanking();

            for(int i = 0; i < ranking.size(); i++) {
                Score score = ranking.get(i);
                String scoreString = (i + 1) + ". " + score.getUsername() + " " + score.getQuizType() + " " + score.getPoints();

                Label scoreLabel = new Label(scoreString);
                // @TODO: Maybe extract method for these 2 lines
                scoreLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
                contentPanel.addComponent(scoreLabel);
            }

            contentPanel.addComponent(new EmptySpace());

            Button newGameButton = new Button("New Game");
            newGameButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            contentPanel.addComponent(newGameButton);

            Button exitButton = new Button("Exit");
            exitButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            contentPanel.addComponent(exitButton);

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

    @Override
    public void displayQuestionView(Question question) {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();

        Terminal terminal = null;
        try {
            terminal = defaultTerminalFactory.createTerminal();
            TerminalSize terminalSize = terminal.getTerminalSize();
            final TextGraphics textGraphics = terminal.newTextGraphics();

            terminal.enterPrivateMode();
            terminal.clearScreen();

            String questionText = question.getQuestionText();

            textGraphics.putString(4, 4, questionText);

            String[] answers = question.getAnswers();
            String answer1 = "A. " + answers[0];
            String answer2 = "B. " + answers[1];
            String answer3 = "C. " + answers[2];
            String answer4 = "D. " + answers[3];

            int maxLength = Integer.max(answer1.length(), answer3.length());

            textGraphics.putString(4, 8, answer1);
            textGraphics.putString(4 + maxLength + 4, 8, answer2);
            textGraphics.putString(4, 10, answer3);
            textGraphics.putString(4 + maxLength + 4, 10, answer4);

            terminal.flush();

            KeyStroke keyStroke = terminal.readInput();
            while(keyStroke.getKeyType() != KeyType.Escape && keyStroke.getKeyType() != KeyType.EOF
                    && Character.toLowerCase(keyStroke.getCharacter()) != 'e'
                    && Character.toLowerCase(keyStroke.getCharacter()) != 'n'
                    && Character.toLowerCase(keyStroke.getCharacter()) != 'l') {
                keyStroke = terminal.readInput();
            }

            terminal.flush();
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

    @Override
    public void displayEndView(Score score) {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();

        Terminal terminal = null;

        try {
            terminal = defaultTerminalFactory.createTerminal();
            TerminalSize terminalSize = terminal.getTerminalSize();
            final TextGraphics textGraphics = terminal.newTextGraphics();

            terminal.enterPrivateMode();
            terminal.clearScreen();

            String congratsString = "Congratulations " + score.getUsername() + "!";
            String pointsString = "You scored " + score.getPoints() + " points in the " + score.getQuizType() + " quiz!";
            String newGame = "New Game";
            String checkLeaders = "Check Leaderboards";
            String exit = "Exit";

            textGraphics.putString(terminalSize.getColumns() / 2 - congratsString.length() / 2, 4, congratsString);
            textGraphics.putString(terminalSize.getColumns() / 2 - pointsString.length() / 2, 6, pointsString);

            textGraphics.putString(terminalSize.getColumns() / 2 - newGame.length() / 2, 10, newGame);
            textGraphics.putString(terminalSize.getColumns() / 2 - checkLeaders.length() / 2, 12, checkLeaders);
            textGraphics.putString(terminalSize.getColumns() / 2 - exit.length() / 2, 14, exit);

            terminal.flush();

            KeyStroke keyStroke = terminal.readInput();
            while(keyStroke.getKeyType() != KeyType.Escape && keyStroke.getKeyType() != KeyType.EOF
                    && Character.toLowerCase(keyStroke.getCharacter()) != 'e'
                    && Character.toLowerCase(keyStroke.getCharacter()) != 'n'
                    && Character.toLowerCase(keyStroke.getCharacter()) != 'l') {
                keyStroke = terminal.readInput();
            }

            terminal.flush();
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
