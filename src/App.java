package src;

import src.constants.GameConstants;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

/**
 * Main application entry point for Flappy Bird game.
 * Sets up the game window and initializes the game panel.
 */
public class App {
    public static void main(String[] args) throws IOException {
        if (isHeadlessEnvironment()) {
            startHttpServer();
        } else {
            startGame();
        }
    }

    private static boolean isHeadlessEnvironment() {
        return System.getenv("HEADLESS") != null;
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Paths.get("application.properties")));
        } catch (Exception e) {
            System.out.println("Could not load application.properties, using defaults");
        }
        return props;
    }

    private static int getServerPort() {
        Properties props = loadProperties();
        String portProperty = props.getProperty("server.port", "${PORT:8080}");

        // Handle property placeholder syntax ${PORT:8080}
        if (portProperty.contains("${PORT:")) {
            String defaultPort = portProperty.replaceAll(".*\\$\\{PORT:(\\d+)}.*", "$1");
            return Integer.parseInt(System.getenv().getOrDefault("PORT", defaultPort));
        }

        // Handle direct port number
        return Integer.parseInt(portProperty);
    }

    private static void startHttpServer() throws IOException {
        int port = getServerPort();
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
        server.createContext("/", new RootHandler());
        server.setExecutor(null); // creates a default executor
        System.out.println("Server is listening on port " + port + "...");
        server.start();
    }

    private static void startGame() {
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
