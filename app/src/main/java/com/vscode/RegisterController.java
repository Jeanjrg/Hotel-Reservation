package com.vscode;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RegisterController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleRegister() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("All fields are required!");
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("database/User.json");
            List<Map<String, Object>> users;
            if (file.exists() && file.length() > 0) {
                users = mapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});
            } else {
                users = new ArrayList<>();
            }

            // Check if username already exists
            for (Map<String, Object> user : users) {
                if (username.equals(user.get("username"))) {
                    errorLabel.setText("Username already exists!");
                    return;
                }
            }

            // Add new user
            Map<String, Object> newUser = new java.util.HashMap<>();
            newUser.put("name", name);
            newUser.put("username", username);
            newUser.put("password", password);
            users.add(newUser);

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Registration Success");
            alert.setHeaderText(null);
            alert.setContentText("Registration successful! Please login.");
            alert.showAndWait();

            App.setRoot("login");
        } catch (Exception e) {
            errorLabel.setText("Registration failed!");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            App.setRoot("login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}