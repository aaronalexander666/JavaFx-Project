Package com.cts.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextArea addressField;
    @FXML private Label statusLabel;

    private final AuthService authService = new AuthService();

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        // Validation
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            updateStatus("Username, password and full name are required", "red");
            return;
        }

        if (password.length() < 4) {
            updateStatus("Password must be at least 4 characters", "red");
            return;
        }

        boolean success = authService.registerCustomer(username, password, fullName, email, phone, address);

        if (success) {
            updateStatus("Registration successful! Please login.", "green");
            // Clear fields
            clearFields();
        } else {
            updateStatus("Registration failed. Username may already exist.", "red");
        }
    }

    @FXML
    private void handleBack() {
        NavigationService.navigateTo("login.fxml", "Hotel Management System");
    }

    private void updateStatus(String message, String color) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: " + color + ";");
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        fullNameField.clear();
        emailField.clear();
        phoneField.clear();
        addressField.clear();
    }
}