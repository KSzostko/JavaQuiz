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
    JFrame mainFrame;
    Leaderboard leaderboard;

    public GuiView() {
        leaderboard = new Leaderboard();
        initializeScreen();
    }

    private void initializeScreen() {
        mainFrame = new JFrame("Quizapp");
        mainFrame.setSize(600, 500);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
    }

    @Override
    public void displayStartView() {
        JLabel headerLabel;
        JPanel controlPanel;

        clearScreen();
        mainFrame.setLayout(new BorderLayout(20, 60));
        mainFrame.getContentPane().setBackground(Color.decode("#FFFFFF"));

        headerLabel = new JLabel("",JLabel.CENTER);

        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.WHITE);

        mainFrame.add(headerLabel, BorderLayout.PAGE_START);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);

        headerLabel.setText("Welcome to the Quiz app!");
        headerLabel.setFont(new Font("Lato", Font.BOLD, 20));
        addMargin(headerLabel, 50, 10, 10, 10);

        JButton okButton = new JButton("New Game");
        styleButton(okButton);
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayQuizTypeView();
            }
        });

        JButton submitButton = new JButton("Leaderboard");
        styleButton(submitButton);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLeadersView();
            }
        });

        JButton cancelButton = new JButton("Exit");
        styleButton(cancelButton);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        controlPanel.add(okButton);
        // spacing between objects
        controlPanel.add(Box.createVerticalStrut(20));

        controlPanel.add(submitButton);
        controlPanel.add(Box.createVerticalStrut(20));

        controlPanel.add(cancelButton);

        mainFrame.setVisible(true);
    }

    @Override
    public void displayQuizTypeView() {
        JLabel headerLabel;
        JPanel controlPanel;

        clearScreen();
        mainFrame.setLayout(new BorderLayout(20, 60));
        mainFrame.getContentPane().setBackground(Color.decode("#FFFFFF"));

        headerLabel = new JLabel("",JLabel.CENTER);

        controlPanel = new JPanel();
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

            controlPanel.add(quizButton);

//            this is jus for wrapping testing
//            JButton quizButton2 = new JButton(type);
//            styleButton(quizButton2);
//
//            controlPanel.add(quizButton2);
//
//            JButton quizButton3 = new JButton(type);
//            styleButton(quizButton3);
//
//            controlPanel.add(quizButton3);
        }

        mainFrame.setVisible(true);
    }

    @Override
    public void displayLeadersView() {
        JLabel headerLabel;
        JPanel controlPanel;

        clearScreen();
        mainFrame.setLayout(new BorderLayout(20, 60));
        mainFrame.getContentPane().setBackground(Color.decode("#FFFFFF"));

        headerLabel = new JLabel("",JLabel.CENTER);

        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.WHITE);

        mainFrame.add(headerLabel, BorderLayout.PAGE_START);

        JTable table = createRankingTable();

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

        // without scrollPane column names weren't visible
        JScrollPane scrollPane = new JScrollPane(table);
        addMargin(scrollPane, 10, 100, 10, 100);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createMatteBorder(10, 100, 0, 100, Color.WHITE));

        mainFrame.add(scrollPane, BorderLayout.CENTER);

        mainFrame.add(controlPanel, BorderLayout.PAGE_END);
        mainFrame.setVisible(true);

        headerLabel.setText("Leaderboard");
        headerLabel.setFont(new Font("Lato", Font.BOLD, 20));
        addMargin(headerLabel, 7, 10, 0, 10);

        JButton rankingButton = new JButton("Open full ranking");
        styleButton(rankingButton);
        rankingButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton okButton = new JButton("New Game");
        styleButton(okButton);
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton submitButton = new JButton("Main Menu");
        styleButton(submitButton);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayStartView();
            }
        });

        JButton cancelButton = new JButton("Exit");
        styleButton(cancelButton);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        controlPanel.add(rankingButton);
        // spacing between objects
        controlPanel.add(Box.createVerticalStrut(20));

        controlPanel.add(okButton);
        // spacing between objects
        controlPanel.add(Box.createVerticalStrut(20));

        controlPanel.add(submitButton);
        controlPanel.add(Box.createVerticalStrut(20));

        controlPanel.add(cancelButton);

        addMargin(controlPanel, 0, 0, 20, 0);

        mainFrame.setVisible(true);
    }

    @Override
    public void displayQuestionView(Question question) {

    }

    @Override
    public void displayEndView(Score score) {
        JLabel headerLabel;
        JPanel controlPanel;

        clearScreen();
        mainFrame.setLayout(new BorderLayout(20, 60));
        mainFrame.getContentPane().setBackground(Color.decode("#FFFFFF"));

        headerLabel = new JLabel("",JLabel.CENTER);

        controlPanel = new JPanel();
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

        JButton okButton = new JButton("New Game");
        styleButton(okButton);
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton submitButton = new JButton("Leaderboard");
        styleButton(submitButton);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton cancelButton = new JButton("Exit");
        styleButton(cancelButton);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        controlPanel.add(okButton);
        // spacing between objects
        controlPanel.add(Box.createVerticalStrut(20));

        controlPanel.add(submitButton);
        controlPanel.add(Box.createVerticalStrut(20));

        controlPanel.add(cancelButton);

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
}
