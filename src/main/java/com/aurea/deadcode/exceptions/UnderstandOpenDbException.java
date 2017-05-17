package com.aurea.deadcode.exceptions;

/**
 * Created by Alex on 5/15/2017.
 */
public class UnderstandOpenDbException extends RuntimeException {

    public UnderstandOpenDbException() {
    }

    public UnderstandOpenDbException(String message) {
        super(message);
    }

    public UnderstandOpenDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnderstandOpenDbException(Throwable cause) {
        super(cause);
    }

    public UnderstandOpenDbException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
