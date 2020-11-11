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
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.gui2.menu.MenuBar;
import com.googlecode.lanterna.gui2.menu.MenuItem;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// @TODO: objects for creating terminal should be fields

public class TextView extends View {
    private final DefaultTerminalFactory defaultTerminalFactory;
    private Terminal terminal;
    private Screen screen;
    private MultiWindowTextGUI gui;
    private Window mainWindow;
    private Panel contentPanel;

    public TextView() {
        defaultTerminalFactory = new DefaultTerminalFactory();
        initializeTerminal();
    }

    private void initializeTerminal() {
        try {
            terminal = defaultTerminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();

            gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));

            mainWindow = new BasicWindow();
            mainWindow.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));

            contentPanel = new Panel();
            mainWindow.setComponent(contentPanel);

            displayStartView();

            gui.addWindowAndWait(mainWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayStartView() {
        contentPanel = new Panel();
        contentPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        LinearLayout linearLayout = (LinearLayout) contentPanel.getLayoutManager();
        linearLayout.setSpacing(1);

        Label welcomeLabel = new Label("Welcome to the Quiz App!");
        welcomeLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        contentPanel.addComponent(welcomeLabel);

        contentPanel.addComponent(new EmptySpace());

        Button button = new Button("New Game");
        button.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        button.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                // @TODO: first user should be able to choose quiz type which he wants
                // for nav testing now
                displayQuestionView(new Question("TEST", new String[] { "tesa", "tesaaaa", "aaaaaaaaaaaaaaaaaaaaaaa", "cos" }, 0));
            }
        });
        contentPanel.addComponent(button);

        Button button2 = new Button("Leaderboards");
        button2.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        button2.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                displayLeadersView();
            }
        });
        contentPanel.addComponent(button2);

        Button button3 = new Button("Exit");
        button3.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        button3.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                endGame();
            }
        });
        contentPanel.addComponent(button3);

        mainWindow.setComponent(contentPanel);
    }

    @Override
    public void displayLeadersView() {
        // @TODO: Change layout to Grid layout and create table with column names
        contentPanel = new Panel();
        contentPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        LinearLayout linearLayout = (LinearLayout) contentPanel.getLayoutManager();
        linearLayout.setSpacing(1);

        Label leaderLabel = new Label("Leaderboard");
        leaderLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        contentPanel.addComponent(leaderLabel);

        contentPanel.addComponent(new EmptySpace());

        Leaderboard leaderboard = new Leaderboard();
        List<Score> ranking = leaderboard.getRanking();

        for (int i = 0; i < ranking.size(); i++) {
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
        newGameButton.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                // for nav testing now
                displayQuestionView(new Question("TEST", new String[] { "tesa", "tesaaaa", "aaaaaaaaaaaaaaaaaaaaaaa", "cos" }, 0));
            }
        });
        contentPanel.addComponent(newGameButton);

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        mainMenuButton.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                displayStartView();
            }
        });
        contentPanel.addComponent(mainMenuButton);

        Button exitButton = new Button("Exit");
        exitButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        exitButton.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                endGame();
            }
        });
        contentPanel.addComponent(exitButton);

        mainWindow.setComponent(contentPanel);
    }

    @Override
    public void displayQuestionView(Question question) {
        Panel contentPanel = new Panel();
        contentPanel.setLayoutManager(new GridLayout(2));

        GridLayout gridLayout = (GridLayout) contentPanel.getLayoutManager();
        gridLayout.setVerticalSpacing(1);
        gridLayout.setLeftMarginSize(10);
        gridLayout.setRightMarginSize(10);

        addMenu(gui, contentPanel);

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

        mainWindow.setComponent(contentPanel);
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
            if (terminal != null) {
                try {
                    terminal.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void endGame() {
        if (screen != null) {
            try {
                screen.stopScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addMenu(MultiWindowTextGUI gui, Panel contentPanel) {
        MenuBar menuBar = new MenuBar();

        Menu menuOptions = new Menu("Options");
        menuBar.add(menuOptions);
        menuOptions.add(new MenuItem("Restart", new Runnable() {
            @Override
            public void run() {
                MessageDialog.showMessageDialog(gui, "Option", "Do you want to start a new game?", MessageDialogButton.OK);
            }
        }));
        menuOptions.add(new MenuItem("End quiz", new Runnable() {
            @Override
            public void run() {
                MessageDialog.showMessageDialog(gui, "Option", "Do you want to end your quiz?", MessageDialogButton.OK);
            }
        }));
        menuOptions.add(new MenuItem("Exit", new Runnable() {
            @Override
            public void run() {
                MessageDialog.showMessageDialog(gui, "Option", "Do you really wanna end now?", MessageDialogButton.OK);
            }
        }));

        Menu menuHelp = new Menu("Hints");
        menuBar.add(menuHelp);
        menuHelp.add(new MenuItem("About", new Runnable() {
            @Override
            public void run() {
                MessageDialog.showMessageDialog(gui, "Hint", "You can use every hint only once in the game",
                        MessageDialogButton.OK);
            }
        }));
        menuHelp.add(new MenuItem("50:50", new Runnable() {
            @Override
            public void run() {
                MessageDialog.showMessageDialog(gui, "Hint", "Do you wish to use hint 50:50?",
                        MessageDialogButton.OK);
            }
        }));
        menuHelp.add(new MenuItem("Phone Expert", new Runnable() {
            @Override
            public void run() {
                MessageDialog.showMessageDialog(gui, "Hint", "Do you wish to phone your quiz expert?",
                        MessageDialogButton.OK);
            }
        }));
        menuHelp.add(new MenuItem("Audience Choice", new Runnable() {
            @Override
            public void run() {
                MessageDialog.showMessageDialog(gui, "Hint", "Do you wish to ask the audience for the answer?",
                        MessageDialogButton.OK);
            }
        }));

        menuBar.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.BEGINNING,
                true,
                false,
                2,
                1
        ));
        contentPanel.addComponent(menuBar);
    }
}
