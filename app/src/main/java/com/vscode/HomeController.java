package com.vscode;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController extends User{
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
    private void switchToAbout() throws Exception {
        App.setRoot("about");
    }

    @FXML
    private void switchToHome() throws Exception {
        App.setRoot("home");
    }

    @FXML
    private void switchToContactUs() throws Exception {
        App.setRoot("contactus");
    }

    @FXML
    private void switchToRoom() {
        try {
            App.setRoot("room");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}