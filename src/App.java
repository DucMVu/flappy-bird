package src;

import src.constants.GameConstants;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

/**
 * Main application entry point for Flappy Bird game.
 * Sets up the game window and initializes the game panel.
 */
public class App {
    public static void main(String[] args) throws IOException {
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

        // Start HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new RootHandler());
        server.setExecutor(null); // creates a default executor
        System.out.println("Server is listening on port 8080...");
        server.start();
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Welcome to Flappy Bird Java HTTP Server!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
