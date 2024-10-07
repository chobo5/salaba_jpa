package salaba.domain.auth.exception;

public class ResignedAccountException extends RuntimeException {
    public ResignedAccountException() {
        super();
    }

    public ResignedAccountException(String message) {
        super(message);
    }

    public ResignedAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResignedAccountException(Throwable cause) {
        super(cause);
    }

    protected ResignedAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
