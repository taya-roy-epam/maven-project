package com.example.SongService.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errorMessage", "Validation error");


        Map<String, String> details = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                details.put(error.getField(), error.getDefaultMessage())
        );

        errorResponse.put("details", details);
        errorResponse.put("errorCode", "400");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errorMessage", "Validation error");
        errorResponse.put("details", e.getMessage());
        errorResponse.put("errorCode", "400");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(SongMetadataNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSongMetadataNotFoundException(SongMetadataNotFoundException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errorMessage", e.getMessage());
        errorResponse.put("errorCode", "404");

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(SongMetadataConflictException.class)
    public ResponseEntity<Map<String, Object>> handleSongMetadataConflictExceptio(SongMetadataConflictException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errorMessage", e.getMessage());
        errorResponse.put("errorCode", "409");

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errorMessage", "An error occurred on the server.");
        errorResponse.put("errorCode", "500");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
}
