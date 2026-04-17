package com.flappybird.controller;

import com.flappybird.dto.GameState;
import com.flappybird.service.FlappyBirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.awt.*;

@RestController
@RequestMapping("/api/flappybird")
public class FlappyBirdController {

    // Game constants (matching the original game)
    private static final int BOARD_WIDTH = 360;
    private static final int BOARD_HEIGHT = 640;
    private static final String WINDOW_TITLE = "Flappy Bird";

    @Autowired
    private FlappyBirdService gameService;

    /**
     * Start a new game
     */
    @PostMapping("/start")
    public ResponseEntity<GameState> startGame() {
        GameState state = gameService.startGame();
        return ResponseEntity.ok(state);
    }

    /**
     * Make the bird jump (flap)
     */
    @PostMapping("/jump")
    public ResponseEntity<GameState> jump() {
        GameState state = gameService.jump();
        return ResponseEntity.ok(state);
    }

    /**
     * Update game state (one tick)
     */
    @PostMapping("/update")
    public ResponseEntity<GameState> update() {
        GameState state = gameService.update();
        return ResponseEntity.ok(state);
    }

    /**
     * Get current game state
     */
    @GetMapping("/state")
    public ResponseEntity<GameState> getGameState() {
        GameState state = gameService.getGameState();
        return ResponseEntity.ok(state);
    }

    /**
     * Get game constants
     */
    @GetMapping("/constants")
    public ResponseEntity<GameState> getConstants() {
        GameState state = gameService.getConstants();
        return ResponseEntity.ok(state);
    }

    /**
     * Open the game UI (JFrame)
     */
    @GetMapping("/launch")
    public ResponseEntity<String> launchGameUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(WINDOW_TITLE);
            frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Create a simple game panel since we can't import from src package
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

        return ResponseEntity.ok("Game UI launched!");
    }
}