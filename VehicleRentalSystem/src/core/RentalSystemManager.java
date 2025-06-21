package core;

import booking.Booking;
import booking.BookingStatus;
import payment.PaymentTransaction;
import store.RentalLocation;
import user.User;
import vehicle.Vehicle;
import vehicle.VehicleStatus;

import java.util.*;
import java.util.stream.Collectors;

public class RentalSystemManager {
    private static RentalSystemManager instance;

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Vehicle> vehicles = new HashMap<>();
    private final Map<String, RentalLocation> locations = new HashMap<>();
    private final Map<String, Booking> bookings = new HashMap<>();
    private final Map<String, PaymentTransaction> payments = new HashMap<>();

    // Private constructor to prevent instantiation from outside
    private RentalSystemManager() {
        // Initialize with some default data if needed
        System.out.println("RentalSystemManager: Initializing (Singleton instance created)");
    }

    // Public method to get the singleton instance
    public static synchronized RentalSystemManager getInstance() {
        if (instance == null) {
            instance = new RentalSystemManager();
        }
        return instance;
    }

    // --- User Management ---
    public User registerUser(String username, String email, String passwordHash, String firstName, String lastName) {
        if (users.values().stream().anyMatch(u -> u.getUsername().equals(username) || u.getEmail().equals(email))) {
            throw new IllegalArgumentException("Username or Email already exists.");
        }
        User newUser = new User(username, email, passwordHash, firstName, lastName);
        users.put(newUser.getUserId(), newUser);
        System.out.println("User registered: " + newUser.getUsername());
        return newUser;
    }

    public Optional<User> getUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public Optional<User> getUserByUsername(String username) {
        return users.values().stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    // --- Vehicle Management ---
    public void addVehicle(Vehicle vehicle, String locationId) {
        if (!locations.containsKey(locationId)) {
            throw new IllegalArgumentException("Location not found for vehicle: " + locationId);
        }
        vehicle.setCurrentLocationId(locationId);
        vehicles.put(vehicle.getVehicleId(), vehicle);
        System.out.println("Vehicle added: " + vehicle.getMake() + " " + vehicle.getModel() + " at " + locations.get(locationId).getName());
    }

    public Optional<Vehicle> getVehicleById(String vehicleId) {
        return Optional.ofNullable(vehicles.get(vehicleId));
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles.values());
    }

    public void updateVehicleStatus(String vehicleId, VehicleStatus status) {
        getVehicleById(vehicleId).ifPresent(v -> {
            v.setStatus(status);
            System.out.println("Vehicle " + v.getLicensePlate() + " status updated to " + status);
        });
    }

    // --- Location Management ---
    public void addLocation(RentalLocation location) {
        locations.put(location.getLocationId(), location);
        System.out.println("Location added: " + location.getName());
    }

    public Optional<RentalLocation> getLocationById(String locationId) {
        return Optional.ofNullable(locations.get(locationId));
    }

    public List<RentalLocation> getAllLocations() {
        return new ArrayList<>(locations.values());
    }

    // --- Booking Management ---
    public void addBooking(Booking booking) {
        bookings.put(booking.getBookingId(), booking);
        System.out.println("Booking added: " + booking.getBookingId());
    }

    public Optional<Booking> getBookingById(String bookingId) {
        return Optional.ofNullable(bookings.get(bookingId));
    }

    public List<Booking> getBookingsByUserId(String userId) {
        return bookings.values().stream()
                .filter(b -> b.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public void updateBookingStatus(String bookingId, BookingStatus status) {
        getBookingById(bookingId).ifPresent(b -> {
            b.setStatus(status);
            System.out.println("Booking " + b.getBookingId() + " status updated to " + status);
        });
    }

    // --- Payment Management ---
    public void addPayment(PaymentTransaction payment) {
        payments.put(payment.getTransactionId(), payment);
        System.out.println("Payment recorded: " + payment.getTransactionId());
    }

    public List<PaymentTransaction> getPaymentsForBooking(String bookingId) {
        return payments.values().stream()
                .filter(p -> p.getBookingId().equals(bookingId))
                .collect(Collectors.toList());
    }
}