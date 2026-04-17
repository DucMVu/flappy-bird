package com.flappybird.dto;

import java.util.List;

public class GameState {
    private double birdX;
    private double birdY;
    private double velocityY;
    private double score;
    private double highScore;
    private boolean gameOver;
    private List<PipeState> pipes;

    public GameState() {}

    public GameState(double birdX, double birdY, double velocityY, double score, double highScore, boolean gameOver, List<PipeState> pipes) {
        this.birdX = birdX;
        this.birdY = birdY;
        this.velocityY = velocityY;
        this.score = score;
        this.highScore = highScore;
        this.gameOver = gameOver;
        this.pipes = pipes;
    }

    // Getters and Setters
    public double getBirdX() { return birdX; }
    public void setBirdX(double birdX) { this.birdX = birdX; }

    public double getBirdY() { return birdY; }
    public void setBirdY(double birdY) { this.birdY = birdY; }

    public double getVelocityY() { return velocityY; }
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public double getHighScore() { return highScore; }
    public void setHighScore(double highScore) { this.highScore = highScore; }

    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }

    public List<PipeState> getPipes() { return pipes; }
    public void setPipes(List<PipeState> pipes) { this.pipes = pipes; }
}