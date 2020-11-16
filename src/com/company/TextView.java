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
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.gui2.menu.MenuBar;
import com.googlecode.lanterna.gui2.menu.MenuItem;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.w3c.dom.Text;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

// @TODO: objects for creating terminal should be fields

public class TextView extends View {
    private final DefaultTerminalFactory defaultTerminalFactory;
    private Terminal terminal;
    private Screen screen;
    private MultiWindowTextGUI gui;
    private Window mainWindow;
    private Panel contentPanel;
    private Quiz quiz;
    private String username;

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

            enterName();

            gui.addWindowAndWait(mainWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enterName() {
        String result = new TextInputDialogBuilder()
                .setTitle("Title")
                .setDescription("Enter your name")
                .setValidationPattern(Pattern.compile("[a-zA-Z]+[0-9]*"), "Username must contain at least one letter and optionally numbers")
                .build()
                .showDialog(gui);

        if(result == null) {
            endGame();
        } else {
            username = result;
            displayStartView();
        }
    }

    @Override
    public void displayStartView() {
        contentPanel = new Panel();
        contentPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        LinearLayout linearLayout = (LinearLayout) contentPanel.getLayoutManager();
        linearLayout.setSpacing(1);

        Label welcomeLabel = new Label("Welcome to the Quiz App!");
        addLinearCenteredComponent(contentPanel, welcomeLabel);

        addEmptySpace(contentPanel, 1);

        Button button = new Button("New Game");
        addLinearCenteredComponent(contentPanel, button);
        button.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                displayQuizTypeView();
            }
        });

        Button button2 = new Button("Leaderboards");
        addLinearCenteredComponent(contentPanel, button2);
        button2.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                displayLeadersView();
            }
        });

        Button button3 = new Button("Exit");
        addLinearCenteredComponent(contentPanel, button3);
        button3.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                endGame();
            }
        });

        mainWindow.setComponent(contentPanel);
    }

    @Override
    public void displayQuizTypeView() {
        contentPanel = new Panel();
        contentPanel.setLayoutManager(new GridLayout(2));

        GridLayout gridLayout = (GridLayout) contentPanel.getLayoutManager();
        gridLayout.setVerticalSpacing(1);
        gridLayout.setLeftMarginSize(20);
        gridLayout.setRightMarginSize(20);

        Label chooseLabel = new Label("Choose a quiz type:");
        addGridComponent(contentPanel,chooseLabel,GridLayout.Alignment.CENTER,GridLayout.Alignment.BEGINNING,true,false,2,1);

        addEmptySpace(contentPanel, 4);

        List<String> quizTypes = Quiz.getTypes();
        for(String type: quizTypes) {
            Button button = new Button(type);
            addGridComponent(contentPanel,button,GridLayout.Alignment.BEGINNING,GridLayout.Alignment.BEGINNING,false,false,1,1);
            button.addListener(new Button.Listener() {
                @Override
                public void onTriggered(Button button) {
                    quiz = new Quiz(button.getLabel());
                    displayQuestionView(quiz.getQuestion());
                }
            });
        }

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
        addLinearCenteredComponent(contentPanel, leaderLabel);

        addEmptySpace(contentPanel, 1);

        Leaderboard leaderboard = new Leaderboard();
        List<Score> ranking = leaderboard.getRanking();

        for (int i = 0; i < ranking.size(); i++) {
            Score score = ranking.get(i);
            String scoreString = (i + 1) + ". " + score.getUsername() + " " + score.getQuizType() + " " + score.getPoints();

            Label scoreLabel = new Label(scoreString);
            addLinearCenteredComponent(contentPanel, scoreLabel);
        }

        addEmptySpace(contentPanel, 1);

        Button newGameButton = new Button("New Game");
        addLinearCenteredComponent(contentPanel, newGameButton);
        newGameButton.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                displayQuizTypeView();
            }
        });

        Button mainMenuButton = new Button("Main Menu");
        addLinearCenteredComponent(contentPanel, mainMenuButton);
        mainMenuButton.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                displayStartView();
            }
        });

        Button exitButton = new Button("Exit");
        addLinearCenteredComponent(contentPanel, exitButton);
        exitButton.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                endGame();
            }
        });

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

        displayMultilineQuestion(contentPanel, question.getQuestionText());

        addEmptySpace(contentPanel, 4);

        // @TODO: Add timer

        String[] answers = question.getAnswers();
        String answer1 = "A." + answers[0];
        String answer2 = "B." + answers[1];
        String answer3 = "C." + answers[2];
        String answer4 = "D." + answers[3];

        addAnswerButton(answer1, 0, contentPanel, question);
        addAnswerButton(answer2, 1, contentPanel, question);
        addAnswerButton(answer3, 2, contentPanel, question);
        addAnswerButton(answer4, 3, contentPanel, question);

        mainWindow.setComponent(contentPanel);
    }

    @Override
    public void displayEndView(Score score) {
        Panel contentPanel = new Panel();
        contentPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        LinearLayout linearLayout = (LinearLayout) contentPanel.getLayoutManager();
        linearLayout.setSpacing(1);

        String congratsString = "Congratulations " + score.getUsername() + "!";
        Label congratsLabel = new Label(congratsString);
        addLinearCenteredComponent(contentPanel, congratsLabel);

        String pointsString = "You scored " + score.getPoints() + " points in the " + score.getQuizType() + " quiz!";
        Label pointsLabel = new Label(pointsString);
        addLinearCenteredComponent(contentPanel, pointsLabel);

        addEmptySpace(contentPanel, 1);

        Button newGameButton = new Button("New Game");
        addLinearCenteredComponent(contentPanel, newGameButton);
        newGameButton.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                displayQuizTypeView();
            }
        });

        Button leadersButton = new Button("Check Leaderboard");
        addLinearCenteredComponent(contentPanel, leadersButton);
        leadersButton.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                displayLeadersView();
            }
        });

        Button exitButton = new Button("Exit");
        addLinearCenteredComponent(contentPanel, exitButton);
        exitButton.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                endGame();
            }
        });

        mainWindow.setComponent(contentPanel);
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
                // @TODO: add message dialog where you choose yes no
