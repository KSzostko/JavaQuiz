package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class GuiView extends View {
    private JFrame mainFrame;
    private Quiz quiz;
    private Leaderboard leaderboard;
    private final PointsCalculator pointsCalculator;
    private long currentTime;

    public GuiView() {
        leaderboard = new Leaderboard();
        pointsCalculator = new PointsCalculator();
        initializeScreen();
    }

    private void initializeScreen() {
        mainFrame = new JFrame("Quizapp");
        mainFrame.setSize(600, 500);
        mainFrame.getContentPane().setBackground(Color.decode("#FFFFFF"));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
    }

    @Override
    public void displayStartView() {
        clearScreen();
        mainFrame.setLayout(new BorderLayout(20, 60));

        JLabel welcomeLabel = new JLabel("", JLabel.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.WHITE);

        mainFrame.add(welcomeLabel, BorderLayout.PAGE_START);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);

        welcomeLabel.setText("Welcome to the Quiz app!");
        welcomeLabel.setFont(new Font("Lato", Font.BOLD, 20));
        addMargin(welcomeLabel, 50, 10, 10, 10);

        addNavButtons(controlPanel, true);

        mainFrame.setVisible(true);
    }

    @Override
    public void displayQuizTypeView() {
        pointsCalculator.resetPoints();

        clearScreen();
        mainFrame.setLayout(new BorderLayout(20, 60));

        JLabel headerLabel = new JLabel("", JLabel.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        controlPanel.setBackground(Color.WHITE);

        mainFrame.add(headerLabel, BorderLayout.PAGE_START);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);

        headerLabel.setText("Choose a quiz type:");
        headerLabel.setFont(new Font("Lato", Font.BOLD, 20));
        addMargin(headerLabel, 50, 10, 10, 10);

        List<String> quizTypes = Quiz.getTypes();
        for(String type: quizTypes) {
            JButton quizButton = new JButton(type);
            styleButton(quizButton);

            quizButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    quiz = new Quiz(e.getActionCommand());
                    displayDialog("You can get bonus points if you will answer questions fast enough");
                    displayQuestionView(quiz.getQuestion());
                }
            });

            controlPanel.add(quizButton);
        }

        mainFrame.setVisible(true);
    }

    @Override
    public void displayLeadersView() {
        clearScreen();
        mainFrame.setLayout(new BorderLayout(20, 60));

        JLabel leadersLabel = new JLabel("", JLabel.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.WHITE);

        mainFrame.add(leadersLabel, BorderLayout.PAGE_START);

        JTable table = createRankingTable();
        styleTable(table);

        // without scrollPane column names weren't visible
        JScrollPane scrollPane = new JScrollPane(table);
        addMargin(scrollPane, 10, 100, 10, 100);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createMatteBorder(10, 100, 0, 100, Color.WHITE));

        mainFrame.add(scrollPane, BorderLayout.CENTER);
        mainFrame.add(controlPanel, BorderLayout.PAGE_END);
        mainFrame.setVisible(true);

        leadersLabel.setText("Leaderboard");
        leadersLabel.setFont(new Font("Lato", Font.BOLD, 20));
        addMargin(leadersLabel, 7, 10, 0, 10);

        addNavButtons(controlPanel, false);

        addMargin(controlPanel, 0, 0, 20, 0);

        mainFrame.setVisible(true);
    }

    @Override
    public void displayQuestionView(Question question) {
        currentTime = System.currentTimeMillis();

        clearScreen();
        mainFrame.setLayout(new BorderLayout(20, 60));

        JMenuBar menuBar = addMenuBar();

        JLabel questionLabel = new JLabel("", JLabel.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.WHITE);

        mainFrame.add(menuBar, BorderLayout.PAGE_START);
        mainFrame.add(questionLabel, BorderLayout.CENTER);
        mainFrame.add(controlPanel, BorderLayout.PAGE_END);
        mainFrame.setVisible(true);

        String multilineString = wrapQuestionText(question.getQuestionText());
        questionLabel.setText(multilineString);
        questionLabel.setFont(new Font("Lato", Font.BOLD, 20));
        addMargin(questionLabel, 7, 10, 0, 10);

        addAnswersButtons(controlPanel, question);

        addMargin(controlPanel, 0, 40, 150, 40);

        mainFrame.setVisible(true);
    }

    @Override
    public void displayEndView(Score score) {
        clearScreen();
        mainFrame.setLayout(new BorderLayout(20, 60));

        JLabel headerLabel = new JLabel("",JLabel.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.WHITE);

        mainFrame.add(headerLabel, BorderLayout.PAGE_START);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);

        String congratsString = "<html>Congratulations " + score.getUsername() + "!<br/>" +
                "You scored " + score.getPoints() + " points in the " + score.getQuizType() + " quiz!<html/>";
        headerLabel.setText(congratsString);
        headerLabel.setFont(new Font("Lato", Font.BOLD, 20));
        addMargin(headerLabel, 50, 10, 10, 10);

        addNavButtons(controlPanel, true);

        mainFrame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.decode("#6200EE"));
        button.setForeground(Color.decode("#FFFFFF"));
        button.setBorderPainted(false);
        button.setFont(new Font("Lato", Font.PLAIN, 15));
    }

    private void addMargin(JComponent cmp, int top, int left, int bottom, int right) {
        Border border = cmp.getBorder();
        Border margin = new EmptyBorder(top, left, bottom, right);
        cmp.setBorder(new CompoundBorder(border, margin));
    }

    private void clearScreen() {
        mainFrame.getContentPane().removeAll();
        mainFrame.validate();
    }

    private JMenuBar addMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu optionsMenu = new JMenu("Options");

        JMenuItem restartItem = new JMenuItem("Restart");
        restartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayQuizTypeView();
            }
        });

        JMenuItem endQuizItem = new JMenuItem("End Quiz");
        endQuizItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // hardcoded values for testing now
                displayEndView(new Score("Anon", "test", 125));
            }
        });

        JMenuItem mainMenuItem = new JMenuItem("Menu");
        mainMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayStartView();
            }
        });

        optionsMenu.add(restartItem);
        optionsMenu.add(endQuizItem);
        optionsMenu.add(mainMenuItem);

        JMenu hintsMenu = new JMenu("Hints");
        JMenuItem aboutItem = new JMenuItem("About");
        JMenuItem fiftyItem = new JMenuItem("50:50");
        JMenuItem expertItem = new JMenuItem("Phone Expert");
        JMenuItem audienceItem = new JMenuItem("Audience Choice");
        hintsMenu.add(aboutItem);
        hintsMenu.add(fiftyItem);
        hintsMenu.add(expertItem);
        hintsMenu.add(audienceItem);

        menuBar.add(optionsMenu);
        menuBar.add(hintsMenu);

        return menuBar;
    }

    private String wrapQuestionText(String text) {
        String[] words = text.split(" ");
        final int BREAK_LINE = 50;
        int currentLineLength = 0;

        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        for(String word: words) {
            if(currentLineLength + word.length() + 1 <= BREAK_LINE) {
                if(builder.length() != 6) {
                    builder.append(" ");
                    currentLineLength++;
                }

                builder.append(word);
                currentLineLength += word.length();
            } else {
                builder.append("<br/>");
                builder.append(word);
                currentLineLength = word.length();
            }
        }
        builder.append("<html/");

        return builder.toString();
    }

    private void displayDialog(String message) {
        JDialog dialog = new JDialog(mainFrame, "Info", true);
        dialog.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        JLabel label = new JLabel(message, JLabel.CENTER);

        JButton okButton = new JButton("Ok");
        styleButton(okButton);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });

        dialog.add(label);
        dialog.add(okButton);
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    }

    private void addAnswersButtons(JPanel controlPanel, Question question) {
        String[] answers = question.getAnswers();

        JPanel upPanel = new JPanel();
        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.X_AXIS));
        upPanel.setBackground(Color.WHITE);
        JPanel downPanel = new JPanel();
        downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.X_AXIS));
        downPanel.setBackground(Color.WHITE);

        JButton button1 = new JButton("A. " + answers[0]);
        styleButton(button1);
        upPanel.add(button1);
        upPanel.add(Box.createHorizontalGlue());
        addButtonListener(button1, 0, question);

        JButton button2 = new JButton("B. " + answers[1]);
        styleButton(button2);
        upPanel.add(button2);
        addButtonListener(button2, 1, question);

        JButton button3 = new JButton("C. " + answers[2]);
        styleButton(button3);
        downPanel.add(button3);
        downPanel.add(Box.createHorizontalGlue());
        addButtonListener(button3, 2, question);

        JButton button4 = new JButton("D." + answers[3]);
        styleButton(button4);
        downPanel.add(button4);
        addButtonListener(button4, 3, question);

        controlPanel.add(upPanel);
        controlPanel.add(Box.createVerticalStrut(20));
        controlPanel.add(downPanel);
    }

    private void addButtonListener(JButton button, int answerId, Question question) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSelectedAnswer(answerId, question);
            }
        });
    }

    private void checkSelectedAnswer(int answerId, Question question) {
        if(question.checkAnswer(answerId)) {
            // @Todo: display dialog about good answer

            int questionNumber = quiz.getCurrentQuestionNumber();
            int questionTime = (int) (System.currentTimeMillis() - currentTime) / 1000;

            pointsCalculator.calculatePoints(questionNumber, questionTime);

            quiz.nextQuestion();
            questionNumber = quiz.getCurrentQuestionNumber();

            if(questionNumber != -1) {
                Question nextQuestion = quiz.getQuestion();
                displayQuestionView(nextQuestion);
            } else {
                // hardcoded name for testing now
                displayEndView(new Score("Anon", quiz.getType(), pointsCalculator.getPoints()));
            }
        } else {
            // @Todo: display dialog about bad answer
            displayEndView(new Score("Anon", quiz.getType(), pointsCalculator.getPoints()));
        }
    }

    private void addNavButtons(JPanel controlPanel, boolean less) {
        JButton newGameButton = new JButton("New Game");
        styleButton(newGameButton);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton leadersButton = new JButton("Leaderboard");
        styleButton(leadersButton);
        leadersButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exitButton = new JButton("Exit");
        styleButton(exitButton);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton rankingButton = new JButton("Open full ranking");
        styleButton(rankingButton);
        rankingButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton menuButton = new JButton("Main Menu");
        styleButton(menuButton);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayQuizTypeView();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
                mainFrame.dispose();
                mainFrame.setVisible(false);
            }
        });

        if (less) {
            leadersButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayLeadersView();
                }
            });

            controlPanel.add(newGameButton);
            // spacing between objects
            controlPanel.add(Box.createVerticalStrut(20));

            controlPanel.add(leadersButton);
        } else {
            // @TODO: full ranking button listener

            menuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayStartView();
                }
            });

            controlPanel.add(rankingButton);
            controlPanel.add(Box.createVerticalStrut(20));

            controlPanel.add(newGameButton);
            controlPanel.add(Box.createVerticalStrut(20));

            controlPanel.add(menuButton);
        }

        controlPanel.add(Box.createVerticalStrut(20));
        controlPanel.add(exitButton);
    }

    private JTable createRankingTable() {
        TableModel dataModel = new AbstractTableModel() {
            private final List<Score> ranking = leaderboard.getTop5();
            private final String columns[] = { "Pos", "Username", "Quiz type", "Points" };

            @Override
            public int getRowCount() {
                return ranking.size();
            }

            @Override
            public int getColumnCount() {
                return columns.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return rowIndex + 1;
                    case 1:
                        return ranking.get(rowIndex).getUsername();
                    case 2:
                        return ranking.get(rowIndex).getQuizType();
                    default:
                        return ranking.get(rowIndex).getPoints();
                }
            }

            @Override
            public String getColumnName(int column) {
                return columns[column];
            }
        };

        return new JTable(dataModel);
    }

    private void styleTable(JTable table) {
        // centering position number in the table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        table.setFont(new Font("Lato", Font.PLAIN, 12));
        table.setGridColor(Color.decode("#DDDDDD"));
        table.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.WHITE);
        tableHeader.setFont(new Font("Lato", Font.BOLD, 12));
        tableHeader.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
    }
}
