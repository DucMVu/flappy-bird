package src;

import src.constants.GameConstants;

import javax.swing.*;

/**
 * Main application entry point for Flappy Bird game.
 * Starts the Swing-based game UI.
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(GameConstants.WINDOW_TITLE);
            frame.setSize(GameConstants.BOARD_WIDTH, GameConstants.BOARD_HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            FlappyBirdGame flappyBirdGame = new FlappyBirdGame();
            frame.add(flappyBirdGame);
            frame.pack();
            flappyBirdGame.requestFocus();
            frame.setVisible(true);
        });
    }
}