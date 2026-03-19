package com.fear.oscar_voting_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFound(ResourceNotFoundException e){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorMessage error = new ErrorMessage(status.value(), e.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessage> handleBusiness(BusinessException e){
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorMessage error = new ErrorMessage(status.value(), e.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(error.status()).body(error);
    }

}
