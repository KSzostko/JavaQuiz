package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GuiView extends View {
    @Override
    public void displayStartView() {
        JFrame mainFrame;
        JLabel headerLabel;
        JPanel controlPanel;

        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(600,500);
        mainFrame.setLayout(new BorderLayout(20, 60));
        mainFrame.getContentPane().setBackground(Color.decode("#FFFFFF"));

        headerLabel = new JLabel("",JLabel.CENTER);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.WHITE);

        mainFrame.add(headerLabel, BorderLayout.PAGE_START);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);

        headerLabel.setText("Welcome to the Quiz app!");
        headerLabel.setFont(new Font("Lato", Font.BOLD, 20));
        Border border = headerLabel.getBorder();
        Border margin = new EmptyBorder(50, 10, 10, 10);
        headerLabel.setBorder(new CompoundBorder(border, margin));

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

    @Override
    public void displayQuizTypeView() {

    }

    @Override
    public void displayLeadersView() {

    }

    @Override
    public void displayQuestionView(Question question) {

    }

    @Override
    public void displayEndView(Score score) {

    }

    private void styleButton(JButton button) {
        button.setBackground(Color.decode("#6200EE"));
        button.setForeground(Color.decode("#FFFFFF"));
        button.setBorderPainted(false);
        button.setFont(new Font("Lato", Font.PLAIN, 15));
    }
}
