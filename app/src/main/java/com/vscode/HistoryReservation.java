package com.vscode;

public class HistoryReservation {
    public String username;
    public String roomType;
    public String checkIn;
    public String checkOut;

    public HistoryReservation() {
        // Konstruktor default diperlukan untuk Jackson
    }

    public HistoryReservation(String username, String roomType, String checkIn, String checkOut) {
        this.username = username;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}