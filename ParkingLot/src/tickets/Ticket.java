package tickets;

import parkingspots.ParkingSpot;
import payment.PaymentStatus;
import vehicles.Vehicle;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Ticket {

    private static final AtomicInteger idCounter = new AtomicInteger();

    private final int id;

    private final LocalDateTime entryTime;

    private final ParkingSpot parkingSpot;

    private final Vehicle vehicle;

    private PaymentStatus paymentStatus;

    public Ticket(ParkingSpot parkingSpot, Vehicle vehicle) {
        this.id = idCounter.incrementAndGet();
        this.entryTime = LocalDateTime.now();
        this.parkingSpot = parkingSpot;
        this.vehicle = vehicle;
        this.paymentStatus = PaymentStatus.UNPAID;
    }

    public int getId() { return id; }

    public LocalDateTime getEntryTime() { return entryTime; }

    public Vehicle getVehicle() { return vehicle; }

    public ParkingSpot getParkingSpot() { return parkingSpot; }

    public void setPaymentStatus(PaymentStatus status) { this.paymentStatus = status; }
}