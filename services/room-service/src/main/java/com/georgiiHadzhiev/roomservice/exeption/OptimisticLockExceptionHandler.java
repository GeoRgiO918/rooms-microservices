package com.georgiiHadzhiev.roomservice.exeption;


import jakarta.persistence.OptimisticLockException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(1)
public class OptimisticLockExceptionHandler {


    @ExceptionHandler({OptimisticLockException.class, ObjectOptimisticLockingFailureException.class})
    @Order(1)
    public ResponseEntity<String> handleOptimisticLockException(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("The resource was updated by another user. Please refresh and try again.");
    }

}