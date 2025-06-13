package com.vscode;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

public class StandardRoomController {
    @FXML
    private ListView<String> roomListView;
    @FXML
    private DatePicker checkInPicker;
    @FXML
    private DatePicker checkOutPicker;
    @FXML
    private Button paymentButton;

    private String currentUsername;

    public void setCurrentUsername(String username) {
    this.currentUsername = username;
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

    if (checkIn == null || checkOut == null) {
        System.out.println("Tanggal belum dipilih!");
        return;
    }

    try {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("database/HistoryReservation.json");

        // Buat objek reservasi baru
        HistoryReservation reservation = new HistoryReservation();
        reservation.username = currentUsername;
        reservation.roomType = "Standard Room";
        reservation.checkIn = checkIn.toString();
        reservation.checkOut = checkOut.toString();

        // Baca data lama (jika file sudah ada)
        List<HistoryReservation> history;
        if (file.exists() && file.length() > 0) {
            history = mapper.readValue(file, new TypeReference<List<HistoryReservation>>() {});
        } else {
            history = new java.util.ArrayList<>();
        }

        // Tambahkan reservasi baru ke list
        history.add(reservation);

        // Simpan kembali ke file
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, history);
        System.out.println("Reservasi berhasil disimpan ke HistoryReservation.json");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}