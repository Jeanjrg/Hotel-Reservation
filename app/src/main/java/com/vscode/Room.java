package com.vscode;

public abstract class Room {
    public String roomNumber;
    public abstract String getRoomType();
    public abstract int getPrice();
    public abstract String getFacilities();
}