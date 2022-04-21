package com.example.dqlite2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DQGUIController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onSubmitButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}