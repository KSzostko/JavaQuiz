package com.company;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// @TODO: objects for creating terminal should be fields

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
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();

            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));

            Window window = new BasicWindow();
            window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));

            Panel contentPanel = new Panel();
            contentPanel.setLayoutManager(new GridLayout(2));

            GridLayout gridLayout = (GridLayout) contentPanel.getLayoutManager();
            gridLayout.setVerticalSpacing(1);
            gridLayout.setLeftMarginSize(10);
            gridLayout.setRightMarginSize(10);

            // @TODO: Add simple nav on the top

            // empty space for whole row require empty space in every row column
            contentPanel.addComponent(new EmptySpace());
            contentPanel.addComponent(new EmptySpace());

            Label questionTextLabel = new Label(question.getQuestionText());
            questionTextLabel.setLayoutData(GridLayout.createLayoutData(
                    GridLayout.Alignment.CENTER,
                    GridLayout.Alignment.BEGINNING,
                    true,
                    false,
                    2,
                    1
            ));
            contentPanel.addComponent(questionTextLabel);

            contentPanel.addComponent(new EmptySpace());
            contentPanel.addComponent(new EmptySpace());
            contentPanel.addComponent(new EmptySpace());
            contentPanel.addComponent(new EmptySpace());

            // @TODO: Add timer
            addMenu(contentPanel);

            String[] answers = question.getAnswers();
            String answer1 = "A." + answers[0];
            String answer2 = "B." + answers[1];
            String answer3 = "C." + answers[2];
            String answer4 = "D." + answers[3];

            contentPanel.addComponent(
                    new Button(answer1)
                    .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING,
                            GridLayout.Alignment.BEGINNING,
                            false,
                            false,
                            1,
                            1))
            );
            contentPanel.addComponent(
                    new Button(answer2)
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING,
                                    GridLayout.Alignment.BEGINNING,
                                    true,
                                    false,
                                    1,
                                    1))
            );

            contentPanel.addComponent(
                    new Button(answer3)
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING,
                                    GridLayout.Alignment.BEGINNING,
                                    false,
                                    false,
                                    1,
                                    1))
            );
            contentPanel.addComponent(
                    new Button(answer4)
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING,
                                    GridLayout.Alignment.BEGINNING,
                                    true,
                                    false,
                                    1,
                                    1))
            );

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
    public void displayEndView(Score score) {
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

            String congratsString = "Congratulations " + score.getUsername() + "!";
            Label congratsLabel = new Label(congratsString);
            congratsLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            contentPanel.addComponent(congratsLabel);

            String pointsString = "You scored " + score.getPoints() + " points in the " + score.getQuizType() + " quiz!";
            Label pointsLabel = new Label(pointsString);
            pointsLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            contentPanel.addComponent(pointsLabel);

            contentPanel.addComponent(new EmptySpace());

            Button newGameButton = new Button("New Game");
            newGameButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            contentPanel.addComponent(newGameButton);

            Button leadersButton = new Button("Check Leaderboard");
            leadersButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            contentPanel.addComponent(leadersButton);

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

    private void addMenu(Panel contentPanel) {
        MenuBar menuBar = new MenuBar();

//        Menu menuOptions = new Menu("Options");
//        menuBar.add(menuOptions);
    }
}
