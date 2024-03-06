package com.bigdata.backend.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> handleValidationException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getFieldError().getDefaultMessage();
        ExceptionMessage exceptionMessage = new ExceptionMessage(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionMessage);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ExceptionMessage> handleConstraintViolationException(ConstraintViolationException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionMessage);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ExceptionMessage> handleRuntimeException(RuntimeException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionMessage);
    }

}
