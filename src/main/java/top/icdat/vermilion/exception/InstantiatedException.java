package top.icdat.vermilion.exception;

public class InstantiatedException extends RuntimeException {

    public InstantiatedException() {
        super();
    }

    public InstantiatedException(String message) {
        super(message);
    }

    public InstantiatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
