package com.aurea.deadcode.exceptions;

/**
 * Created by Alex on 5/13/2017.
 */

public class ProcessException extends RuntimeException {

    public ProcessException(String s) {
        super(s);
    }
    public ProcessException(String s, Throwable throwable) {
        super(s, throwable);
    }
    public ProcessException(Throwable throwable) {
        super(throwable);
    }
}
