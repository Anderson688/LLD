package booking;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Booking {
    private final String bookingId;
    private final String userId;
    private final String vehicleId;
    private final String pickUpLocationId;
    private final String dropOffLocationId;
    private final LocalDateTime pickUpDateTime;
    private final LocalDateTime dropOffDateTime;
    private BigDecimal totalCost;
    private BookingStatus status;

    public Booking(String userId, String vehicleId, String pickUpLocationId, String dropOffLocationId,
                   LocalDateTime pickUpDateTime, LocalDateTime dropOffDateTime) {
        this.bookingId = UUID.randomUUID().toString();
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.pickUpLocationId = pickUpLocationId;
        this.dropOffLocationId = dropOffLocationId;
        this.pickUpDateTime = pickUpDateTime;
        this.dropOffDateTime = dropOffDateTime;
        this.status = BookingStatus.PENDING; // Initial status
    }

    // Getters
    public String getBookingId() { return bookingId; }
    public String getUserId() { return userId; }
    public String getVehicleId() { return vehicleId; }
    public String getPickUpLocationId() { return pickUpLocationId; }
    public String getDropOffLocationId() { return dropOffLocationId; }
    public LocalDateTime getPickUpDateTime() { return pickUpDateTime; }
    public LocalDateTime getDropOffDateTime() { return dropOffDateTime; }
    public BigDecimal getTotalCost() { return totalCost; }
    public BookingStatus getStatus() { return status; }

    // Setters for mutable properties (like status and total cost)
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public void setStatus(BookingStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Booking{" + "id='" + bookingId + '\'' + ", userId='" + userId + '\'' + ", vehicleId='" + vehicleId + '\'' + ", status=" + status + ", totalCost=" + totalCost + '}';
    }
}