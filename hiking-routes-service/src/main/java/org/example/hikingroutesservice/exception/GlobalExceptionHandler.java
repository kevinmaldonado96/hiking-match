package org.example.hikingroutesservice.exception;

import org.example.hikingroutesservice.exception.exceptions.DuplicateRouteException;
import org.example.hikingroutesservice.exception.exceptions.InjuryException;
import org.example.hikingroutesservice.exception.exceptions.RouteNotFoundException;
import org.example.hikingroutesservice.exception.exceptions.UserAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<?>> handleBadRequestWebException(WebExchangeBindException e) {
        List<Map<String, String>> errors = e.getFieldErrors().stream()
                .map(fieldError -> Map.of(
                        "field", fieldError.getField(),
                        "message", fieldError.getDefaultMessage()
                )).toList();

        return Mono.just(
                ResponseEntity.badRequest().body(errors)
        );
    }

    @ExceptionHandler(DuplicateRouteException.class)
    public Mono<ResponseEntity<?>> handleDuplicateRouteException(DuplicateRouteException e) {
        return Mono.just(new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(RouteNotFoundException.class)
    public Mono<ResponseEntity<?>> handleRouteNotFoundException(RouteNotFoundException e) {
        return Mono.just(new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(InjuryException.class)
    public Mono<ResponseEntity<?>> handleUserInjuryException(InjuryException e) {
        return Mono.just(new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public Mono<ResponseEntity<?>> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException e) {
        return Mono.just(new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT));
    }
}
