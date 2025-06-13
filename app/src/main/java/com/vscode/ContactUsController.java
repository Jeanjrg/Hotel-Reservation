package com.vscode;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ContactUsController {

    @FXML
    private Label userNameLabel;
    private static String userName = "";

    public static void setUserName(String name) {
        userName = name;
    }

    @FXML
    private void initialize() {
        if (userNameLabel != null) {
            userNameLabel.setText("Welcome, " + userName + "!");
        }
    }
    
    @FXML
    private void switchToHome() {
        try {
            App.setRoot("home");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToAbout() {
        try {
            App.setRoot("about");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}