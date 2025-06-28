package booking.exception;

public class SeatsTemporarilyHeldException extends RuntimeException {
    public SeatsTemporarilyHeldException(String message) {
        super(message);
    }
}
