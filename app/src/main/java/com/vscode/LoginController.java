package com.vscode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

import java.io.File;
import java.util.List;

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

                    // Ganti root scene utama, bukan buat stage baru
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