package com.vscode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Room {
    protected String roomType;
    protected List<String> facilities;
    protected int price;

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    public String getRoomType() {
        return roomType;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }
    public List<String> getFacilities() {
        return facilities;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getPrice() {
        return price;
    }

    // Factory method untuk membuat Room dari data JSON
    public static Room createRoomFromJson(Map<String, Object> jsonRoom) {
        String type = (String) jsonRoom.get("roomType");
        Room room;
        switch (type) {
            case "Standard Room":
                room = new StandardRoom();
                break;
            case "Superior Room":
                room = new SuperiorRoom();
                break;
            case "Deluxe Room":
                room = new DeluxeRoom();
                break;
            case "Junior Suite":
                room = new JuniorSuite();
                break;
            case "Suite":
                room = new Suite();
                break;
            case "Presidential Suite":
                room = new PresidentialSuite();
                break;
            default:
                throw new IllegalArgumentException("Unknown room type: " + type);
        }
        room.setRoomType(type);
        room.setFacilities((List<String>) jsonRoom.get("facilities"));
        room.setPrice((Integer) jsonRoom.get("price"));
        return room;
    }
}

// Subclass untuk tiap tipe kamar
class StandardRoom extends Room {
    @Override
    public List<String> getFacilities() {
        List<String> facilities = new ArrayList<>();
        facilities.add("1 Queen Bed");
        facilities.add("AC");
        facilities.add("TV");
        facilities.add("WiFi");
        facilities.add("Kamar mandi dalam");
        facilities.add("Air panas");
        facilities.add("Sarapan gratis");
        return facilities;
    }
    @Override
    public int getPrice() {
        return price;
    }
}

class SuperiorRoom extends Room {
    @Override
    public List<String> getFacilities() {
        List<String> facilities = new ArrayList<>();
        facilities.add("1 King Bed");
        facilities.add("AC");
        facilities.add("TV");
        facilities.add("WiFi");
        facilities.add("Kamar mandi dalam");
        facilities.add("Air panas");
        facilities.add("Sarapan gratis");
        facilities.add("Mini Bar");
        return facilities;
    }
    @Override
    public int getPrice() {
        return price;
    }
}

class DeluxeRoom extends Room {
    @Override
    public List<String> getFacilities() {
        List<String> facilities = new ArrayList<>();
        facilities.add("2 Queen Bed");
        facilities.add("AC");
        facilities.add("TV");
        facilities.add("WiFi");
        facilities.add("Kamar mandi dalam");
        facilities.add("Air panas");
        facilities.add("Sarapan gratis");
        facilities.add("Mini Bar");
        facilities.add("Bathtub");
        return facilities;
    }
    @Override
    public int getPrice() {
        return price;
    }
}

class JuniorSuite extends Room {
    @Override
    public List<String> getFacilities() {
        List<String> facilities = new ArrayList<>();
        facilities.add("1 King Bed + 1 Sofa Bed");
        facilities.add("AC");
        facilities.add("TV");
        facilities.add("WiFi");
        facilities.add("Kamar mandi dalam");
        facilities.add("Air panas");
        facilities.add("Sarapan gratis");
        facilities.add("Mini Bar");
        facilities.add("Bathtub");
        facilities.add("Living Room");
        return facilities;
    }
    @Override
    public int getPrice() {
        return price;
    }
}

class Suite extends Room {
    @Override
    public List<String> getFacilities() {
        List<String> facilities = new ArrayList<>();
        facilities.add("2 King Bed");
        facilities.add("AC");
        facilities.add("TV");
        facilities.add("WiFi");
        facilities.add("Kamar mandi dalam");
        facilities.add("Air panas");
        facilities.add("Sarapan gratis");
        facilities.add("Mini Bar");
        facilities.add("Bathtub");
        facilities.add("Living Room");
        facilities.add("Dapur Kecil");
        return facilities;
    }
    @Override
    public int getPrice() {
        return price;
    }
}

class PresidentialSuite extends Room {
    @Override
    public List<String> getFacilities() {
        List<String> facilities = new ArrayList<>();
        facilities.add("2 King Bed + 1 Sofa Bed");
        facilities.add("AC");
        facilities.add("TV");
        facilities.add("WiFi");
        facilities.add("Kamar mandi dalam");
        facilities.add("Air panas");
        facilities.add("Sarapan gratis");
        facilities.add("Mini Bar");
        facilities.add("Bathtub");
        facilities.add("Living Room");
        facilities.add("Dapur Kecil");
        facilities.add("Private Pool");
        facilities.add("Butler Service");
        return facilities;
    }
    @Override
    public int getPrice() {
        return price;
    }
}