package com.tripplannerai.exception.image;

public class NotImageFoundException extends RuntimeException {
    public NotImageFoundException() {
    }

    public NotImageFoundException(String message) {
        super(message);
    }

    public NotImageFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotImageFoundException(Throwable cause) {
        super(cause);
    }

    public NotImageFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
