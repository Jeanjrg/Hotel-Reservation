# Hotel Reservation JavaFX Application

This is a simple Hotel Reservation application built with Java, JavaFX, and JSON for data storage.  
The project demonstrates object-oriented programming (OOP) concepts such as inheritance and polymorphism, and uses JavaFX for the user interface.

## Features

- User login and registration
- Room selection with multiple room types (Standard, Superior, Deluxe, Junior Suite, Suite, Presidential Suite)
- Dynamic display of room facilities, images, and prices based on room type
- Date selection for check-in and check-out (with validation)
- Room number selection via ComboBox
- Reservation confirmation pop-up
- Reservation history saved to JSON file
- Modular code with OOP best practices

## Folder Structure

```
app/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/vscode/
│       │       ├── App.java
│       │       ├── Room.java
│       │       ├── RoomController.java
│       │       ├── User.java
│       │       ├── LoginController.java
│       │       ├── HomeController.java
│       │       └── ... (other controllers)
│       └── resources/
│           └── com/vscode/
│               ├── home.fxml
│               ├── room.fxml
│               ├── images/
│               │   ├── StandardRoomView.jpg
│               │   ├── DeluxeRoomView.jpg
│               │   └── ... (other images)
│               └── database/
│                   ├── Rooms.json
│                   ├── User.json
│                   └── HistoryReservation.json
```

## How to Run

1. **Clone this repository**
2. **Open the project in your favorite Java IDE (e.g., VS Code, IntelliJ, NetBeans)**
3. **Make sure you have Java 11+ and JavaFX SDK installed**
4. **Build the project**
5. **Run the `App.java` file**

## JSON Data

- **Rooms.json**: Contains room types, facilities, prices, and image paths.
- **User.json**: Stores registered user data.
- **HistoryReservation.json**: Stores reservation history.

## Screenshots

*(Add your own screenshots here)*

## Credits

- JavaFX
- Jackson Databind (for JSON)
- All contributors

---

**This project is for educational purposes and can be extended for more advanced hotel