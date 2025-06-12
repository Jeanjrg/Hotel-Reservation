package com.vscode;

import javafx.fxml.FXML;

public class ContactUsController {

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