//                MessageDialog.showMessageDialog(gui, "Option", "Do you want to start a new game?", MessageDialogButton.OK);
                displayQuizTypeView();
            }
        }));
        menuOptions.add(new MenuItem("End quiz", new Runnable() {
            @Override
            public void run() {
//                MessageDialog.showMessageDialog(gui, "Option", "Do you want to end your quiz?", MessageDialogButton.OK);
                // for nav testing for now
                displayEndView(new Score("asd", "nowy", 25000));
            }
        }));
        menuOptions.add(new MenuItem("Exit", new Runnable() {
            @Override
            public void run() {
//                MessageDialog.showMessageDialog(gui, "Option", "Do you really wanna end now?", MessageDialogButton.OK);
                endGame();
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

        addGridComponent(contentPanel,menuBar,GridLayout.Alignment.CENTER,GridLayout.Alignment.BEGINNING,true,false,2,1);
    }

    private void addLinearCenteredComponent(Panel panel, Component component) {
        panel.addComponent(component);
        component.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
    }

    private void addGridComponent(Panel panel, Component comp, GridLayout.Alignment alignHor, GridLayout.Alignment alignVer, boolean grabHor,
                                  boolean grabVer, int horSpan, int verSpan) {
        panel.addComponent(comp);
        comp.setLayoutData(GridLayout.createLayoutData(alignHor, alignVer, grabHor, grabVer, horSpan, verSpan));
    }

    private void addEmptySpace(Panel panel, int count) {
        for(int i = 0; i < count; i++) {
            panel.addComponent(new EmptySpace());
        }
    }

    private void displayMultilineQuestion(Panel panel, String text) {
        String[] words = text.split(" ");
        final int BREAK_LINE = 50;
        StringBuilder builder = new StringBuilder();

        for(String word: words) {
            if(builder.length() + word.length() + 1 <= BREAK_LINE) {
                if(builder.length() != 0) {
                    builder.append(" ");
                }
                builder.append(word);
            } else {
                Label textLabel = new Label(builder.toString());
                addGridComponent(panel,textLabel,GridLayout.Alignment.CENTER,GridLayout.Alignment.BEGINNING,false,false,2,1);
                builder.setLength(0);
                builder.append(word);
            }
        }
        if(builder.length() != 0) {
            Label textLabel = new Label(builder.toString());
            addGridComponent(panel,textLabel,GridLayout.Alignment.CENTER,GridLayout.Alignment.BEGINNING,false,false,2,1);
        }
    }

    private void checkSelectedAnswer(int answerId, Question question) {
        if(question.checkAnswer(answerId)) {
            // @TODO: Update score
            // @TODO: Maybe ask user if he is sure it's the right answer
            MessageDialog.showMessageDialog(gui, "Bravo!", "This is correct answer",
                    MessageDialogButton.Close);

            quiz.nextQuestion();
            int questionNumber = quiz.getCurrentQuestionNumber();

            if(questionNumber != -1) {
                Question nextQuestion = quiz.getQuestion();
                displayQuestionView(nextQuestion);
            } else {
                MessageDialog.showMessageDialog(gui, "Finish", "You made it to the end!",
                        MessageDialogButton.Close);
                // random score just for testing now
                displayEndView(new Score("asd", quiz.getType(), 25000));
            }
        } else {
            MessageDialog.showMessageDialog(gui, "Ooops", "Not this time bro",
                    MessageDialogButton.Close);
            // random score just for testing now
            displayEndView(new Score("asd", quiz.getType(), 25000));
        }
    }

    private void addAnswerButton(String answerText, int answerId, Panel panel, Question question) {
        Button button = new Button(answerText);

        boolean onRight = answerId % 2 == 1;
        GridLayout.Alignment alignment;
        if(onRight) {
            alignment = GridLayout.Alignment.END;
        } else {
            alignment = GridLayout.Alignment.BEGINNING;
        }
        addGridComponent(panel,button,alignment,GridLayout.Alignment.BEGINNING,false,false,1,1);

        button.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                checkSelectedAnswer(answerId, question);
            }
        });
    }
}
