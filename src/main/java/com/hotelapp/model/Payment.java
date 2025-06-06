
package com.hotelapp.model;

public class Payment {
    private int id;
    private int reservationId;
    private double amount;
    private String status;  // e.g. "pending", "paid", "failed"

    public Payment(int id, int reservationId, double amount, String status) {
        this.id = id;
        this.reservationId = reservationId;
        this.amount = amount;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
