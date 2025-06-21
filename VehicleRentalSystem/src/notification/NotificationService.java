package notification;

import booking.Booking;
import booking.BookingEvent;
import booking.IBookingObserver;
import core.RentalSystemManager;
import user.User;

public class NotificationService implements IBookingObserver {
    private INotificationStrategy notificationStrategy;
    private final RentalSystemManager manager = RentalSystemManager.getInstance();

    public NotificationService(INotificationStrategy notificationStrategy) {
        this.notificationStrategy = notificationStrategy;
    }

    public void setNotificationStrategy(INotificationStrategy notificationStrategy) {
        this.notificationStrategy = notificationStrategy;
    }

    @Override
    public void update(BookingEvent event, Booking booking) {
        String recipient = manager.getUserById(booking.getUserId())
                .map(User::getEmail)
                .orElse("unknown_user@example.com");
        String message = "";

        switch (event) {
            case BOOKING_CREATED:
                message = "Your booking " + booking.getBookingId() + " has been created! Total: " + booking.getTotalCost();
                break;
            case BOOKING_CONFIRMED:
                message = "Your booking " + booking.getBookingId() + " is confirmed. Vehicle: " + booking.getVehicleId();
                break;
            case BOOKING_CANCELLED:
                message = "Your booking " + booking.getBookingId() + " has been cancelled.";
                break;
            case VEHICLE_PICKED_UP:
                message = "Vehicle " + booking.getVehicleId() + " for booking " + booking.getBookingId() + " has been picked up.";
                break;
            case VEHICLE_DROPPED_OFF:
                message = "Vehicle for booking " + booking.getBookingId() + " has been dropped off. Thank you!";
                break;
        }
        notificationStrategy.sendNotification(recipient, message);
    }
}