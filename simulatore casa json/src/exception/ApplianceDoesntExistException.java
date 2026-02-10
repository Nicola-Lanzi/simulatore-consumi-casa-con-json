package exception;

public class ApplianceDoesntExistException extends RuntimeException {
    public ApplianceDoesntExistException(String message) {
        super(message);
    }
}
