package top.icdat.vermilion.exception;

public class NoSuchFieldException extends RuntimeException {

    public NoSuchFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchFieldException() {
        super();
    }

    public NoSuchFieldException(String message) {
        super(message);
    }

}
