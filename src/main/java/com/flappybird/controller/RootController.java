package com.flappybird.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.*;
import java.awt.*;

@Controller
public class RootController {

    // Game constants (matching the original game)
    private static final int BOARD_WIDTH = 360;
    private static final int BOARD_HEIGHT = 640;
    private static final String WINDOW_TITLE = "Flappy Bird";

    /**
     * Root endpoint - launches the game UI
     */
    @GetMapping("/")
    public String launchGame() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(WINDOW_TITLE);
            frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Create a simple game panel
            JPanel gamePanel = new JPanel() {
                private int birdY = BOARD_HEIGHT / 2;
                private int velocityY = 0;
                private int score = 0;
                private javax.swing.Timer gameTimer;

                {
                    setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
                    setFocusable(true);
                    setLayout(null); // Use null layout for precise positioning
                    addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyPressed(java.awt.event.KeyEvent e) {
                            if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE) {
                                velocityY = -9;
                            }
                        }
                    });

                    gameTimer = new javax.swing.Timer(16, e -> {
                        velocityY += 1; // gravity
                        birdY += velocityY;
                        if (birdY > BOARD_HEIGHT || birdY < 0) {
                            gameTimer.stop();
                            JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);
                        }
                        repaint();
                    });
                    gameTimer.start();
                }

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(Color.CYAN);
                    g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
                    g.setColor(Color.YELLOW);
                    g.fillOval(BOARD_WIDTH / 8, birdY, 34, 24);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 20));
                    g.drawString("Score: " + score, 10, 30);
                }
            };
            
            // Make sure the panel is visible and has proper size
            gamePanel.setVisible(true);
            gamePanel.setSize(BOARD_WIDTH, BOARD_HEIGHT);

            frame.add(gamePanel);
            frame.pack();
            gamePanel.requestFocus();
            frame.setVisible(true);
        });

        return "redirect:/api/flappybird/launch";
    }
}