package controller;

//Authentication controller implementing signup & login endpoints
import dto.auth.*;
import service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
 private final AuthService authService;
 private final Logger logger = LoggerFactory.getLogger(AuthController.class);

 @Autowired
 public AuthController(AuthService authService) { this.authService = authService; }

 @PostMapping("/signup")
 public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
     authService.signup(request);
     logger.info("Signup performed for username={} with role={}", request.getUsername(), request.getRole());
     return ResponseEntity.status(HttpStatus.CREATED).build();
 }

 @PostMapping("/login")
 public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
     var resp = authService.authenticate(request);
     logger.debug("Issued token for user");
     return ResponseEntity.ok(resp);
 }

 @PostMapping("/forgot-password")
 public ResponseEntity<ForgotPasswordResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
     ForgotPasswordResponse response = authService.forgotPassword(request);
     logger.info("Password reset requested for email={}", request.getEmail());
     return ResponseEntity.ok(response);
 }

 @PostMapping("/reset-password")
 public ResponseEntity<MessageResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
     authService.resetPassword(request);
     logger.info("Password reset completed successfully");
     return ResponseEntity.ok(new MessageResponse("Password has been reset successfully"));
 }

 @PostMapping("/logout")
 public ResponseEntity<MessageResponse> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
     authService.logout(authHeader);
     return ResponseEntity.ok(new MessageResponse("Logged out successfully"));
 }
}
