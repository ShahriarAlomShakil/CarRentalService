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
            // Load the main FXML file (will be created in Phase 3)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();
            
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
            
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
            
            // For Phase 1, create a simple window if FXML is not available
            createSimpleWindow(primaryStage);
        }
    }
    
    /**
     * Creates a simple window for Phase 1 testing
     * This will be replaced with proper FXML in Phase 3
     */
    private void createSimpleWindow(Stage primaryStage) {
        try {
            javafx.scene.control.Label label = new javafx.scene.control.Label("Car Rental System - Phase 1 Setup Complete");
            label.setStyle("-fx-font-size: 18px; -fx-padding: 50px;");
            
            javafx.scene.layout.VBox root = new javafx.scene.layout.VBox();
            root.getChildren().add(label);
            root.setAlignment(javafx.geometry.Pos.CENTER);
            
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            primaryStage.setTitle(APP_TITLE + " - Phase 1");
            primaryStage.setScene(scene);
            primaryStage.show();
            
            System.out.println("Phase 1 setup complete - Simple window displayed");
        } catch (Exception e) {
            System.err.println("Error creating simple window: " + e.getMessage());
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
