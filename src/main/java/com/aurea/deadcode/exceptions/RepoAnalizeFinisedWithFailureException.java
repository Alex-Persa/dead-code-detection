package com.aurea.deadcode.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Alex on 5/14/2017.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Couldn't provide dead code occurrences since the processing failed")
public class RepoAnalizeFinisedWithFailureException extends RuntimeException {
    public RepoAnalizeFinisedWithFailureException() {
    }

    public RepoAnalizeFinisedWithFailureException(String message) {
        super(message);
    }

    public RepoAnalizeFinisedWithFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepoAnalizeFinisedWithFailureException(Throwable cause) {
        super(cause);
    }

    public RepoAnalizeFinisedWithFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
