package com.cts.javafxdemo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.io.IOException;
import java.net.URL;

/**
 * Enhanced Navigation Service for unified scene management.
 * Optimized for "Big Mapping" and data-heavy transitions.
 */
public class NavigationService {

    private static Stage primaryStage;
    private static final String FXML_PATH = "/com/cts/javafxdemo/";

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Standard navigation to a new view.
     */
    public static void navigateTo(String fxmlFile, String title) {
        try {
            FXMLLoader loader = getLoader(fxmlFile);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            // Future is thick: apply global theme mapping here
            // scene.getStylesheets().add(NavigationService.class.getResource(FXML_PATH + "styles.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (IOException e) {
            handleError("Navigation", e);
        }
    }

    /**
     * Advanced navigation that allows passing data to the target controller.
     * Use this when mapping a specific Booking or Room to a new view.
     */
    public static <T> T navigateAndGetController(String fxmlFile, String title) {
        try {
            FXMLLoader loader = getLoader(fxmlFile);
            Parent root = loader.load();

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle(title);
            primaryStage.centerOnScreen();
            primaryStage.show();

            return loader.getController();
        } catch (IOException e) {
            handleError("Data-Transition", e);
            return null;
        }
    }

    /**
     * Opens a modal window for focused tasks (e.g., confirming a booking).
     */
    public static void openModal(String fxmlFile, String title) {
        try {
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage); // Links the modal to the main window
            dialogStage.setTitle(title);

            Parent root = getLoader(fxmlFile).load();
            dialogStage.setScene(new Scene(root));
            dialogStage.centerOnScreen();
            dialogStage.showAndWait();

        } catch (IOException e) {
            handleError("Modal", e);
        }
    }

    private static FXMLLoader getLoader(String fxmlFile) {
        URL location = NavigationService.class.getResource(fxmlFile.startsWith("/") ? fxmlFile : FXML_PATH + fxmlFile);
        if (location == null) {
            throw new RuntimeException("FXML file not found: " + fxmlFile);
        }
        return new FXMLLoader(location);
    }

    private static void handleError(String context, Exception e) {
        System.err.println(context + " Error: " + e.getMessage());
        e.printStackTrace();
    }
}