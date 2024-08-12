package salaba.exception;

public class CannotChangeStatusException extends RuntimeException {
    public CannotChangeStatusException() {
    }

    public CannotChangeStatusException(String message) {
        super(message);
    }

    public CannotChangeStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotChangeStatusException(Throwable cause) {
        super(cause);
    }
}
