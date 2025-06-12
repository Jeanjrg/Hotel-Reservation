package com.vscode.service;

package com.vscode.service;

import com.vscode.model.Reservation;

import java.util.List;

public class ReportService {
private final List<Reservation> reservations;
public ReportService(List<Reservation> reservations) {
    this.reservations = reservations;
}

public int getTotalReservations() {
    return reservations.size();
}

public double getTotalRevenue() {
    double total = 0.0;
    for (Reservation reservation : reservations) {
        total += reservation.getRooms().stream()
                .mapToDouble(room -> room.getRoomType().getPricePerNight())
                .sum();
    }
    return total;
}
}