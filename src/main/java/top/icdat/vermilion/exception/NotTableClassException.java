package top.icdat.vermilion.exception;

public class NotTableClassException extends RuntimeException {
    public NotTableClassException() {
    }

    public NotTableClassException(String message) {
        super(message);
    }

    public NotTableClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
