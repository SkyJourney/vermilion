package top.icdat.vermilion.exception;

public class NotOperatorClassException extends RuntimeException {
    public NotOperatorClassException() {
    }

    public NotOperatorClassException(String message) {
        super(message);
    }

    public NotOperatorClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
