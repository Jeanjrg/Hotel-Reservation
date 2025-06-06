
package com.hotelapp.model;

public class Guest extends User {
    private String email;

    public Guest(int id, String username, String password, String email) {
        super(id, username, password);
        this.email = email;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String getRole() {
        return "Guest";
    }
}
