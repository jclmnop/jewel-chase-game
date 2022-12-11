package DataTypes.Exception;

/**
 * Thrown when something goes wrong with deserialisation.
 * @author Jonny
 * @version 1.0
 * @see Utils.Deserialiser
 */
public class DeserialiseException extends RuntimeException {
    private final Exception originalException;

    /**
     * @param msg Message describing the exception.
     */
    public DeserialiseException(String msg) {
        super(msg);
        this.originalException = null;
    }

    /**
     * @param msg Message describing the exception.
     * @param originalException The original exception detected when this
     *                          exception was thrown.
     */
    public DeserialiseException(String msg, Exception originalException) {
        super(msg);
        this.originalException = originalException;
    }

    /**
     * @return The original exception detected when this exception was thrown.
     */
    public Exception getOriginalException() {
        return originalException;
    }
}
