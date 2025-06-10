public enum VehicleType {

    TWO_WHEELER(10),
    FOUR_WHEELER(20);

    private final int costPerSecond;

    VehicleType(int costPerSecond) {
        this.costPerSecond = costPerSecond;
    }

    public int getCostPerSecond() {
        return this.costPerSecond;
    }
}
