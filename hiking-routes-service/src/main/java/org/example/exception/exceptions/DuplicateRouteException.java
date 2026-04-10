package org.example.exception.exceptions;

public class DuplicateRouteException extends RuntimeException{
    public DuplicateRouteException(String message) {
        super(message);
    }
}
