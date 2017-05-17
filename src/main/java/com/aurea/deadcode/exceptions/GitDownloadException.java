package com.aurea.deadcode.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Alex on 5/14/2017.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GitDownloadException extends RuntimeException {
    public GitDownloadException() {
    }

    public GitDownloadException(String message) {
        super(message);
    }

    public GitDownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public GitDownloadException(Throwable cause) {
        super(cause);
    }

    public GitDownloadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
