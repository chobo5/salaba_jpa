package salaba.exception;

public class CannotBeZeroException extends RuntimeException {
    public CannotBeZeroException() {
        super();
    }

    public CannotBeZeroException(String message) {
        super(message);
    }

    public CannotBeZeroException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotBeZeroException(Throwable cause) {
        super(cause);
    }

    protected CannotBeZeroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
