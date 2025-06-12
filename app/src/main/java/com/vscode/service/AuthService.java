package com.vscode.service;

import com.vscode.db.JsonDatabase;
import com.vscode.model.User;
import com.vscode.model.Guest;


import java.util.List;

public class AuthService {
private List<User> users;
public AuthService() {
    users = JsonDatabase.loadUsers();
}

public User login(String username, String password) {
    for (User user : users) {
        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            return user;
        }
    }
    return null;
}
}

