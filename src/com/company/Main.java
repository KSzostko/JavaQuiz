package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {
        //View textView = new TextView();

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
        okButton.setBackground(Color.decode("#6200EE"));
        okButton.setForeground(Color.decode("#FFFFFF"));
        okButton.setBorderPainted(false);
        okButton.setFont(new Font("Lato", Font.PLAIN, 15));
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton submitButton = new JButton("Leaderboard");
        submitButton.setBackground(Color.decode("#6200EE"));
        submitButton.setForeground(Color.decode("#FFFFFF"));
        submitButton.setBorderPainted(false);
        submitButton.setFont(new Font("Lato", Font.PLAIN, 15));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton cancelButton = new JButton("Exit");
        cancelButton.setBackground(Color.decode("#6200EE"));
        cancelButton.setForeground(Color.decode("#FFFFFF"));
        cancelButton.setBorderPainted(false);
        cancelButton.setFont(new Font("Lato", Font.PLAIN, 15));
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        controlPanel.add(okButton);
        // spacing between objects
        controlPanel.add(Box.createVerticalStrut(20));

        controlPanel.add(submitButton);
        controlPanel.add(Box.createVerticalStrut(20));

        controlPanel.add(cancelButton);

        mainFrame.setVisible(true);
    }
}
