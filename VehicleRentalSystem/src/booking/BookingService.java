package booking;

import core.RentalSystemManager;
import cost.*;
import vehicle.Vehicle;
import vehicle.VehicleStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookingService implements IBookingSubject {
    private final RentalSystemManager manager;
    private final List<IBookingObserver> observers = new ArrayList<>();

    public BookingService(RentalSystemManager manager) {
        this.manager = manager;
    }

    // Observer Subject implementations
    @Override
    public void addObserver(IBookingObserver observer) {
        observers.add(observer);
        System.out.println("Observer added: " + observer.getClass().getSimpleName());
    }

    @Override
    public void removeObserver(IBookingObserver observer) {
        observers.remove(observer);
        System.out.println("Observer removed: " + observer.getClass().getSimpleName());
    }

    @Override
    public void notifyObservers(BookingEvent event, Booking booking) {
        System.out.println("Notifying observers about event: " + event + " for booking " + booking.getBookingId());
        for (IBookingObserver observer : observers) {
            observer.update(event, booking);
        }
    }

    // Create booking using Decorator for cost calculation
    public Booking createBooking(String userId, String vehicleId, String pickUpLocationId, String dropOffLocationId,
                                 LocalDateTime pickUpDateTime, LocalDateTime dropOffDateTime, List<String> addons) {
        // Validate inputs
        manager.getUserById(userId).orElseThrow(() -> new IllegalArgumentException("User not found."));
        Vehicle vehicle = manager.getVehicleById(vehicleId).orElseThrow(() -> new IllegalArgumentException("Vehicle not found."));
        manager.getLocationById(pickUpLocationId).orElseThrow(() -> new IllegalArgumentException("Pick-up location not found."));
        manager.getLocationById(dropOffLocationId).orElseThrow(() -> new IllegalArgumentException("Drop-off location not found."));

        if (pickUpDateTime.isAfter(dropOffDateTime)) {
            throw new IllegalArgumentException("Pick-up date cannot be after drop-off date.");
        }
        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            throw new IllegalStateException("Vehicle is not available for booking.");
        }

        // Calculate cost using Decorator Pattern
        long numberOfDays = ChronoUnit.DAYS.between(pickUpDateTime.toLocalDate(), dropOffDateTime.toLocalDate());
        if (numberOfDays == 0) numberOfDays = 1; // Minimum 1 day charge

        IRentalCostComponent currentCost = new BaseRentalCost(vehicle.getDailyRate(), numberOfDays);

        for (String addon : addons) {
            switch (addon.toLowerCase()) {
                case "gps":
                    currentCost = new GPSDecorator(currentCost, numberOfDays);
                    break;
                case "childseat":
                    currentCost = new ChildSeatDecorator(currentCost);
                    break;
                case "insurance":
                    currentCost = new InsuranceDecorator(currentCost);
                    break;
                default:
                    System.out.println("Warning: Unknown add-on requested: " + addon);
            }
        }

        BigDecimal totalCost = currentCost.calculateCost();
        String costDescription = currentCost.getDescription();
        System.out.println("Calculated Booking Cost Breakdown: " + costDescription);


        Booking newBooking = new Booking(userId, vehicleId, pickUpLocationId, dropOffLocationId, pickUpDateTime, dropOffDateTime);
        newBooking.setTotalCost(totalCost);
        manager.addBooking(newBooking);

        // Update vehicle status
        manager.updateVehicleStatus(vehicleId, VehicleStatus.RENTED); // Mark as rented immediately for simplicity

        notifyObservers(BookingEvent.BOOKING_CREATED, newBooking);
        return newBooking;
    }

    public void confirmBooking(String bookingId) {
        manager.getBookingById(bookingId).ifPresent(booking -> {
            if (booking.getStatus() == BookingStatus.PENDING) {
                booking.setStatus(BookingStatus.CONFIRMED);
                System.out.println("Booking " + booking.getBookingId() + " confirmed.");
                notifyObservers(BookingEvent.BOOKING_CONFIRMED, booking);
            } else {
                System.out.println("Booking " + booking.getBookingId() + " cannot be confirmed from status " + booking.getStatus());
            }
        });
    }

    public void cancelBooking(String bookingId) {
        manager.getBookingById(bookingId).ifPresent(booking -> {
            if (booking.getStatus() == BookingStatus.PENDING || booking.getStatus() == BookingStatus.CONFIRMED) {
                booking.setStatus(BookingStatus.CANCELLED);
                manager.updateVehicleStatus(booking.getVehicleId(), VehicleStatus.AVAILABLE);
                System.out.println("Booking " + booking.getBookingId() + " cancelled.");
                notifyObservers(BookingEvent.BOOKING_CANCELLED, booking);
            } else {
                System.out.println("Booking " + booking.getBookingId() + " cannot be cancelled from status " + booking.getStatus());
            }
        });
    }

    public void pickUpVehicle(String bookingId) {
        manager.getBookingById(bookingId).ifPresent(booking -> {
            if (booking.getStatus() == BookingStatus.CONFIRMED) {
                booking.setStatus(BookingStatus.PICKED_UP);
                manager.updateVehicleStatus(booking.getVehicleId(), VehicleStatus.RENTED); // Ensure it's marked as rented
                System.out.println("Vehicle for booking " + booking.getBookingId() + " picked up.");
                notifyObservers(BookingEvent.VEHICLE_PICKED_UP, booking);
            } else {
                System.out.println("Cannot pick up vehicle for booking " + booking.getBookingId() + " from status " + booking.getStatus());
            }
        });
    }

    public void dropOffVehicle(String bookingId, String actualDropOffLocationId) {
        manager.getBookingById(bookingId).ifPresent(booking -> {
            if (booking.getStatus() == BookingStatus.PICKED_UP) {
                booking.setStatus(BookingStatus.COMPLETED);
                manager.updateVehicleStatus(booking.getVehicleId(), VehicleStatus.AVAILABLE); // Make available again
                manager.getVehicleById(booking.getVehicleId())
                        .ifPresent(v -> v.setCurrentLocationId(actualDropOffLocationId)); // Update vehicle location
                System.out.println("Vehicle for booking " + booking.getBookingId() + " dropped off.");
                notifyObservers(BookingEvent.VEHICLE_DROPPED_OFF, booking);
            } else {
                System.out.println("Cannot drop off vehicle for booking " + booking.getBookingId() + " from status " + booking.getStatus());
            }
        });
    }
}