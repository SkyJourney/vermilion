package top.icdat.vermilion.exception;

public class NoSuchMethodException extends RuntimeException {

    public NoSuchMethodException() {
        super();
    }

    public NoSuchMethodException(String message) {
        super(message);
    }

    public NoSuchMethodException(String message, Throwable cause) {
        super(message, cause);
    }
}
