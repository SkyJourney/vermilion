package top.icdat.vermilion.exception;

public class UnsupportedDatabaseException extends RuntimeException {
    public UnsupportedDatabaseException() {
    }

    public UnsupportedDatabaseException(String message) {
        super(message);
    }

    public UnsupportedDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
