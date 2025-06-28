package booking.concurrency;

import catalog.model.ShowSeat;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface SeatLockProvider {
    boolean tryLockSeats(List<ShowSeat> seats, long timeout, TimeUnit unit) throws InterruptedException;
    void unlockSeats(List<ShowSeat> seats);
}