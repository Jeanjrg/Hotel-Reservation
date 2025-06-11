package java.com.hotelapp.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class DashboardController {

    // Event handler umum untuk mouse enter (semua tombol menu)
    @FXML
    private void hoverEnter(MouseEvent event) {
        if (event.getSource() instanceof Button btn) {
            btn.setStyle(btn.getStyle() + "; -fx-text-fill: #111827; -fx-font-weight: bold;");
        }
    }

    // Event handler umum untuk mouse exit (semua tombol menu)
    @FXML
    private void hoverExit(MouseEvent event) {
        if (event.getSource() instanceof Button btn) {
            // Reset ke style default
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6b7280; -fx-font-size: 16px;");
        }
    }

    // Hover untuk tombol utama "Create New Reservation"
    @FXML
    private void buttonHoverEnter(MouseEvent event) {
        if (event.getSource() instanceof Button btn) {
            btn.setStyle(btn.getStyle() + "; -fx-background-color: #374151;");
        }
    }

    @FXML
    private void buttonHoverExit(MouseEvent event) {
        if (event.getSource() instanceof Button btn) {
            btn.setStyle("-fx-background-color: #111827; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: 600; -fx-padding: 14 28; -fx-background-radius: 12;");
        }
    }
}
