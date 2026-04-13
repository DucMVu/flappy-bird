package src.constants;

/**
 * Central constants file for the Flappy Bird game.
 * Contains all configuration values for easy modification.
 */
public class GameConstants {
    // Board dimensions
    public static final int BOARD_WIDTH = 360;
    public static final int BOARD_HEIGHT = 640;

    // Bird properties
    public static final int BIRD_X = BOARD_WIDTH / 8;
    public static final int BIRD_Y = BOARD_HEIGHT / 2;
    public static final int BIRD_WIDTH = 34;
    public static final int BIRD_HEIGHT = 24;

    // Pipe properties
    public static final int PIPE_X = BOARD_WIDTH;
    public static final int PIPE_Y = 0;
    public static final int PIPE_WIDTH = 64;
    public static final int PIPE_HEIGHT = 512;
    public static final int PIPE_SPAWN_INTERVAL = 1500; // milliseconds

    // Game physics
    public static final int VELOCITY_X = -4; // Pipe movement speed (simulates bird moving right)
    public static final int GRAVITY = 1;
    public static final int JUMP_STRENGTH = -9;

    // Game timing
    public static final int GAME_FPS = 60;
    public static final int GAME_LOOP_DELAY = 1000 / GAME_FPS; // milliseconds per frame

    // Opening between pipes
    public static final int PIPE_OPENING_SPACE = BOARD_HEIGHT / 4;

    // Score
    public static final double SCORE_PER_PIPE = 0.5; // 0.5 * 2 pipes = 1 point per obstacle

    // Image resources
    public static final String BACKGROUND_IMAGE = "images/flappybirdbg.png";
    public static final String BIRD_IMAGE = "images/flappybird.png";
    public static final String TOP_PIPE_IMAGE = "images/toppipe.png";
    public static final String BOTTOM_PIPE_IMAGE = "images/bottompipe.png";

    // UI properties
    public static final String WINDOW_TITLE = "Flappy Bird";
    public static final int SCORE_FONT_SIZE = 30;
    public static final String SCORE_FONT = "Arial";
    public static final int SCORE_X = 15;
    public static final int SCORE_Y = 60;
    public static final int HIGH_SCORE_OFFSET_X = 15;
    public static final int HIGH_SCORE_OFFSET_Y = 60;

    // Private constructor to prevent instantiation
    private GameConstants() {
        throw new AssertionError("Cannot instantiate constants class");
    }
}

