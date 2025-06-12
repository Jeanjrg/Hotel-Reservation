package com.vscode.service;

import com.vscode.db.JsonDatabase;
import com.vscode.model.Guest;
import com.vscode.model.Reservation;
import com.vscode.model.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservationService {
private List<Reservation> reservations;
public ReservationService() {
    this.reservations = JsonDatabase.loadReservations();
}

public Reservation createReservation(Guest guest, List<Room> selectedRooms, LocalDate checkIn, LocalDate checkOut) {
    String id = UUID.randomUUID().toString();
    Reservation reservation = new Reservation(id, guest, selectedRooms, checkIn, checkOut);
    reservations.add(reservation);
    JsonDatabase.saveReservations(reservations);

    // set room status to unavailable
    for (Room room : selectedRooms) {
        room.setAvailable(false);
    }
    JsonDatabase.saveRooms(JsonDatabase.loadRooms()); // or re-save the updated room list

    return reservation;
}

public List<Reservation> getAllReservations() {
    return reservations;
}
}
