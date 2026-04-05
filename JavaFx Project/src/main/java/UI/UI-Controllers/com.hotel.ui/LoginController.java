package com.hotel.app; // This must match the folder path

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private RadioButton customerRadio;
    @FXML private RadioButton staffRadio;
    @FXML private Label statusLabel;
    @FXML private ToggleGroup userTypeGroup;

    @FXML
    private void handleLogin() {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        RadioButton selected = (RadioButton) userTypeGroup.getSelectedToggle();

        if (selected != null) {
            System.out.println("Attempting login for: " + user + " as " + selected.getText());
            // Add your DatabaseConnection logic here
        } else {
            statusLabel.setText("Please select a User Type.");
        }
    }

    @FXML
    private void handleRegister() {
        System.out.println("Navigating to Register...");
    }
}