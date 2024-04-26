package org.cyberspeed.exception;

//business logic exception
public class ScratchException extends Exception {
    public ScratchException(String message) {
        super(message);
    }

    public ScratchException(String message, Throwable rootCause) {
        super(message, rootCause);
    }
}
