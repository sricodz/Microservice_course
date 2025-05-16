package com.product.ecom.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleProductNotFoundException(ProductNotFoundException ex){

        Map<String,String> errors = new HashMap<>();
        errors.put("message",ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex){

        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(e->errors.put(e.getField(),e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> handleConstraintValidationException(ConstraintViolationException ex){

        Map<String,String> errors = new HashMap<>();
        ex.getConstraintViolations()
                .forEach(e->{
                    String path = e.getPropertyPath().toString();
                    String msg = e.getMessage();
                    errors.put(path,msg);
                });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
