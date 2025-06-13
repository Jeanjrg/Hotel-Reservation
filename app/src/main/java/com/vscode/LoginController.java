package com.vscode;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<User> users = mapper.readValue(
                new File("database/User.json"),
                new TypeReference<List<User>>() {}
            );

            for (User user : users) {
                if (user.username.equals(username) && user.password.equals(password)) {
                    HomeController.setUserName(user.name);
                    ContactUsController.setUserName(user.name);
                    AboutController.setUserName(user.name);
                    RoomController.setUserName(user.name);

                    App.setRoot("home");
                    return;
                }
            }
            errorLabel.setText("Username atau password salah!");
        } catch (Exception e) {
            errorLabel.setText("Terjadi error saat login.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister() {
        // Contoh: tampilkan pesan atau nanti bisa diarahkan ke scene register
        errorLabel.setText("Fitur register belum tersedia.");
        // Atau bisa load scene register jika sudah ada
        // TODO: Implementasi scene register jika diperlukan
    }
}