package store;

import java.util.UUID;

public class RentalLocation {
    private final String locationId;
    private final String name;
    private final String address;

    public RentalLocation(String name, String address) {
        this.locationId = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
    }

    // Getters
    public String getLocationId() { return locationId; }
    public String getName() { return name; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
        return "Location{" + "id='" + locationId + '\'' + ", name='" + name + '\'' + '}';
    }
}