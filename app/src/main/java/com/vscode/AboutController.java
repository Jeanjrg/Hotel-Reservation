package com.vscode;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AboutController {

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
    private void switchToContactUs() {
        try {
            App.setRoot("contactus");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}