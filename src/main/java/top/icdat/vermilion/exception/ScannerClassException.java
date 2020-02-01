package top.icdat.vermilion.exception;

public class ScannerClassException extends RuntimeException {

    public ScannerClassException() {
        super();
    }

    public ScannerClassException(String message) {
        super(message);
    }

    public ScannerClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
