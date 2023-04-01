package com.github.proditos.exception;

/**
 * An exception thrown when a parsing error occurs.
 *
 * @author Vladislav Konovalov
 */
public class ParserException extends RuntimeException {
    public ParserException(String message) {
        super(message);
    }
}
