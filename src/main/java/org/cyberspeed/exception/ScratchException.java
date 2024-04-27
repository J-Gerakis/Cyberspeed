package org.cyberspeed.exception;

//business logic exception
public class ScratchException extends RuntimeException {
    public ScratchException(String message) {
        super(message);
    }

    public ScratchException(String message, Throwable rootCause) {
        super(message, rootCause);
    }
}
