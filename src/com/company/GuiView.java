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

    public GuiView() {
        leaderboard = new Leaderboard();
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

        questionLabel.setText(question.getQuestionText());
        questionLabel.setFont(new Font("Lato", Font.BOLD, 20));
        addMargin(questionLabel, 7, 10, 0, 10);

        addMargin(controlPanel, 0, 0, 20, 0);

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
        JMenuItem endQuizItem = new JMenuItem("End Quiz");
        JMenuItem mainMenuItem = new JMenuItem("Menu");
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
