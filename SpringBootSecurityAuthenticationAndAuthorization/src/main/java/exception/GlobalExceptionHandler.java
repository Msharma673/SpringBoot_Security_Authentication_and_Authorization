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

 @ExceptionHandler(Exception.class)
 public ResponseEntity<?> handleUnexpected(Exception ex) {
     logger.error("Unexpected error", ex);
     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
             .body(Map.of("error", "Internal server error"));
 }
}
