package com.vscode;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RoomController {
    @FXML
    private ListView<String> roomListView;
    @FXML
    private DatePicker checkInPicker;
    @FXML
    private DatePicker checkOutPicker;
    @FXML
    private Button paymentButton;
    @FXML
    private Label roomType;
    @FXML
    private ImageView roomImageView;
    @FXML
    private ComboBox<String> roomComboBox;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private ListView<String> facilitiesListView;

    private String currentUsername;
    private static String userName = "";

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    public static void setUserName(String name) {
        userName = name;
    }

    @FXML
    private void initialize() {
        roomComboBox.getItems().clear();
        roomComboBox.getItems().addAll("101", "102", "103", "104", "105", "106", "107", "108", "109", "110");
        roomComboBox.getSelectionModel().clearSelection();
        if (userNameLabel != null) {
            userNameLabel.setText("Welcome, " + userName + "!");
        }
        // Default tampilan Standard Room
        updateRoomDisplay("Standard Room", "/com/vscode/images/StandardRoomView.jpg");
    }

    private Map<String, Object> getRoomDataFromJson(String type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("database/Rooms.json");
            List<Map<String, Object>> rooms = mapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});
            for (Map<String, Object> jsonRoom : rooms) {
                if (type.equalsIgnoreCase((String) jsonRoom.get("roomType"))) {
                    return jsonRoom;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateRoomDisplay(String type, String imagePath) {
        Map<String, Object> roomData = getRoomDataFromJson(type);
        if (roomData != null) {
            roomType.setText((String) roomData.get("roomType"));
            // Set fasilitas jika ada ListView fasilitas
            if (facilitiesListView != null && roomData.get("facilities") instanceof List) {
                facilitiesListView.getItems().setAll((List<String>) roomData.get("facilities"));
            }
            // Set harga jika ada label price
            if (priceLabel != null && roomData.get("price") != null) {
                priceLabel.setText("Rp " + String.format("%,d", (Integer) roomData.get("price")));
            }
        } else {
            roomType.setText(type);
            if (facilitiesListView != null) facilitiesListView.getItems().clear();
            if (priceLabel != null) priceLabel.setText("-");
        }
        // Set gambar
        if (roomImageView != null && imagePath != null) {
            roomImageView.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        }
    }

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

    @FXML
    private void switchToContactUs() {
        try {
            App.setRoot("contactus");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePayment() {
        LocalDate checkIn = checkInPicker.getValue();
        LocalDate checkOut = checkOutPicker.getValue();
        String selectedRoomNumber = roomComboBox.getValue();
        String currentRoomType = roomType.getText();

        if (checkIn == null || checkOut == null || selectedRoomNumber == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Peringatan");
            alert.setHeaderText(null);
            alert.setContentText("Tanggal dan nomor kamar harus dipilih!");
            alert.showAndWait();
            return;
        }

        if (!checkIn.isBefore(checkOut)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Tanggal Tidak Valid");
            alert.setHeaderText(null);
            alert.setContentText("Tanggal check-in harus sebelum tanggal check-out!");
            alert.showAndWait();
            return;
        }

        // Pop up konfirmasi pemesanan
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Konfirmasi Pemesanan");
        confirm.setHeaderText("Pesan Kamar?");
        confirm.setContentText(
            "Tipe Kamar: " + currentRoomType +
            "\nNomor Kamar: " + selectedRoomNumber +
            "\nCheck-in: " + checkIn +
            "\nCheck-out: " + checkOut +
            "\nLanjutkan pemesanan?"
        );
        Optional<javafx.scene.control.ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            // Simpan ke database/JSON
            try {
                ObjectMapper mapper = new ObjectMapper();
                File file = new File("database/HistoryReservation.json");
                List<Map<String, Object>> history;
                if (file.exists() && file.length() > 0) {
                    history = mapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});
                } else {
                    history = new ArrayList<>();
                }
                Map<String, Object> reservation = new java.util.HashMap<>();
                reservation.put("username", currentUsername);
                reservation.put("roomType", currentRoomType);
                reservation.put("roomNumber", selectedRoomNumber);
                reservation.put("checkIn", checkIn.toString());
                reservation.put("checkOut", checkOut.toString());
                history.add(reservation);
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, history);

                Alert success = new Alert(AlertType.INFORMATION);
                success.setTitle("Berhasil");
                success.setHeaderText(null);
                success.setContentText("Pemesanan kamar berhasil disimpan!");
                success.showAndWait();
            } catch (Exception e) {
                Alert error = new Alert(AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText(null);
                error.setContentText("Gagal menyimpan pemesanan!");
                error.showAndWait();
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToStandard() {
        roomComboBox.getItems().clear();
        roomComboBox.getItems().addAll("101", "102", "103", "104", "105", "106", "107", "108", "109", "110");
        roomComboBox.getSelectionModel().clearSelection();
        updateRoomDisplay("Standard Room", "/com/vscode/images/StandardRoomView.jpg");
    }

    @FXML
    private void switchToSuperior() {
        roomComboBox.getItems().clear();
        roomComboBox.getItems().addAll("201", "202", "203", "204", "205", "206", "207", "208", "209", "210");
        roomComboBox.getSelectionModel().clearSelection();
        updateRoomDisplay("Superior Room", "/com/vscode/images/SuperiorRoomView.jpg");
    }

    @FXML
    private void switchToDeluxe() {
        roomComboBox.getItems().clear();
        roomComboBox.getItems().addAll("301", "302", "303", "304", "305");
        roomComboBox.getSelectionModel().clearSelection();
        updateRoomDisplay("Deluxe Room", "/com/vscode/images/DeluxeRoomView.jpg");
    }

    @FXML
    private void switchToJuniorSuite() {
        roomComboBox.getItems().clear();
        roomComboBox.getItems().addAll("401", "402", "403");
        roomComboBox.getSelectionModel().clearSelection();
        updateRoomDisplay("Junior Suite", "/com/vscode/images/JuniorSuiteView.jpg");
    }

    @FXML
    private void switchToSuite() {
        roomComboBox.getItems().clear();
        roomComboBox.getItems().addAll("501", "502");
        roomComboBox.getSelectionModel().clearSelection();
        updateRoomDisplay("Suite", "/com/vscode/images/SuiteView.jpg");
    }

    @FXML
    private void switchToPresidential() {
        roomComboBox.getItems().clear();
        roomComboBox.getItems().addAll("601");
        roomComboBox.getSelectionModel().clearSelection();
        updateRoomDisplay("Presidential Suite", "/com/vscode/images/PresidentialSuiteView.jpg");
    }
}