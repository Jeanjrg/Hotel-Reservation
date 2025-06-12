module com.vscode {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.vscode to javafx.fxml;
    exports com.vscode;
}
