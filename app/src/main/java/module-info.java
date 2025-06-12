module com.vscode {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core; 

    opens com.vscode to javafx.fxml;
    exports com.vscode;
}
