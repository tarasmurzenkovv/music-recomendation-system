package exceptions.classes;

public class TrackException extends RuntimeException {
    public TrackException(String message) {
        super(message);
    }

    public TrackException(String message, Throwable cause) {
        super(message, cause);
    }
}
