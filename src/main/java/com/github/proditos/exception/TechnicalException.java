package com.github.proditos.exception;

/**
 * Wraps checked exceptions such as {@code IOException} and
 * {@code SQLException} for their further processing.
 *
 * @author Vladislav Konovalov
 */
public class TechnicalException extends RuntimeException {
    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
