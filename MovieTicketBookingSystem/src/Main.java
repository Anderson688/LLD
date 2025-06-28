import booking.BookingService;
import booking.concurrency.InMemorySeatLockProvider;
import booking.concurrency.SeatLockProvider;
import catalog.model.Cinema;
import catalog.model.Show;
import catalog.model.ShowSeat;
import initializer.DemoSetupService;
import user.model.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Setup
        SeatLockProvider lockProvider = new InMemorySeatLockProvider();
        BookingService bookingService = BookingService.getInstance(lockProvider);
        Cinema cinema = DemoSetupService.setupCinema();

        // Get a specific show
        Show show = cinema.getScreens().get(0).getShows().get(0);
        System.out.println("Booking for movie: " + show.getMovie().getTitle());

        // Create Users
        User user1 = new User("Alice", "alice@example.com");
        User user2 = new User("Bob", "bob@example.com");

        // --- DEMO: CONCURRENT BOOKING ---
        System.out.println("\n--- Concurrent Booking Demo ---");

        // Select the same seats for two different users
        List<ShowSeat> seatsToBookForAlice = show.getSeats().stream()
                .filter(s -> s.getRow() == 2 && (s.getCol() >= 5 && s.getCol() <= 7))
                .collect(Collectors.toList());

        List<ShowSeat> seatsToBookForBob = show.getSeats().stream()
                .filter(s -> s.getRow() == 2 && (s.getCol() >= 6 && s.getCol() <= 8))
                .collect(Collectors.toList());

        System.out.println("Alice wants to book: " + seatsToBookForAlice);
        System.out.println("Bob wants to book: " + seatsToBookForBob);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Task for Alice's booking
        Runnable aliceBookingTask = () -> {
            try {
                System.out.println("Alice trying to book...");
                bookingService.createBooking(user1, show, seatsToBookForAlice);
            } catch (IllegalStateException e) {
                System.out.println("Alice's Booking Failed: " + e.getMessage());
            }
        };

        // Task for Bob's booking
        Runnable bobBookingTask = () -> {
            try {
                // Add a small delay to simulate near-simultaneous requests
//                Thread.sleep(50);
                System.out.println("Bob trying to book...");
                bookingService.createBooking(user2, show, seatsToBookForBob);
            } catch (IllegalStateException e) {
                System.out.println("Bob's Booking Failed: " + e.getMessage());
            }
        };

        executor.submit(aliceBookingTask);
        executor.submit(bobBookingTask);

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("\n--- Final Seat Status ---");
        show.getSeats().stream()
                .filter(s -> s.getRow() == 2 && s.getCol() >= 5 && s.getCol() <= 8)
                .forEach(s -> System.out.println("Seat (" + s + ") Status: " + s.getStatus()));
    }
}