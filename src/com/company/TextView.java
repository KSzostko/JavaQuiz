package com.company;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class TextView extends View {
    @Override
    public void displayStartView() {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();

        Terminal terminal = null;
        try {
            terminal = defaultTerminalFactory.createTerminal();
            final TextGraphics textGraphics = terminal.newTextGraphics();

            terminal.enterPrivateMode();
            terminal.clearScreen();

            textGraphics.putString(2, 1, "Siema witaj w quizie");
            terminal.flush();

            KeyStroke keyStroke = terminal.readInput();
            while(keyStroke.getKeyType() != KeyType.Escape) {
                keyStroke = terminal.readInput();
            }

            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(terminal != null) {
                try {
                    terminal.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void displayLeadersView() {

    }

    @Override
    public void displayQuestionView(Question question) {

    }

    @Override
    public void displayEndView() {

    }
}
