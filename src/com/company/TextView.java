package com.company;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
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
            TerminalSize terminalSize = terminal.getTerminalSize();
            final TextGraphics textGraphics = terminal.newTextGraphics();

            terminal.enterPrivateMode();
            terminal.clearScreen();

            String welcomeMessage = "Welcome to the Quiz App!";
            String hintMessage = "Input red characters to select given option";
            String newGame = "New Game";
            String leaderboard = "Leaderboards";
            String exit = "Exit";

            textGraphics.putString(terminalSize.getColumns() / 2 - welcomeMessage.length() / 2, 5, welcomeMessage);

            terminal.setCursorPosition(terminalSize.getColumns() / 2 - newGame.length() / 2, 8);
            terminal.setForegroundColor(TextColor.ANSI.RED);
            terminal.putCharacter('N');
            terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
            terminal.putCharacter('e');
            terminal.putCharacter('w');
            terminal.putCharacter(' ');
            terminal.putCharacter('G');
            terminal.putCharacter('a');
            terminal.putCharacter('m');
            terminal.putCharacter('e');

            terminal.setCursorPosition(terminalSize.getColumns() / 2 - leaderboard.length() / 2, 10);
            terminal.setForegroundColor(TextColor.ANSI.RED);
            terminal.putCharacter('L');
            terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
            terminal.putCharacter('e');
            terminal.putCharacter('a');
            terminal.putCharacter('d');
            terminal.putCharacter('e');
            terminal.putCharacter('r');
            terminal.putCharacter('b');
            terminal.putCharacter('o');
            terminal.putCharacter('a');
            terminal.putCharacter('r');
            terminal.putCharacter('d');
            terminal.putCharacter('s');

            terminal.setCursorPosition(terminalSize.getColumns() / 2 - exit.length() / 2, 12);
            terminal.setForegroundColor(TextColor.ANSI.RED);
            terminal.putCharacter('E');
            terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
            terminal.putCharacter('x');
            terminal.putCharacter('i');
            terminal.putCharacter('t');

            textGraphics.putString(terminalSize.getColumns() / 2 - hintMessage.length() / 2, 15, hintMessage);
            terminal.flush();

            KeyStroke keyStroke = terminal.readInput();
            while(keyStroke.getKeyType() != KeyType.Escape && keyStroke.getKeyType() != KeyType.EOF
                    && keyStroke.getCharacter() != 'e' && keyStroke.getCharacter() != 'E'
                    && keyStroke.getCharacter() != 'n' && keyStroke.getCharacter() != 'N'
                    && keyStroke.getCharacter() != 'l' && keyStroke.getCharacter() != 'L') {
                keyStroke = terminal.readInput();
            }

            // this check can be done in game class
            switch(keyStroke.getCharacter()) {
                case 'n':
                case 'N':
                    // new game screen
                    break;
                case 'l':
                case 'L':
                    // leaderboard screen
                    break;
                case 'e':
                case 'E':
                    // exit game
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
