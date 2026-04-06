package com.cts.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.cts.javafxdemo.models.User;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cmbUserType;
    @FXML private Label lblMessage;
    @FXML private Button btnLogin;
    @FXML private Hyperlink lnkRegister;

    private final AuthService authService = new AuthService();

    @FXML
    public void initialize() {
        cmbUserType.getItems().addAll("Customer", "Staff");
        cmbUserType.setValue("Customer");
    }

    @FXML
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();
        String userType = cmbUserType.getValue();

        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Please enter username and password.");
            return;
        }

        try {
            boolean success = authService.login(username, password, userType.toLowerCase());
            if (success) {
                // Navigate based on user type
                if ("staff".equals(userType.toLowerCase())) {
                    NavigationService.navigateTo("staffDashboard.fxml", "Staff Dashboard");
                } else {
                    NavigationService.navigateTo("ui/CustomerDashboard.fxml", "Customer Dashboard");
                }
            } else {
                lblMessage.setText("Invalid credentials.");
            }
        } catch (Exception e) {
            lblMessage.setText("Login failed: " + e.getMessage());
        }
    }

    @FXML
    private void handleShowRegister() {
        NavigationService.navigateTo("register.fxml", "Register");
    }
}