package com.cts.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class JavaFxDemoController {
    @FXML
    private Label welcomeText; [cite: 1]

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!"); [cite: 2]
    }
}
