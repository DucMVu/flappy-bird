package com.flappybird.service;

import com.flappybird.dto.GameState;
import com.flappybird.dto.PipeState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class FlappyBirdService {

    // Game constants (matching the original game)
    private static final int BOARD_WIDTH = 360;
    private static final int BOARD_HEIGHT = 640;
    private static final int BIRD_X = 45; // BOARD_WIDTH / 8
    private static final int BIRD_Y = 320; // BOARD_HEIGHT / 2
    private static final int BIRD_WIDTH = 34;
    private static final int BIRD_HEIGHT = 24;
    private static final int PIPE_WIDTH = 64;
    private static final int PIPE_HEIGHT = 512;
    private static final int VELOCITY_X = -4;
    private static final int GRAVITY = 1;
    private static final int JUMP_STRENGTH = -9;
    private static final int PIPE_OPENING_SPACE = 160; // BOARD_HEIGHT / 4
    private static final double SCORE_PER_PIPE = 0.5;

    // Game state
    private double birdX = BIRD_X;
    private double birdY = BIRD_Y;
    private double velocityY = 0;
    private double score = 0;
    private double highScore = 0;
    private boolean gameOver = false;
    private List<PipeState> pipes = new ArrayList<>();
    private Random random = new Random();

    public GameState startGame() {
        birdX = BIRD_X;
        birdY = BIRD_Y;
        velocityY = 0;
        score = 0;
        gameOver = false;
        pipes.clear();
        return getGameState();
    }

    public GameState jump() {
        if (gameOver) {
            startGame();
            return getGameState();
        }
        velocityY = JUMP_STRENGTH;
        return update();
    }

    public GameState update() {
        if (gameOver) {
            return getGameState();
        }

        // Update bird physics
        velocityY += GRAVITY;
        birdY += velocityY;

        // Prevent bird from going above the screen
        if (birdY < 0) {
            birdY = 0;
            velocityY = 0;
        }

        // Check if bird fell below the screen
        if (birdY > BOARD_HEIGHT) {
            handleGameOver();
            return getGameState();
        }

        // Update pipes
        List<PipeState> pipesToRemove = new ArrayList<>();
        for (PipeState pipe : pipes) {
            pipe.setX(pipe.getX() + VELOCITY_X);

            // Check if bird passed the pipe
            if (!pipe.isPassed() && birdX > pipe.getX() + PIPE_WIDTH) {
                score += SCORE_PER_PIPE;
                pipe.setPassed(true);
            }

            // Check for collision
            if (detectCollision(pipe)) {
                handleGameOver();
                return getGameState();
            }

            // Mark pipes for removal if they're off screen
            if (pipe.getX() + PIPE_WIDTH < 0) {
                pipesToRemove.add(pipe);
            }
        }

        pipes.removeAll(pipesToRemove);

        // Spawn new pipes
        if (pipes.isEmpty() || shouldSpawnPipe()) {
            spawnPipes();
        }

        return getGameState();
    }

    private boolean shouldSpawnPipe() {
        // Find the rightmost pipe
        double rightmostX = 0;
        for (PipeState pipe : pipes) {
            if (pipe.getX() > rightmostX) {
                rightmostX = pipe.getX();
            }
        }
        // Spawn if the rightmost pipe has moved enough
        return rightmostX < BOARD_WIDTH - 150;
    }

    private void spawnPipes() {
        int randomPipeY = (int) (PIPE_HEIGHT / 4 - Math.random() * (PIPE_HEIGHT / 2));

        // Top pipe
        pipes.add(new PipeState(BOARD_WIDTH, randomPipeY, false));

        // Bottom pipe
        pipes.add(new PipeState(BOARD_WIDTH, randomPipeY + PIPE_HEIGHT + PIPE_OPENING_SPACE, false));
    }

    private boolean detectCollision(PipeState pipe) {
        return birdX < pipe.getX() + PIPE_WIDTH &&
               birdX + BIRD_WIDTH > pipe.getX() &&
               birdY < pipe.getY() + PIPE_HEIGHT &&
               birdY + BIRD_HEIGHT > pipe.getY();
    }

    private void handleGameOver() {
        gameOver = true;
        if (score > highScore) {
            highScore = score;
        }
    }

    public GameState getGameState() {
        return new GameState(birdX, birdY, velocityY, score, highScore, gameOver, new ArrayList<>(pipes));
    }

    public GameState getConstants() {
        // Return game constants as a game state for reference
        GameState state = new GameState();
        state.setBirdX(BIRD_X);
        state.setBirdY(BIRD_Y);
        state.setScore(BOARD_WIDTH);
        state.setHighScore(BOARD_HEIGHT);
        return state;
    }
}