package com.github.proditos.exception;

/**
 * Wraps {@code SQLException} for
 * their further processing.
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
