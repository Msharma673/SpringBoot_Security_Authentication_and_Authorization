package service;

import dto.auth.JwtResponse;
import dto.auth.LoginRequest;
import dto.auth.SignupRequest;
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

import java.util.HashSet;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

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
    public void signup(SignupRequest request, String requesterUsername) {
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

        // Determine desired role
        String desiredRole = (request.getRole() == null)
                ? "USER"
                : request.getRole().toUpperCase();

        // Only ADMIN can create ADMIN
        if ("ADMIN".equals(desiredRole)) {
            if (requesterUsername == null) {
                throw new SecurityException("Only ADMIN can create ADMIN user");
            }

            User requester = userRepository.findByUsername(requesterUsername)
                    .orElseThrow(() -> new IllegalArgumentException("Requester not found"));

            boolean isAdmin = requester.getRoles()
                    .stream()
                    .anyMatch(role -> "ADMIN".equals(role.getName()));

            if (!isAdmin) {
                throw new SecurityException("Only ADMIN can create ADMIN user");
            }
        }

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
}
