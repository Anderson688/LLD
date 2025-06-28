package booking.concurrency;

import catalog.model.Seat;
import catalog.model.ShowSeat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class InMemorySeatLockProvider implements SeatLockProvider {
    private final ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    @Override
    public boolean tryLockSeats(List<ShowSeat> seats, long timeout, TimeUnit unit) throws InterruptedException {
        // Sort to prevent deadlocks
        seats.sort(Comparator.comparing(Seat::getId));

        List<ReentrantLock> acquiredLocks = new ArrayList<>();

        for (ShowSeat seat : seats) {
            ReentrantLock lock = locks.computeIfAbsent(seat.getId(), _ -> new ReentrantLock());
            if (lock.tryLock(timeout, unit)) {
                acquiredLocks.add(lock);
            } else {
                // Could not acquire a lock for this seat.
                // CRITICAL: Release all previously acquired locks to prevent holding them indefinitely.
                for (ReentrantLock acquiredLock : acquiredLocks) {
                    acquiredLock.unlock();
                }
                return false; // Report failure
            }
        }
        return true; // All locks were successfully acquired
    }

    @Override
    public void unlockSeats(List<ShowSeat> seats) {
        // Sort in the same order to be consistent
        seats.sort(Comparator.comparing(Seat::getId));
        for (ShowSeat seat : seats) {
            ReentrantLock lock = locks.get(seat.getId());
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}