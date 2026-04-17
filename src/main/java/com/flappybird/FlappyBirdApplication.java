package com.flappybird;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlappyBirdApplication {

    public static void main(String[] args) {
        // Ensure we're not in headless mode so we can show JFrame
        System.setProperty("java.awt.headless", "false");
        
        SpringApplication.run(FlappyBirdApplication.class, args);
    }
}
