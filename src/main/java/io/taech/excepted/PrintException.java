package io.taech.excepted;

public class PrintException extends RuntimeException {

    private String message;

    public PrintException(final String message) {
        super(message);
        this.message = message;
    }

    public PrintException() {
        super();
        this.message = "";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
