package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        //View textView = new TextView();

        Color BUTTON_COLOUR1 = Color.WHITE;
        int BUTTON_HEIGHT = 75;
        int BUTTON_WIDTH = 200;
        final int TEXTFIELD_HEIGHT = 400;
        int TEXTFIELD_WIDTH = 50;
        String SECONDS_PER_MINUTE = "Seconds to Minutes or Minutes to Seconds";
        String POUNDS_PER_KILOGRAM = "Pounds to Kilograms or Kilograms to Pounds";
        int WIDTH = 400;
        int HEIGHT = 400;

        // instance fields
        JButton button1;
        JButton button2;
        JFrame frame;
        JTextField textInput;
        JPanel panel;

        frame = new JFrame("Centering example");
        panel = new JPanel();
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Establish button dimensions.
        Dimension buttonDimension = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);

        // Establish Textfield dimensions.
        Dimension textDimension = new Dimension(TEXTFIELD_HEIGHT, TEXTFIELD_WIDTH);

        // Create and add the first button.
        button1 = new JButton(SECONDS_PER_MINUTE);
        button1.setPreferredSize(buttonDimension);
        button1.setMinimumSize(buttonDimension);
        button1.setMaximumSize(buttonDimension);
        button1.setBackground(BUTTON_COLOUR1);
        panel.add(button1);

        // Create and add the second button.
        button2 = new JButton(POUNDS_PER_KILOGRAM);
        button2.setPreferredSize(buttonDimension);
        button2.setMinimumSize(buttonDimension);
        button2.setMaximumSize(buttonDimension);
        button2.setBackground(BUTTON_COLOUR1);
        panel.add(button2);

        // Create an input text field.
        textInput = new JTextField(20);
        textInput.setPreferredSize(textDimension);
        textInput.setMinimumSize(textDimension);
        textInput.setMaximumSize(textDimension);
        textInput.setHorizontalAlignment(JTextField.CENTER);

        panel.add(textInput, BorderLayout.CENTER);
        String string = textInput.getText();

        frame.add(panel);
        // Display the frame and text field.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
