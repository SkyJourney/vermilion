package top.icdat.vermilion.exception;

public class DataTypeException extends RuntimeException {

    public DataTypeException() {
    }

    public DataTypeException(String message) {
        super(message);
    }

    public DataTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
