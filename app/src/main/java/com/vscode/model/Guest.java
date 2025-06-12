package com.vscode.model;

public class Guest extends User {

    public Guest(String id, String username, String password, String name) {
        super(id, username, password, name);
    }

    @Override
    public String getRole() {
        return "Guest";
    }
}
