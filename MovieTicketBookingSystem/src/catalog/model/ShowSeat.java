package catalog.model;

public class ShowSeat extends Seat {
    private final String showId;
    private SeatStatus status;
    private double price;
    // Optimistic locking
    private int version;

    public ShowSeat(int row, int col, String showId, double price) {
        super(row, col);
        this.showId = showId;
        this.price = price;
        this.status = SeatStatus.AVAILABLE;
        this.version = 1;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVersion() {
        return version;
    }

    public void incrementVersion() {
        this.version++;
    }
}