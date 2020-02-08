package top.icdat.vermilion.exception;

public class SessionFactoryInitializationException extends RuntimeException {
    public SessionFactoryInitializationException() {
    }

    public SessionFactoryInitializationException(String message) {
        super(message);
    }

    public SessionFactoryInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
