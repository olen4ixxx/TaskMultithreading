package io.olen4ixxx.ship.exception;

public class ShipException extends Exception {
    public ShipException(String message) {
        super(message);
    }

    public ShipException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShipException() {
    }

    public ShipException(Throwable cause) {
        super(cause);
    }
}
