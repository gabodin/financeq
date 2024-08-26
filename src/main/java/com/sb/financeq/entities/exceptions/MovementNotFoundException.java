package com.sb.financeq.entities.exceptions;

public class MovementNotFoundException extends RuntimeException {

    public MovementNotFoundException(String message) {
        super(message);
    }

    public MovementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovementNotFoundException(Throwable cause) {
        super(cause);
    }
}
