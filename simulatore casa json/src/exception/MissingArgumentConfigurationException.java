package exception;

public class MissingArgumentConfigurationException extends RuntimeException {
    public MissingArgumentConfigurationException(String message) {
        super(message);
    }
}
