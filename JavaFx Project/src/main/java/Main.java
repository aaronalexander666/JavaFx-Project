package com.cts.javafxdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // 1. Initialize Navigation & Session
            NavigationService.setPrimaryStage(primaryStage);

            // 2. Thick Health Check: Ensure the mapping layer is reachable
            if (!DatabaseConnection.testConnection()) {
                showCriticalError("Database Connection Failed",
                        "Could not connect to the Hotel Management Database.\n" +
                                "Please ensure MySQL is running on port 3306.");
                return;
            }

            // 3. Load Initial View (Login)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            // Future is thick: Map global styles here
            // scene.getStylesheets().add(getClass().getResource("styles/global.css").toExternalForm());

            primaryStage.setTitle("Hotel Management System - 2026 Edition");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Startup Error: " + e.getMessage());
            e.printStackTrace();
            showCriticalError("Application Error", "An unexpected error occurred during startup.");
        }
    }

    private void showCriticalError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        // JavaFX launch entry
        launch(args);
    }
}