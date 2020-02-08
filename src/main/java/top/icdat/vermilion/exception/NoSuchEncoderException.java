package top.icdat.vermilion.exception;

public class NoSuchEncoderException extends RuntimeException {
    public NoSuchEncoderException(String message) {
        super(message);
    }

    public NoSuchEncoderException() {
    }

    public NoSuchEncoderException(String message, Throwable cause) {
        super(message, cause);
    }
}
