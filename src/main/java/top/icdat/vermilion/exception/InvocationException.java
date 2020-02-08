package top.icdat.vermilion.exception;

public class InvocationException extends RuntimeException {
    public InvocationException(String message) {
        super(message);
    }

    public InvocationException() {
    }

    public InvocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
