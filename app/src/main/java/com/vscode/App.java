package com.vscode;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXML("login")); // Awali dengan login.fxml
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws Exception {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws Exception {
        return new javafx.fxml.FXMLLoader(App.class.getResource(fxml + ".fxml")).load();
    }

    public static void main(String[] args) {
        launch();
    }
}