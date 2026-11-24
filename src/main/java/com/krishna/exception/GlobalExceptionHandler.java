
package com.krishna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleStudentNotFound(
            StudentNotFoundException ex) {

        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleStudentAlreadyExists(
            StudentAlreadyExistsException ex) {

        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> response = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                response.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDenied(
            AccessDeniedException ex) {

        Map<String, String> response = new HashMap<>();
        response.put(
                "error",
                "You do not have permission to access this resource."
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    // Handles Gemini API errors and other unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(
            Exception ex) {

        // Temporarily print the actual exception in IntelliJ console
        ex.printStackTrace();

        Map<String, String> response = new HashMap<>();
        String message = ex.getMessage();

        if (message != null && message.contains("503")) {
            response.put(
                    "error",
                    "AI service is temporarily busy. Please try again later."
            );

            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(response);
        }

        if (message != null && message.contains("429")) {
            response.put(
                    "error",
                    "AI request limit reached. Please try again after your quota resets."
            );

            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(response);
        }

        response.put("error", "Something went wrong.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateUsername(
            DuplicateUsernameException ex) {

        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
