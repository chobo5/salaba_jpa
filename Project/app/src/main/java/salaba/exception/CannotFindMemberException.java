package salaba.exception;

public class CannotFindMemberException extends RuntimeException {
    public CannotFindMemberException() {
        super();
    }

    public CannotFindMemberException(String message) {
        super(message);
    }

    public CannotFindMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotFindMemberException(Throwable cause) {
        super(cause);
    }

    protected CannotFindMemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
