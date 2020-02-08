package top.icdat.vermilion.exception;

public class NoSuchDecoderException extends RuntimeException {
    public NoSuchDecoderException(String message) {
        super(message);
    }

    public NoSuchDecoderException() {
    }

    public NoSuchDecoderException(String message, Throwable cause) {
        super(message, cause);
    }
}
