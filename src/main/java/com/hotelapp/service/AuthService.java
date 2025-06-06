
package com.hotelapp.service;

public class AuthService {
    public boolean login(String username, String password) {
        // Simulate authentication logic
        return "user".equals(username) && "pass".equals(password);
    }
}
