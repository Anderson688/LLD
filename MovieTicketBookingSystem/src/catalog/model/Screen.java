package catalog.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Screen {
    private final String id;
    private final String name;
    private final List<Seat> seats;
    private final List<Show> shows;

    public Screen(String name, int numRows, int numCols) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.shows = new ArrayList<>();
        this.seats = new ArrayList<>();
        for (int row = 1; row <= numRows; row++) {
            for (int col = 1; col <= numCols; col++) {
                seats.add(new Seat(row, col));
            }
        }
    }

    public String getId() {
        return id;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void addShow(Show show) {
        this.shows.add(show);
    }

    public List<Show> getShows() {
        return shows;
    }
}