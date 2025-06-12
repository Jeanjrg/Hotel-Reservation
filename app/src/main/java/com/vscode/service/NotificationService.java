package com.vscode.service;

public class NotificationService {
public static void showInfo(String message) {
System.out.println("[INFO] " + message);
}

public static void showError(String message) {
    System.err.println("[ERROR] " + message);
}
}

