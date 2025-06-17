package com.vscode;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @FXML
    private Button standard;
    @FXML
    private Button superior;
    @FXML
    private Button deluxe;
    @FXML
    private Button juniorSuite;
    @FXML
    private Button suite;
    @FXML
    private Button presidentialSuite;

    private static String userName = "";

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
        updateRoomDisplay("Standard Room", "/com/vscode/images/StandardRoomView.jpg");
        updateRoomStatuses();
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

    // Ambil fasilitas dari Room.java (polimorfisme)
    private List<String> getFacilitiesFromRoomClass(String type) {
        Room roomObj;
        switch (type) {
            case "Standard Room":
                roomObj = new StandardRoom();
                break;
            case "Superior Room":
                roomObj = new SuperiorRoom();
                break;
            case "Deluxe Room":
                roomObj = new DeluxeRoom();
                break;
            case "Junior Suite":
                roomObj = new JuniorSuite();
                break;
            case "Suite":
                roomObj = new Suite();
                break;
            case "Presidential Suite":
                roomObj = new PresidentialSuite();
                break;
            default:
                return new ArrayList<>();
        }
        List<String> facilities = roomObj.getFacilities();
        if (facilities == null) facilities = new ArrayList<>();
        return facilities;
    }

    private void updateRoomDisplay(String type, String imagePath) {
        Map<String, Object> roomData = getRoomDataFromJson(type);
        if (roomData != null) {
            roomType.setText((String) roomData.get("roomType"));
            // Set fasilitas dari Room.java (bukan dari JSON)
            if (facilitiesListView != null) {
                List<String> facilities = getFacilitiesFromRoomClass(type);
                if (facilities == null) facilities = new ArrayList<>();
                facilitiesListView.getItems().setAll(facilities);
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

        // Hitung total price
        long days = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
        int pricePerNight = 0;
        try {
            ObjectMapper mapper = new ObjectMapper();
            File roomsFile = new File("database/Rooms.json");
            List<Map<String, Object>> rooms = mapper.readValue(roomsFile, new TypeReference<List<Map<String, Object>>>() {});
            for (Map<String, Object> room : rooms) {
                if (currentRoomType.equals(room.get("roomType")) && selectedRoomNumber.equals(String.valueOf(room.get("roomNumber")))) {
                    pricePerNight = (Integer) room.get("price");
                    break;
                }
            }
        } catch (Exception e) {
            pricePerNight = 0;
        }
        long totalPrice = pricePerNight * days;

        // Cek overlap booking
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("database/HistoryReservation.json");
            List<Map<String, Object>> history;
            if (file.exists() && file.length() > 0) {
                history = mapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});
            } else {
                history = new ArrayList<>();
            }
            for (Map<String, Object> res : history) {
                if (currentRoomType.equals(res.get("roomType")) && selectedRoomNumber.equals(String.valueOf(res.get("roomNumber")))) {
                    LocalDate existingCheckIn = LocalDate.parse((String) res.get("checkIn"));
                    LocalDate existingCheckOut = LocalDate.parse((String) res.get("checkOut"));
                    // Cek overlap: (A < D) && (C < B)
                    if (!checkOut.isBefore(existingCheckIn) && !existingCheckOut.isBefore(checkIn)) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Kamar Tidak Tersedia");
                        alert.setHeaderText(null);
                        alert.setContentText("Kamar ini sudah dipesan pada rentang tanggal tersebut!");
                        alert.showAndWait();
                        return;
                    }
                }
            }

            // Pop up konfirmasi pemesanan dengan total price
            Alert confirm = new Alert(AlertType.CONFIRMATION);
            confirm.setTitle("Konfirmasi Pemesanan");
            confirm.setHeaderText("Pesan Kamar?");
            confirm.setContentText(
                "Tipe Kamar: " + currentRoomType +
                "\nNomor Kamar: " + selectedRoomNumber +
                "\nCheck-in: " + checkIn +
                "\nCheck-out: " + checkOut +
                "\nTotal malam: " + days +
                "\nHarga per malam: Rp " + String.format("%,d", pricePerNight) +
                "\nTotal harga: Rp " + String.format("%,d", totalPrice) +
                "\n\nLanjutkan pemesanan?"
            );
            Optional<javafx.scene.control.ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
                Map<String, Object> reservation = new java.util.HashMap<>();
                reservation.put("name", userNameLabel.getText().replace("Welcome, ", "").replace("!", "").trim());
                reservation.put("roomType", currentRoomType);
                reservation.put("roomNumber", selectedRoomNumber);
                reservation.put("checkIn", checkIn.toString());
                reservation.put("checkOut", checkOut.toString());
                reservation.put("totalPrice", totalPrice);
                history.add(reservation);
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, history);

                // Update status kamar di Rooms.json
                updateRoomStatusAfterBooking(currentRoomType, selectedRoomNumber, checkOut);

                Alert success = new Alert(AlertType.INFORMATION);
                success.setTitle("Berhasil");
                success.setHeaderText(null);
                success.setContentText("Pemesanan kamar berhasil disimpan!");
                success.showAndWait();
            }
        } catch (Exception e) {
            Alert error = new Alert(AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Gagal menyimpan pemesanan!");
            error.showAndWait();
            e.printStackTrace();
        }
    }

    private void updateRoomStatusAfterBooking(String roomType, String roomNumber, LocalDate checkOut) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File roomsFile = new File("database/Rooms.json");
            List<Map<String, Object>> rooms = mapper.readValue(roomsFile, new TypeReference<List<Map<String, Object>>>() {});
            for (Map<String, Object> room : rooms) {
                if (roomType.equals(room.get("roomType")) && roomNumber.equals(String.valueOf(room.get("roomNumber")))) {
                    // Set status to Not Available if checkOut >= today
                    if (!checkOut.isBefore(LocalDate.now())) {
                        room.put("status", "Not Available");
                    } else {
                        room.put("status", "Available");
                    }
                    break;
                }
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(roomsFile, rooms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateRoomStatuses() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File roomsFile = new File("database/Rooms.json");
            File historyFile = new File("database/HistoryReservation.json");
            if (!roomsFile.exists() || !historyFile.exists()) return;

            List<Map<String, Object>> rooms = mapper.readValue(roomsFile, new TypeReference<List<Map<String, Object>>>() {});
            List<Map<String, Object>> history = mapper.readValue(historyFile, new TypeReference<List<Map<String, Object>>>() {});

            for (Map<String, Object> room : rooms) {
                String roomType = (String) room.get("roomType");
                String roomNumber = String.valueOf(room.get("roomNumber"));
                boolean isBooked = false;
                for (Map<String, Object> res : history) {
                    if (roomType.equals(res.get("roomType")) && roomNumber.equals(String.valueOf(res.get("roomNumber")))) {
                        LocalDate checkOutDate = LocalDate.parse((String) res.get("checkOut"));
                        if (!checkOutDate.isBefore(LocalDate.now())) {
                            isBooked = true;
                            break;
                        }
                    }
                }
                room.put("status", isBooked ? "Not Available" : "Available");
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(roomsFile, rooms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToStandard() {
        roomComboBox.getItems().clear();
        roomComboBox.getItems().addAll("101", "102", "103", "104", "105", "106", "107", "108", "109", "110");
        roomComboBox.getSelectionModel().clearSelection();
        updateRoomDisplay("Standard Room", "/com/vscode/images/StandardRoomView.jpg");
        standard.setStyle("-fx-background-color: none; -fx-border-color: black; -fx-border-radius: 10; -fx-border-width: 5;");
        superior.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        deluxe.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        juniorSuite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        suite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        presidentialSuite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
    }

    @FXML
    private void switchToSuperior() {
        roomComboBox.getItems().clear();
        roomComboBox.getItems().addAll("201", "202", "203", "204", "205", "206", "207", "208", "209", "210");
        roomComboBox.getSelectionModel().clearSelection();
        updateRoomDisplay("Superior Room", "/com/vscode/images/SuperiorRoomView.jpg");
        standard.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        superior.setStyle("-fx-background-color: none; -fx-border-color: black; -fx-border-radius: 10; -fx-border-width: 5;");
        deluxe.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        juniorSuite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        suite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        presidentialSuite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
    }

    @FXML
    private void switchToDeluxe() {
        roomComboBox.getItems().clear();
        roomComboBox.getItems().addAll("301", "302", "303", "304", "305");
        roomComboBox.getSelectionModel().clearSelection();
        updateRoomDisplay("Deluxe Room", "/com/vscode/images/DeluxeRoomView.jpg");
        standard.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        superior.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        deluxe.setStyle("-fx-background-color: none; -fx-border-color: black; -fx-border-radius: 10; -fx-border-width: 5;");
        juniorSuite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        suite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        presidentialSuite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
    }

    @FXML
    private void switchToJuniorSuite() {
        roomComboBox.getItems().clear();
        roomComboBox.getItems().addAll("401", "402", "403");
        roomComboBox.getSelectionModel().clearSelection();
        updateRoomDisplay("Junior Suite", "/com/vscode/images/JuniorSuiteView.jpg");
        standard.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        superior.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        deluxe.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        juniorSuite.setStyle("-fx-background-color: none; -fx-border-color: black; -fx-border-radius: 10; -fx-border-width: 5;");
        suite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        presidentialSuite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
    }

    @FXML
    private void switchToSuite() {
        roomComboBox.getItems().clear();
        roomComboBox.getItems().addAll("501", "502");
        roomComboBox.getSelectionModel().clearSelection();
        updateRoomDisplay("Suite", "/com/vscode/images/SuiteView.jpg");
        standard.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        superior.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        deluxe.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        juniorSuite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        suite.setStyle("-fx-background-color: none; -fx-border-color: black; -fx-border-radius: 10; -fx-border-width: 5;");
        presidentialSuite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
    }

    @FXML
    private void switchToPresidential() {
        roomComboBox.getItems().clear();
        roomComboBox.getItems().addAll("601");
        roomComboBox.getSelectionModel().clearSelection();
        updateRoomDisplay("Presidential Suite", "/com/vscode/images/PresidentialSuiteView.jpg");
        standard.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        superior.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        deluxe.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        juniorSuite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        suite.setStyle("-fx-background-color: none; -fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 5;");
        presidentialSuite.setStyle("-fx-background-color: none; -fx-border-color: black; -fx-border-radius: 10; -fx-border-width: 5;");
    }
}