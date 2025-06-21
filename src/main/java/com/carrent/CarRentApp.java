package com.carrent;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Application class for Car Rental System
 * 
 * This is the entry point of the JavaFX application.
 * It extends Application and overrides the start method to set up the primary stage.
 * 
 * @author Car Rental Team
 * @version 1.0
 */
public class CarRentApp extends Application {
      private static final String APP_TITLE = "Car Rental System";
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            System.out.println("Starting Car Rental Application...");
            System.out.println("Loading FXML from: " + getClass().getResource("/fxml/MainView.fxml"));
            
            // Load the main FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();
            
            System.out.println("FXML loaded successfully");
            
            // Create the scene
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            
            // Configure the primary stage
            primaryStage.setTitle(APP_TITLE);
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.centerOnScreen();
            
            // Show the application
            primaryStage.show();
            
            System.out.println("Application started successfully with main UI");
            
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
            
            // Re-throw the exception instead of falling back to simple window
            throw e;
        }
    }

    /**
     * Main method - entry point of the application
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Starting Car Rental Application...");
        launch(args);
    }
}
