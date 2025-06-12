package com.vscode;

import javafx.fxml.FXML;

public class AboutController {

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