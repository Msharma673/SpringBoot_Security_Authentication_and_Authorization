package service;


//Implementation of AuthService: user creation, login generation (JWT)
//Implementation of AuthService: user creation, login generation (JWT)
package com.example.corporate.service;

import com.example.corporate.dto.auth.*;
import com.example.corporate.model.*;
import com.example.corporate.repository.*;
import com.example.corporate.security.JwtUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.*;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
 private final AuthenticationManager authenticationManager;
 private final UserRepository userRepository;
 private final RoleRepository roleRepository;
 private final PasswordEncoder passwordEncoder;
 private final JwtUtils jwtUtils;
 private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

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
     // authenticate using AuthenticationManager (usernameOrEmail resolution handled in CustomUserDetailsService)
     Authentication authentication = authenticationManager.authenticate(
             new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
     );
     // If no exception, successful
     String token = jwtUtils.generateToken(authentication.getName());
     logger.info("User {} authenticated successfully", authentication.getName());
     return new JwtResponse(token, "Bearer", jwtUtils == null ? 3600L : jwtUtilsExpiration());
 }

 // helper to safely get expiration
 private Long jwtUtilsExpiration() {
     try {
         java.lang.reflect.Field f = JwtUtils.class.getDeclaredField("expirationSeconds");
         f.setAccessible(true);
         return (Long) f.get(jwtUtils);
     } catch (Exception e) {
         return 3600L;
     }
 }

 @Override
 public void signup(SignupRequest request, String requesterUsername) {
     if (userRepository.existsByUsername(request.getUsername()))
         throw new IllegalArgumentException("Username already taken");
     if (userRepository.existsByEmail(request.getEmail()))
         throw new IllegalArgumentException("Email already in use");

     User user = new User();
     user.setUsername(request.getUsername());
     user.setEmail(request.getEmail());
     user.setPassword(passwordEncoder.encode(request.getPassword()));

     // Determine role: by default USER, but if request.role == ADMIN require requester is ADMIN
     String desired = (request.getRole() == null) ? "USER" : request.getRole().toUpperCase();
     if ("ADMIN".equals(desired)) {
         // only allow if requesterUsername has ADMIN role
         if (requesterUsername == null) {
             throw new SecurityException("Cannot create ADMIN without admin privileges");
         }
         User requester = userRepository.findByUsername(requesterUsername).orElseThrow();
         boolean isAdmin = requester.getRoles().stream().anyMatch(r -> "ADMIN".equals(r.getName()));
         if (!isAdmin) throw new SecurityException("Only ADMIN can create ADMIN user");
     }
     Role role = roleRepository.findByName(desired).orElseThrow(() -> new IllegalStateException("Role not configured: " + desired));
     user.getRoles().add(role);
     userRepository.save(user);
     logger.info("New user created: {} with role {}", user.getUsername(), desired);
 }
}
