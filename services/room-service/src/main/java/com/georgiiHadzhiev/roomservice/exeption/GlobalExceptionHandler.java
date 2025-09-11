package com.georgiiHadzhiev.roomservice.exeption;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(2)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @Order(2)
    public ResponseEntity<String> handleAllOtherExceptions(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected error: " + ex.getMessage() + " "+ ex.getClass().getName());
    }
}
