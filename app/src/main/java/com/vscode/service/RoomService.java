package com.vscode.service;

import com.vscode.db.JsonDatabase;
import com.vscode.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomService {
private List<Room> rooms;
public RoomService() {
    this.rooms = JsonDatabase.loadRooms();
}

public List<Room> getAvailableRooms() {
    List<Room> availableRooms = new ArrayList<>();
    for (Room room : rooms) {
        if (room.isAvailable()) {
            availableRooms.add(room);
        }
    }
    return availableRooms;
}

public Room getRoomByNumber(String number) {
    for (Room room : rooms) {
        if (room.getRoomNumber().equals(number)) {
            return room;
        }
    }
    return null;
}

public void updateRoom(Room updatedRoom) {
    for (int i = 0; i < rooms.size(); i++) {
        if (rooms.get(i).getRoomNumber().equals(updatedRoom.getRoomNumber())) {
            rooms.set(i, updatedRoom);
            break;
        }
    }
    JsonDatabase.saveRooms(rooms);
}
}