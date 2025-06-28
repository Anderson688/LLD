package catalog.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Show {
    private final String id;
    private final Movie movie;
    private final Screen screen;
    private final LocalDateTime startTime;
    private final List<ShowSeat> seats;

    public Show(Movie movie, Screen screen, LocalDateTime startTime, List<ShowSeat> seats) {
        this.id = UUID.randomUUID().toString();
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.seats = seats;
    }

    public String getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public List<ShowSeat> getSeats() {
        return seats;
    }
}