package com.vscode.model;

import java.time.LocalDate;
import java.util.List;

public class Reservation {
    private String id;
    private Guest guest;
    private List<Room> rooms;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Reservation(String id, Guest guest, List<Room> rooms, LocalDate checkInDate, LocalDate checkOutDate) {
        this.id = id;
        this.guest = guest;
        this.rooms = rooms;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public String getId() {
        return id;
    }

    public Guest getGuest() {
        return guest;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
}
