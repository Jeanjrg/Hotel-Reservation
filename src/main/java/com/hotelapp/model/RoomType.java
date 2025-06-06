
package com.hotelapp.model;

public class RoomType {
    private String typeName;
    private double pricePerHour;

    public RoomType(String typeName, double pricePerHour) {
        this.typeName = typeName;
        this.pricePerHour = pricePerHour;
    }

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }

    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }
}
