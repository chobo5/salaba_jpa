package salaba.domain.member.exception;

public class PointCannotBeZeroException extends RuntimeException {
    public PointCannotBeZeroException() {
        super();
    }

    public PointCannotBeZeroException(String message) {
        super(message);
    }

    public PointCannotBeZeroException(String message, Throwable cause) {
        super(message, cause);
    }

    public PointCannotBeZeroException(Throwable cause) {
        super(cause);
    }

    protected PointCannotBeZeroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
