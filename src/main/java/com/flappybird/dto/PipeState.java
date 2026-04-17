package com.flappybird.dto;

public class PipeState {
    private double x;
    private double y;
    private boolean passed;

    public PipeState() {}

    public PipeState(double x, double y, boolean passed) {
        this.x = x;
        this.y = y;
        this.passed = passed;
    }

    // Getters and Setters
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }
}