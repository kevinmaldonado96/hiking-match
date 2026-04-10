package org.example.exception.exceptions;

public class ExceedingLimitException extends RuntimeException{
    public ExceedingLimitException(String message) {
        super(message);
    }
}
