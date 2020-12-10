package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        //View textView = new TextView();

        JFrame frame = new JFrame("Button example");

        final JTextField tf = new JTextField();
        tf.setBounds(50, 50, 150, 20);
        frame.add(tf);

        JButton button = new JButton("click");
        button.setBounds(50, 100, 95, 30);
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tf.setText("Welcome to Swing!");
            }
        });
        frame.add(button);

        frame.setSize(400, 500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
