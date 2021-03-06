package com.company;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.gui2.menu.MenuBar;
import com.googlecode.lanterna.gui2.menu.MenuItem;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class TextView extends View {
    private final DefaultTerminalFactory defaultTerminalFactory;
    private Terminal terminal;
    private Screen screen;
    private MultiWindowTextGUI gui;
    private Window mainWindow;
    private Panel contentPanel;
    private Quiz quiz;
    private String username;
    private final PointsCalculator pointsCalculator;
    private final HintsChecker hintsChecker;
    private Leaderboard leaderboard;
    private long currentTime;

    public TextView() {
        defaultTerminalFactory = new DefaultTerminalFactory();
        pointsCalculator = new PointsCalculator();
        hintsChecker = new HintsChecker();
        leaderboard = new Leaderboard();
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

        addEmptySpace(contentPanel, 1);

        String asciiWord = AsciiArt.drawWord("Quiz");
        Label welcomeLabel = new Label(asciiWord);
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
        hintsChecker.clearUsedHints();
        pointsCalculator.resetPoints();

        contentPanel = new Panel();
        contentPanel.setLayoutManager(new GridLayout(2));

        GridLayout gridLayout = (GridLayout) contentPanel.getLayoutManager();
        gridLayout.setVerticalSpacing(1);
        gridLayout.setLeftMarginSize(20);
        gridLayout.setRightMarginSize(20);

        Label chooseLabel = new Label("Choose a quiz type:");
        addGridComponent(contentPanel,chooseLabel,GridLayout.Alignment.CENTER,GridLayout.Alignment.BEGINNING,true,false,4,1);

        addEmptySpace(contentPanel, 8);

        List<String> quizTypes = Quiz.getTypes();
        for(String type: quizTypes) {
            Button button = new Button(type);
            addGridComponent(contentPanel,button,GridLayout.Alignment.BEGINNING,GridLayout.Alignment.BEGINNING,false,false,1,1);
            button.addListener(new Button.Listener() {
                @Override
                public void onTriggered(Button button) {
                    quiz = new Quiz(button.getLabel());
                    MessageDialog.showMessageDialog(gui, "Info", "You can get bonus points if you will answer questions fast enough",
                            MessageDialogButton.OK);
                    displayQuestionView(quiz.getQuestion());
                }
            });
        }

        mainWindow.setComponent(contentPanel);
    }

    @Override
    public void displayLeadersView() {
        contentPanel = new Panel();
        contentPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        LinearLayout linearLayout = (LinearLayout) contentPanel.getLayoutManager();
        linearLayout.setSpacing(1);

        Label leaderLabel = new Label("Leaderboard");
        addLinearCenteredComponent(contentPanel, leaderLabel);

        // it can be hard to fit more scores on the small terminal screen
        List<Score> ranking = leaderboard.getTop5();

        Table<String> rankingTable = new Table<>("Pos", "Username", "Quiz type", "Points");

        for (int i = 0; i < ranking.size(); i++) {
            Score score = ranking.get(i);
            rankingTable.getTableModel().addRow(String.valueOf(i + 1), score.getUsername(), score.getQuizType(), String.valueOf(score.getPoints()));
        }
        addLinearCenteredComponent(contentPanel, rankingTable);

        addEmptySpace(contentPanel, 1);

        Button fullRankingButton = new Button("Open full ranking");
        addLinearCenteredComponent(contentPanel, fullRankingButton);
        fullRankingButton.addListener(new Button.Listener() {
            @Override
            public void onTriggered(Button button) {
                MessageDialog.showMessageDialog(gui, "Info", "To see full ranking choose ranking directory in the right panel.\n"
                        + "Then you have to choose the ranking.txt file and click open",
                        MessageDialogButton.OK);
                File file = new FileDialogBuilder()
                        .setTitle("Open File")
                        .setDescription("Choose ranking directory in the right panel and then ranking.txt")
                        .setActionLabel("Open")
                        .build()
                        .showDialog(gui);

                if(file != null) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.open(file);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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
        currentTime = System.currentTimeMillis();
        hintsChecker.clearWasUsed();

        Panel contentPanel = new Panel();
        contentPanel.setLayoutManager(new GridLayout(2));

        GridLayout gridLayout = (GridLayout) contentPanel.getLayoutManager();
        gridLayout.setVerticalSpacing(1);
        gridLayout.setLeftMarginSize(10);
        gridLayout.setRightMarginSize(10);

        String[] answers = question.getAnswers();
        String answer1 = "A." + answers[0];
        String answer2 = "B." + answers[1];
        String answer3 = "C." + answers[2];
        String answer4 = "D." + answers[3];

        addMenu(gui, contentPanel, answers, question.getCorrectAnswer());

        displayMultilineQuestion(contentPanel, question.getQuestionText());

        addEmptySpace(contentPanel, 4);

        addAnswerButton(answer1, 0, contentPanel, question);
        addAnswerButton(answer2, 1, contentPanel, question);
        addAnswerButton(answer3, 2, contentPanel, question);
        addAnswerButton(answer4, 3, contentPanel, question);

        addEmptySpace(contentPanel, 2);

        addAnimatedLabel(contentPanel);

        mainWindow.setComponent(contentPanel);
    }

    @Override
    public void displayEndView(Score score) {
        leaderboard.addScore(score);

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

    private void addMenu(MultiWindowTextGUI gui, Panel contentPanel, String[]answers, int correctAnswer) {
        MenuBar menuBar = new MenuBar();

        Menu menuOptions = new Menu("Options");
        menuBar.add(menuOptions);
        menuOptions.add(new MenuItem("Restart", new Runnable() {
            @Override
            public void run() {
                displayQuizTypeView();
            }
        }));
        menuOptions.add(new MenuItem("End quiz", new Runnable() {
            @Override
            public void run() {
                displayEndView(new Score(username, quiz.getType(), pointsCalculator.getPoints()));
            }
        }));
        menuOptions.add(new MenuItem("Menu", new Runnable() {
            @Override
            public void run() {
                displayStartView();
            }
        }));

        Menu menuHelp = new Menu("Hints");
        menuBar.add(menuHelp);
        menuHelp.add(new MenuItem("About", new Runnable() {
            @Override
            public void run() {
                MessageDialog.showMessageDialog(gui, "Hint", "You can use every hint only once in the game\n" +
                        "You can only use one hint per question",
                        MessageDialogButton.OK);
            }
        }));
        menuHelp.add(new MenuItem("50:50", new Runnable() {
            @Override
            public void run() {
                if(hintsChecker.canUseHint(Hint.FIFTYFYFTY)) {
                    useFiftyFiftyHint(contentPanel, answers, correctAnswer);
                } else {
                    MessageDialog.showMessageDialog(gui, "Hint", "You can't use this hint right now.\n" +
                                    "You either used it before or used other hint for this question",
                            MessageDialogButton.OK);
                }
            }
        }));
        menuHelp.add(new MenuItem("Phone Expert", new Runnable() {
            @Override
            public void run() {
                if(hintsChecker.canUseHint(Hint.EXPERT)) {
                    usePhoneExpertHint(answers, correctAnswer);
                } else {
                    MessageDialog.showMessageDialog(gui, "Hint", "You can't use this hint right now.\n" +
                                    "You either used it before or used other hint for this question",
                            MessageDialogButton.OK);
                }
            }
        }));
        menuHelp.add(new MenuItem("Audience Choice", new Runnable() {
            @Override
            public void run() {
                if(hintsChecker.canUseHint(Hint.AUDIENCE)) {
                    useAskAudienceHint(answers, correctAnswer);
                } else {
                    MessageDialog.showMessageDialog(gui, "Hint", "You can't use this hint right now.\n" +
                                    "You either used it before or used other hint for this question",
                            MessageDialogButton.OK);
                }
            }
        }));

        addGridComponent(contentPanel,menuBar,GridLayout.Alignment.CENTER,GridLayout.Alignment.BEGINNING,true,false,2,1);
    }

    private void useFiftyFiftyHint(Panel panel, String[] answers, int correctAnswer) {
        String[] removedAnswers = Fifty.chooseRemoved(answers, correctAnswer);

        List<Component> toRemove = new ArrayList<>();
        List<Component> compList = panel.getChildrenList();
        for(Component comp : compList) {
            // There was a weird bug where
            // sometimes EmptySpace component was deleted
            if(comp.toString().contains("Button")) {
                Button compButton = (Button) comp;
                String buttonString = compButton.getLabel().substring(2);
                if(buttonString.equals(removedAnswers[0]) || buttonString.equals(removedAnswers[1])) {
                    toRemove.add(comp);
                }
            }
        }

        for(Component comp : toRemove) {
            comp.setVisible(false);
        }
    }

    private void usePhoneExpertHint(String[] answers, int correctAnswer) {
        Expert expert = new Expert();
        String finalString = expert.getExpertAnswer(answers, correctAnswer);

        // multiline dialog text
        final int BREAK_LINE = 50;
        int currentLineLength = 0;

        String[] words = finalString.split(" ");
        StringBuilder builder = new StringBuilder();

        for(String word: words) {
            if(currentLineLength + word.length() + 1 <= BREAK_LINE) {
                if(builder.length() != 0) {
                    builder.append(" ");
                    currentLineLength++;
                }
                builder.append(word);
                currentLineLength += word.length();
            } else {
                builder.append("\n");
                builder.append(word);
                currentLineLength = word.length();
            }
        }

        MessageDialog.showMessageDialog(gui, "Expert answer", builder.toString(), MessageDialogButton.OK);
    }

    private void useAskAudienceHint(String[] answers, int correctAnswer) {
        String audienceAnswer = Audience.vote(answers, correctAnswer);
        MessageDialog.showMessageDialog(gui, "Audience Choice", audienceAnswer, MessageDialogButton.OK);
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
            MessageDialog.showMessageDialog(gui, "Bravo!", "This is correct answer",
                    MessageDialogButton.Close);

            int questionNumber = quiz.getCurrentQuestionNumber();
            int questionTime = (int) (System.currentTimeMillis() - currentTime) / 1000;

            pointsCalculator.calculatePoints(questionNumber, questionTime);

            quiz.nextQuestion();
            questionNumber = quiz.getCurrentQuestionNumber();

            if(questionNumber != -1) {
                Question nextQuestion = quiz.getQuestion();
                displayQuestionView(nextQuestion);
            } else {
                MessageDialog.showMessageDialog(gui, "Finish", "You made it to the end!",
                        MessageDialogButton.Close);
                displayEndView(new Score(username, quiz.getType(), pointsCalculator.getPoints()));
            }
        } else {
            MessageDialog.showMessageDialog(gui, "Ooops", "Not this time bro",
                    MessageDialogButton.Close);
            displayEndView(new Score(username, quiz.getType(), pointsCalculator.getPoints()));
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

    private void addAnimatedLabel(Panel panel) {
        AnimatedLabel label = new AnimatedLabel("Time is ticking");
        label.addFrame("Time is ticking.");
        label.addFrame("Time is ticking..");
        label.addFrame("Time is ticking...");
        addGridComponent(panel,label,
                GridLayout.Alignment.CENTER,GridLayout.Alignment.BEGINNING,
                true,false,2,1);
        label.startAnimation(500);
    }
}
