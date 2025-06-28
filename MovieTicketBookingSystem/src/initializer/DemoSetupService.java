package initializer;

import catalog.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DemoSetupService {

    public static Cinema setupCinema() {
        Cinema pvr = new Cinema("PVR Forum Mall", "Bengaluru");

        Screen screen1 = new Screen("Audi 1", 5, 10);
        Screen screen2 = new Screen("Audi 2", 6, 12);

        Movie movie1 = new Movie("Dune: Part Two", 166);
        Movie movie2 = new Movie("Oppenheimer", 180);

        // Create ShowSeats for Show 1
        List<ShowSeat> show1Seats = screen1.getSeats().stream()
                .map(seat -> new ShowSeat(seat.getRow(), seat.getCol(), "show1", 250.0))
                .collect(Collectors.toList());

        Show show1 = new Show(movie1, screen1, LocalDateTime.now().plusHours(2), show1Seats);
        screen1.addShow(show1);

        // Create ShowSeats for Show 2
        List<ShowSeat> show2Seats = screen2.getSeats().stream()
                .map(seat -> new ShowSeat(seat.getRow(), seat.getCol(), "show2", 300.0))
                .collect(Collectors.toList());

        Show show2 = new Show(movie2, screen2, LocalDateTime.now().plusHours(3), show2Seats);
        screen2.addShow(show2);

        pvr.addScreen(screen1);
        pvr.addScreen(screen2);

        return pvr;
    }
}
