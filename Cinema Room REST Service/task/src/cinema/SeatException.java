package cinema;

public class SeatException extends Exception {
    public SeatException(String message) {
        super(message);
    }
}

class UnauthorizedException extends Exception {
    public UnauthorizedException(String message) {
        super(message);
    }
}