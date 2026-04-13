package src;

import src.constants.GameConstants;
import src.models.Bird;
import src.models.Pipe;
import src.utils.CollisionDetector;
import src.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Main game panel for Flappy Bird.
 * Handles rendering, game logic, and user input.
 */
public class FlappyBirdGame extends JPanel implements ActionListener, KeyListener {

    // Game state
    private Bird bird;
    private ArrayList<Pipe> pipes;
    private Random random;
    private double score;
    private double highScore;
    private boolean gameOver;

    // Physics properties
    private int velocityY;
    private static final int VELOCITY_X = GameConstants.VELOCITY_X;

    // Game resources
    private Image backgroundImage;
    private Image topPipeImage;
    private Image bottomPipeImage;

    // Timers
    private Timer gameLoop;
    private Timer placePipeTimer;

    /**
     * Constructor for FlappyBirdGame.
     * Initializes the game panel with all necessary components.
     */
    public FlappyBirdGame() {
        initializePanel();
        loadImages();
        initializeGame();
        startGameTimers();
    }

    /**
     * Initializes the panel properties.
     */
    private void initializePanel() {
        setPreferredSize(new Dimension(GameConstants.BOARD_WIDTH, GameConstants.BOARD_HEIGHT));
        setFocusable(true);
        addKeyListener(this);
    }

    /**
     * Loads all game images from resources.
     */
    private void loadImages() {
        backgroundImage = ImageLoader.loadImage(GameConstants.BACKGROUND_IMAGE);
        Image birdImage = ImageLoader.loadImage(GameConstants.BIRD_IMAGE);
        topPipeImage = ImageLoader.loadImage(GameConstants.TOP_PIPE_IMAGE);
        bottomPipeImage = ImageLoader.loadImage(GameConstants.BOTTOM_PIPE_IMAGE);

        // Initialize bird
        bird = new Bird(
                GameConstants.BIRD_X,
                GameConstants.BIRD_Y,
                GameConstants.BIRD_WIDTH,
                GameConstants.BIRD_HEIGHT,
                birdImage
        );
    }

    /**
     * Initializes game state variables.
     */
    private void initializeGame() {
        pipes = new ArrayList<>();
        random = new Random();
        score = 0;
        highScore = 0;
        gameOver = false;
        velocityY = 0;
    }

    /**
     * Starts the game timers for pipe spawning and game loop.
     */
    private void startGameTimers() {
        // Timer for spawning pipes
        placePipeTimer = new Timer(GameConstants.PIPE_SPAWN_INTERVAL, e -> placePipes());
        placePipeTimer.start();

        // Game loop timer
        gameLoop = new Timer(GameConstants.GAME_LOOP_DELAY, this);
        gameLoop.start();
    }

    /**
     * Creates and adds a pair of pipes (top and bottom) to the game.
     */
    private void placePipes() {
        // Calculate random Y position for top pipe
        int randomPipeY = (int) (GameConstants.PIPE_Y - GameConstants.PIPE_HEIGHT / 4
                - Math.random() * (GameConstants.PIPE_HEIGHT / 2));

        // Create top pipe
        Pipe topPipe = new Pipe(
                GameConstants.PIPE_X,
                randomPipeY,
                GameConstants.PIPE_WIDTH,
                GameConstants.PIPE_HEIGHT,
                topPipeImage
        );
        pipes.add(topPipe);

        // Create bottom pipe
        Pipe bottomPipe = new Pipe(
                GameConstants.PIPE_X,
                randomPipeY + GameConstants.PIPE_HEIGHT + GameConstants.PIPE_OPENING_SPACE,
                GameConstants.PIPE_WIDTH,
                GameConstants.PIPE_HEIGHT,
                bottomPipeImage
        );
        pipes.add(bottomPipe);
    }

    /**
     * Updates game physics and state.
     */
    private void updateGameState() {
        updateBirdPhysics();
        updatePipePositions();
        checkGameOverConditions();
    }

    /**
     * Updates bird position based on velocity and gravity.
     */
    private void updateBirdPhysics() {
        velocityY += GameConstants.GRAVITY;
        bird.setY(bird.getY() + velocityY);

        // Prevent bird from going above the screen
        bird.setY(Math.max(bird.getY(), 0));
    }

    /**
     * Updates pipe positions and checks for collisions and score increments.
     */
    private void updatePipePositions() {
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setX(pipe.getX() + VELOCITY_X);

            // Check if bird passed the pipe
            if (!pipe.isPassed() && bird.getX() > pipe.getX() + pipe.getWidth()) {
                score += GameConstants.SCORE_PER_PIPE;
                pipe.setPassed(true);
            }

            // Check for collision
            if (CollisionDetector.detectCollision(bird, pipe)) {
                gameOver = true;
            }
        }
    }

    /**
     * Checks if game over conditions are met.
     */
    private void checkGameOverConditions() {
        // Check if bird fell below the screen
        if (bird.getY() > GameConstants.BOARD_HEIGHT) {
            gameOver = true;
        }

        // Handle game over
        if (gameOver) {
            handleGameOver();
        }
    }

    /**
     * Handles the game over state.
     */
    private void handleGameOver() {
        // Update high score if current score is higher
        if (score > highScore) {
            highScore = score;
        }

        // Stop timers
        placePipeTimer.stop();
        gameLoop.stop();
    }

    /**
     * Resets the game to initial state.
     */
    private void resetGame() {
        bird.setY(GameConstants.BIRD_Y);
        velocityY = 0;
        pipes.clear();
        gameOver = false;
        score = 0;

        // Restart timers
        gameLoop.start();
        placePipeTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Draws all game elements to the screen.
     *
     * @param g the Graphics object for drawing
     */
    private void draw(Graphics g) {
        drawBackground(g);
        drawBird(g);
        drawPipes(g);
        drawScore(g);
    }

    /**
     * Draws the background image.
     *
     * @param g the Graphics object
     */
    private void drawBackground(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, GameConstants.BOARD_WIDTH, GameConstants.BOARD_HEIGHT, null);
    }

    /**
     * Draws the bird.
     *
     * @param g the Graphics object
     */
    private void drawBird(Graphics g) {
        g.drawImage(bird.getImage(), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), null);
    }

    /**
     * Draws all pipes.
     *
     * @param g the Graphics object
     */
    private void drawPipes(Graphics g) {
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.getImage(), pipe.getX(), pipe.getY(), pipe.getWidth(), pipe.getHeight(), null);
        }
    }

    /**
     * Draws the current score and high score.
     *
     * @param g the Graphics object
     */
    private void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font(GameConstants.SCORE_FONT, Font.BOLD, GameConstants.SCORE_FONT_SIZE));

        // Draw current score on top left
        g.drawString(String.valueOf((int) score), GameConstants.SCORE_X, GameConstants.SCORE_Y);

        // Draw high score on top right
        String highScoreText = "High Score: " + (int) highScore;
        int textWidth = g.getFontMetrics().stringWidth(highScoreText);
        g.drawString(highScoreText,
                GameConstants.BOARD_WIDTH - textWidth - GameConstants.HIGH_SCORE_OFFSET_X,
                GameConstants.HIGH_SCORE_OFFSET_Y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Called every game loop iteration
        updateGameState();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = GameConstants.JUMP_STRENGTH;

            // Restart game if game is over
            if (gameOver) {
                resetGame();
            }
        }
    }

    // Unused KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}

