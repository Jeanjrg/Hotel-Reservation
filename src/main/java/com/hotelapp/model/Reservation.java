
package com.hotelapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class Reservation {
    private int id;
    private Guest guest;
    private List<Room> rooms;  // support multiple rooms booking
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Reservation(int id, Guest guest, List<Room> rooms, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.id = id;
        this.guest = guest;
        this.rooms = rooms;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }

    public List<Room> getRooms() { return rooms; }
    public void setRooms(List<Room> rooms) { this.rooms = rooms; }

    public LocalDateTime getStartDateTime() { return startDateTime; }
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }

    public LocalDateTime getEndDateTime() { return endDateTime; }
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }
}
