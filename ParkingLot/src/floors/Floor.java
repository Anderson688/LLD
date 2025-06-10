package floors;

import parkingspots.ParkingSpot;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Floor {
    private final int level;
    private final List<ParkingSpot> parkingSpots;

    public Floor(int level) {
        this.level = level;
        this.parkingSpots = new CopyOnWriteArrayList<>();
    }

    public void addParkingSpot(ParkingSpot spot) {
        parkingSpots.add(spot);
    }

    public List<ParkingSpot> getParkingSpots() {
        return Collections.unmodifiableList(parkingSpots);
    }
}