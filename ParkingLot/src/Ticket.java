import java.time.Duration;
import java.time.Instant;

public class Ticket {

    private final Instant entranceTime;

    private final ParkingSpot parkingSpot;

    public Ticket(ParkingSpot parkingSpot) {
        this.entranceTime = Instant.now();
        this.parkingSpot = parkingSpot;
    }

    public long calculateCost() {
        return Duration.between(entranceTime, Instant.now()).toSeconds()*parkingSpot.getCostPerSecond();
    }

    public ParkingSpot getParkingSpot() {
        return this.parkingSpot;
    }
}