package com.krishna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Student not found
    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleStudentNotFound(
            StudentNotFoundException exception) {

        Map<String, String> response = new HashMap<>();

        response.put("message", exception.getMessage());

        return response;
    }
    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleStudentAlreadyExists(
            StudentAlreadyExistsException ex) {

        Map<String, String> response = new HashMap<>();

        response.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }
    // Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return errors;
    }
}