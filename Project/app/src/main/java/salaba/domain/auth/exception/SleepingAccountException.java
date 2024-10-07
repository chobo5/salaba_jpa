package salaba.domain.auth.exception;

public class SleepingAccountException extends RuntimeException {
    public SleepingAccountException() {
        super();
    }

    public SleepingAccountException(String message) {
        super(message);
    }

    public SleepingAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public SleepingAccountException(Throwable cause) {
        super(cause);
    }

    protected SleepingAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
