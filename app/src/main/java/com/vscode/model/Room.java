package com.vscode.model;

public class Room {
    private String roomNumber;
    private RoomType roomType;
    private boolean available;

    public Room(String roomNumber, RoomType roomType, boolean available) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.available = available;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return roomNumber + " (" + roomType.getName() + ")";
    }
}
