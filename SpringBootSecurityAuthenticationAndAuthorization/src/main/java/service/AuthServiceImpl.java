package service;

import dto.auth.*;
import model.Role;
import model.User;
import repository.RoleRepository;
import repository.UserRepository;
import security.JwtUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    
    // In-memory store for reset tokens (in production, use database or Redis)
    // Key: resetToken, Value: {email, expiryTime}
    private static final Map<String, ResetTokenInfo> resetTokens = new HashMap<>();
    
    private static class ResetTokenInfo {
        String email;
        LocalDateTime expiryTime;
        
        ResetTokenInfo(String email, LocalDateTime expiryTime) {
            this.email = email;
            this.expiryTime = expiryTime;
        }
    }

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public JwtResponse authenticate(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail(),
                            request.getPassword()
                    )
            );

            String username = authentication.getName();
            
            // Extract roles from authentication
            List<String> roles = authentication.getAuthorities().stream()
                    .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                    .toList();
            
            String token = jwtUtils.generateToken(username, roles);

            logger.info("User {} authenticated successfully with roles: {}", username, roles);

            return new JwtResponse(token, "Bearer", jwtUtils.getExpirationSeconds());
        } catch (org.springframework.security.core.AuthenticationException e) {
            logger.warn("Authentication failed for user: {}", request.getUsernameOrEmail());
            throw e;
        }
    }

    @Override
    public void signup(SignupRequest request) {
        // Validate password strength
        validatePassword(request.getPassword());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true); // New users are enabled by default

        // prevent NullPointerException
        user.setRoles(new HashSet<>());

        // Determine desired role - anyone can create any role (ADMIN, USER, etc.)
        String desiredRole = (request.getRole() == null)
                ? "USER"
                : request.getRole().toUpperCase();

        // Load role from DB
        Role role = roleRepository.findByName(desiredRole)
                .orElseThrow(() -> new IllegalStateException("Role not found: " + desiredRole));

        user.getRoles().add(role);

        userRepository.save(user);

        logger.info("New user created: {} with role {}", user.getUsername(), desiredRole);
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if (password.length() > 128) {
            throw new IllegalArgumentException("Password must be less than 128 characters");
        }
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(ch -> "!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(ch) >= 0);
        
        if (!hasUpperCase || !hasLowerCase || !hasDigit || !hasSpecial) {
            throw new IllegalArgumentException(
                "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
            );
        }
    }
    
    @Override
    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User with this email does not exist"));
        
        // Generate reset token
        String resetToken = UUID.randomUUID().toString();
        
        // Store token with expiry (15 minutes from now)
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(15);
        resetTokens.put(resetToken, new ResetTokenInfo(request.getEmail(), expiryTime));
        
        logger.info("Password reset token generated for user: {}", request.getEmail());
        
        // In production, send token via email instead of returning it
        // For now, we return it for testing purposes
        return new ForgotPasswordResponse(
            "Password reset token has been generated. Please check your email.",
            resetToken
        );
    }
    
    @Override
    public void resetPassword(ResetPasswordRequest request) {
        // Validate password strength
        validatePassword(request.getNewPassword());
        
        // Check if token exists and is valid
        ResetTokenInfo tokenInfo = resetTokens.get(request.getResetToken());
        if (tokenInfo == null) {
            throw new IllegalArgumentException("Invalid or expired reset token");
        }
        
        // Check if token has expired
        if (LocalDateTime.now().isAfter(tokenInfo.expiryTime)) {
            resetTokens.remove(request.getResetToken());
            throw new IllegalArgumentException("Reset token has expired. Please request a new one.");
        }
        
        // Find user by email from token
        User user = userRepository.findByEmail(tokenInfo.email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        
        // Remove used token
        resetTokens.remove(request.getResetToken());
        
        logger.info("Password reset successfully for user: {}", tokenInfo.email);
    }
    
    @Override
    public void logout(String token) {
        // Since JWT is stateless, logout is mainly client-side
        // In a stateless system, the client should discard the token
        // For a more robust solution, you could maintain a blacklist of tokens
        // For now, we just log the logout and return success
        
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        try {
            String username = jwtUtils.getUsernameFromJwt(token);
            logger.info("User {} logged out successfully", username);
        } catch (Exception e) {
            logger.warn("Error extracting username from token during logout: {}", e.getMessage());
        }
        
        // In production, you might want to:
        // 1. Add token to a blacklist (Redis/database)
        // 2. Set token expiry to current time
        // 3. Use refresh tokens and revoke them
    }
}
