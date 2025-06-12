package com.vscode.model;

public class Payment {
    private String id;
    private Reservation reservation;
    private double amount;
    private String paymentMethod;

    public Payment(String id, Reservation reservation, double amount, String paymentMethod) {
        this.id = id;
        this.reservation = reservation;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public String getId() {
        return id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Overloading example: Pay by amount only (maybe partial payment)
    public void makePayment(double amount) {
        // Process payment logic here
    }

    // Overloading example: Pay full amount
    public void makePayment() {
        makePayment(this.amount);
    }
}
