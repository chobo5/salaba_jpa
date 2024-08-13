package salaba.exception;

public class PasswordValidationException extends RuntimeException {
    public PasswordValidationException() {
        super();
    }

    public PasswordValidationException(String message) {
        super(message);
    }

    public PasswordValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordValidationException(Throwable cause) {
        super(cause);
    }

    protected PasswordValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
