package NamanDigital;

import javax.swing.*;
import java.awt.*;
import java.util.*;

    class Room {
        int roomNumber;
        boolean isAvailable = true;
        String guestName;

        Room(int roomNumber) {
            this.roomNumber = roomNumber;
        }
    }

    public class HotelBooking extends JFrame {

        private JTextArea outputArea;
        private JTextField guestNameField, roomNumberField;
        private java.util.List<Room> rooms;

        public HotelBooking() {
            setTitle("Hotel Room Reservation System");
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Initialize rooms
            rooms = new ArrayList<>();
            for (int i = 101; i <= 105; i++) { // 5 rooms (101–105)
                rooms.add(new Room(i));
            }

            // GUI components
            JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
            panel.add(new JLabel("Guest Name:"));
            guestNameField = new JTextField();
            panel.add(guestNameField);

            panel.add(new JLabel("Room Number:"));
            roomNumberField = new JTextField();
            panel.add(roomNumberField);

            JButton checkBtn = new JButton("Check Availability");
            JButton bookBtn = new JButton("Book Room");
            JButton cancelBtn = new JButton("Cancel Booking");
            JButton showBtn = new JButton("Show All Rooms");

            panel.add(checkBtn);
            panel.add(bookBtn);
            panel.add(cancelBtn);
            panel.add(showBtn);

            outputArea = new JTextArea();
            outputArea.setEditable(false);
            JScrollPane scroll = new JScrollPane(outputArea);

            add(panel, BorderLayout.NORTH);
            add(scroll, BorderLayout.CENTER);

            // Action Listeners
            checkBtn.addActionListener(e -> checkAvailability());
            bookBtn.addActionListener(e -> bookRoom());
            cancelBtn.addActionListener(e -> cancelBooking());
            showBtn.addActionListener(e -> showRooms());
        }

        private void checkAvailability() {
            String roomNoText = roomNumberField.getText().trim();
            if (roomNoText.isEmpty()) {
                outputArea.append("Enter room number to check!\n");
                return;
            }

            try {
                int roomNo = Integer.parseInt(roomNoText);
                for (Room r : rooms) {
                    if (r.roomNumber == roomNo) {
                        outputArea.append("Room " + roomNo + " is " +
                                (r.isAvailable ? "Available\n" : "Booked by " + r.guestName + "\n"));
                        return;
                    }
                }
                outputArea.append("Room not found!\n");
            } catch (NumberFormatException e) {
                outputArea.append("Invalid room number! Please enter digits only.\n");
            }
        }


        private void bookRoom() {
            String name = guestNameField.getText().trim();
            String roomNoText = roomNumberField.getText().trim();

            if (name.isEmpty() || roomNoText.isEmpty()) {
                outputArea.append("Enter guest name and room number!\n");
                return;
            }

            int roomNo = Integer.parseInt(roomNoText);
            for (Room r : rooms) {
                if (r.roomNumber == roomNo) {
                    if (r.isAvailable) {
                        r.isAvailable = false;
                        r.guestName = name;
                        outputArea.append("Room " + roomNo + " booked for " + name + "\n");
                    } else {
                        outputArea.append("Room " + roomNo + " is already booked!\n");
                    }
                    return;
                }
            }
            outputArea.append("Room not found!\n");
        }

        private void cancelBooking() {
            String roomNoText = roomNumberField.getText().trim();
            if (roomNoText.isEmpty()) {
                outputArea.append("Enter room number to cancel booking!\n");
                return;
            }

            int roomNo = Integer.parseInt(roomNoText);
            for (Room r : rooms) {
                if (r.roomNumber == roomNo) {
                    if (!r.isAvailable) {
                        outputArea.append("Booking cancelled for " + r.guestName + " in room " + roomNo + "\n");
                        r.isAvailable = true;
                        r.guestName = null;
                    } else {
                        outputArea.append("Room " + roomNo + " is not booked.\n");
                    }
                    return;
                }
            }
            outputArea.append("Room not found!\n");
        }

        private void showRooms() {
            outputArea.append("=== Room Status ===\n");
            for (Room r : rooms) {
                outputArea.append("Room " + r.roomNumber + " : " +
                        (r.isAvailable ? "Available" : "Booked by " + r.guestName) + "\n");
            }
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new HotelBooking().setVisible(true));
        }
    }

