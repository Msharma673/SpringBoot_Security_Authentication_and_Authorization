package controller;

//Authentication controller implementing signup & login endpoints
import dto.auth.*;
import service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
     String requester = null;
     var auth = SecurityContextHolder.getContext().getAuthentication();
     if (auth != null && auth.isAuthenticated()) {
         requester = auth.getName();
     }
     authService.signup(request, requester);
     logger.info("Signup performed for username={}", request.getUsername());
     return ResponseEntity.status(HttpStatus.CREATED).build();
 }

 @PostMapping("/login")
 public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
     var resp = authService.authenticate(request);
     logger.debug("Issued token for user");
     return ResponseEntity.ok(resp);
 }

 // TODO: forgot-password/reset-password/logout endpoints can be added similarly
}
