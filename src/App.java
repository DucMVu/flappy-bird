package src;

import src.constants.GameConstants;

import javax.swing.*;

/**
 * Main application entry point for Flappy Bird game.
 * Sets up the game window and initializes the game panel.
 */
public class App {
    public static void main(String[] args) {
        // Create the main game window
        JFrame frame = new JFrame(GameConstants.WINDOW_TITLE);
        frame.setSize(GameConstants.BOARD_WIDTH, GameConstants.BOARD_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and add the game panel
        FlappyBirdGame flappyBirdGame = new FlappyBirdGame();
        frame.add(flappyBirdGame);
        frame.pack();
        flappyBirdGame.requestFocus();
        frame.setVisible(true);
    }
}

