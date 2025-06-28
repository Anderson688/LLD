package catalog.model;

import java.util.UUID;

public class Seat {
    private final String id;
    private final int row;
    private final int col;

    public Seat(int row, int col) {
        this.id = UUID.randomUUID().toString();
        this.row = row;
        this.col = col;
    }

    public String getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "Row=" + row + ", Col=" + col;
    }
}