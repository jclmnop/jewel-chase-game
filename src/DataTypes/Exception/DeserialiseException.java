package DataTypes.Exception;

public class DeserialiseException extends RuntimeException {
    private final Exception originalException;

    public DeserialiseException(String msg) {
        super(msg);
        this.originalException = null;
    }

    public DeserialiseException(String msg, Exception originalException) {
        super(msg);
        this.originalException = originalException;
    }

    public Exception getOriginalException() {
        return originalException;
    }
}
