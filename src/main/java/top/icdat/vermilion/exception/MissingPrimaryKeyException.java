package top.icdat.vermilion.exception;

public class MissingPrimaryKeyException extends RuntimeException {

    public MissingPrimaryKeyException() {
    }

    public MissingPrimaryKeyException(String message) {
        super(message);
    }

    public MissingPrimaryKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
