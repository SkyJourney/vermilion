package top.icdat.vermilion.exception;

public class SQLGeneratingException extends RuntimeException {

    public SQLGeneratingException() {
    }

    public SQLGeneratingException(String message) {
        super(message);
    }

    public SQLGeneratingException(String message, Throwable cause) {
        super(message, cause);
    }
}
