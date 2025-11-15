package exception;

//Centralized exception handling for controllers


import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
 private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

 @ExceptionHandler(ResourceNotFoundException.class)
 public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
     logger.warn("Resource not found: {}", ex.getMessage());
     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
 }

 @ExceptionHandler(IllegalArgumentException.class)
 public ResponseEntity<?> handleBadRequest(IllegalArgumentException ex) {
     logger.warn("Bad request: {}", ex.getMessage());
     return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
 }

 @ExceptionHandler(MethodArgumentNotValidException.class)
 public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
     var errors = new HashMap<String,String>();
     ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
     logger.warn("Validation failed: {}", errors);
     return ResponseEntity.badRequest().body(Map.of("validationErrors", errors));
 }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<?> handleSecurityException(SecurityException ex) {
        logger.warn("Security violation: {}", ex.getMessage());
        // Return 400 Bad Request for security violations during signup (like trying to create ADMIN without permission)
        // This is more appropriate than 403 Forbidden for validation/authorization errors in public endpoints
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(org.springframework.security.core.AuthenticationException ex) {
        logger.warn("Authentication failed: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid credentials"));
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex) {
        logger.warn("Access denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("error", "Access denied"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpected(Exception ex) {
        logger.error("Unexpected error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal server error"));
    }
}
