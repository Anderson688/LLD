import booking.Booking;
import booking.BookingService;
import core.RentalSystemManager;
import notification.EmailNotificationStrategy;
import notification.NotificationService;
import notification.SMSNotificationStrategy;
import payment.*;
import store.RentalLocation;
import user.User;
import user.UserService;
import vehicle.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Vehicle Rental System Demo (Focused Patterns) ---");

        // --- Singleton Pattern ---
        // Get the single instance of the RentalSystemManager
        RentalSystemManager manager = RentalSystemManager.getInstance();
        System.out.println("Manager instance obtained. Hash: " + manager.hashCode());
        RentalSystemManager anotherManagerInstance = RentalSystemManager.getInstance();
        System.out.println("Another manager instance obtained. Hash: " + anotherManagerInstance.hashCode());
        assert manager == anotherManagerInstance; // Confirms it's the same instance

        // --- Initialize Services with Singleton Manager ---
        UserService userService = new UserService(manager);
        VehicleService vehicleService = new VehicleService(manager);
        BookingService bookingService = new BookingService(manager);
        PaymentService paymentService = new PaymentService(manager);

        // --- Observer Pattern ---
        // Create NotificationService and register it as an observer
        NotificationService emailNotificationService = new NotificationService(new EmailNotificationStrategy());
        bookingService.addObserver(emailNotificationService);

        NotificationService smsNotificationService = new NotificationService(new SMSNotificationStrategy());
        bookingService.addObserver(smsNotificationService);

        System.out.println("\n--- Setup Complete ---");

        // --- User Operations ---
        User customer1 = userService.registerCustomer("john_doe", "john@example.com", "pass123", "John", "Doe");
        User customer2 = userService.registerCustomer("jane_doe", "jane@example.com", "pass123", "Jane", "Doe");
        userService.loginUser("john_doe", "pass123");

        // --- Location Operations ---
        RentalLocation downtownLoc = new RentalLocation("Downtown Branch", "123 Main St");
        RentalLocation airportLoc = new RentalLocation("Airport Office", "456 Airport Rd");
        manager.addLocation(downtownLoc);
        manager.addLocation(airportLoc);

        // --- Vehicle Creation using Factory Patterns ---
        System.out.println("\n--- Vehicle Creation with Factory ---");
        Vehicle basicCar = vehicleService.addVehicle(VehicleType.SEDAN, "Honda", "Civic", 2022, "SF-001", new BigDecimal("40.00"), downtownLoc.getLocationId());
        Vehicle basicSUV = vehicleService.addVehicle(VehicleType.SUV, "Nissan", "Rogue", 2021, "SF-002", new BigDecimal("60.00"), airportLoc.getLocationId());
        Vehicle econCar = vehicleService.addVehicle(VehicleType.SEDAN, "Toyota", "Corolla", 2023, "AF-E01", new BigDecimal("30.00"), downtownLoc.getLocationId());
        Vehicle luxSUV = vehicleService.addVehicle(VehicleType.SUV, "BMW", "X5", 2024, "AF-L01", new BigDecimal("120.00"), airportLoc.getLocationId());
        basicCar.displayDetails();
        basicSUV.displayDetails();
        econCar.displayDetails();
        luxSUV.displayDetails();

        // --- Vehicle Search using Strategy Pattern ---
        System.out.println("\n--- Vehicle Search with Strategy ---");
        System.out.println("Searching for vehicles at Downtown Branch:");
        List<Vehicle> downtownVehicles = vehicleService.searchVehicles(new SearchByLocationStrategy(), "Downtown Branch");
        downtownVehicles.forEach(Vehicle::displayDetails);

        System.out.println("\nSearching for SUV type vehicles:");
        List<Vehicle> suvVehicles = vehicleService.searchVehicles(new SearchByTypeStrategy(), "SUV");
        suvVehicles.forEach(Vehicle::displayDetails);


        // --- Booking Creation & Cost Calculation using Decorator Pattern ---
        System.out.println("\n--- Booking Creation & Cost with Decorator ---");
        LocalDateTime pickupTime1 = LocalDateTime.now().plusDays(3);
        LocalDateTime dropoffTime1 = pickupTime1.plusDays(5); // 5 days rental

        // Booking with GPS and Child Seat
        Booking booking1 = bookingService.createBooking(
                customer1.getUserId(), basicCar.getVehicleId(),
                downtownLoc.getLocationId(), airportLoc.getLocationId(),
                pickupTime1, dropoffTime1, List.of("gps", "childseat")
        );
        System.out.println("Created " + booking1);

        // Booking with Insurance
        LocalDateTime pickupTime2 = LocalDateTime.now().plusDays(7);
        LocalDateTime dropoffTime2 = pickupTime2.plusDays(2); // 2 days rental
        Booking booking2 = bookingService.createBooking(
                customer2.getUserId(), luxSUV.getVehicleId(),
                airportLoc.getLocationId(), airportLoc.getLocationId(),
                pickupTime2, dropoffTime2, List.of("insurance")
        );
        System.out.println("Created " + booking2);


        // --- Payment Processing using Strategy Pattern ---
        System.out.println("\n--- Payment Processing with Strategy ---");
        IPaymentStrategy creditCardStrategy = new CreditCardPaymentStrategy("4111-XXXX-XXXX-1111");
        IPaymentStrategy payPalStrategy = new PayPalPaymentStrategy("john.doe@paypal.com");

        PaymentTransaction tx1 = paymentService.processPayment(booking1.getBookingId(), booking1.getTotalCost(), creditCardStrategy);
        System.out.println("Payment 1 result: " + tx1.isSuccess());

        PaymentTransaction tx2 = paymentService.processPayment(booking2.getBookingId(), booking2.getTotalCost(), payPalStrategy);
        System.out.println("Payment 2 result: " + tx2.isSuccess());


        // --- Booking Status Changes & Observer Pattern (Notifications) ---
        System.out.println("\n--- Booking Status Changes (Observer notifications will follow) ---");

        System.out.println("Attempting to confirm booking1:");
        bookingService.confirmBooking(booking1.getBookingId());

        System.out.println("\nAttempting to pick up vehicle for booking1:");
        bookingService.pickUpVehicle(booking1.getBookingId());

        System.out.println("\nAttempting to drop off vehicle for booking1:");
        bookingService.dropOffVehicle(booking1.getBookingId(), airportLoc.getLocationId());

        System.out.println("\nAttempting to cancel booking2:");
        bookingService.cancelBooking(booking2.getBookingId());

        // Demonstrate changing notification strategy
        System.out.println("\n--- Changing Notification Strategy for future notifications ---");
        emailNotificationService.setNotificationStrategy(new SMSNotificationStrategy()); // emailNotificationService will now send SMS
        Booking booking3 = bookingService.createBooking(
                customer1.getUserId(), econCar.getVehicleId(),
                downtownLoc.getLocationId(), downtownLoc.getLocationId(),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), List.of()
        );
        System.out.println("Created " + booking3 + ". Check logs for SMS notification for this and subsequent events.");
        bookingService.confirmBooking(booking3.getBookingId());

        System.out.println("\n--- Demo End ---");
    }
}