package security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

//Filter that extracts JWT from Authorization: Bearer <token> and authenticates user


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
 private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
 private final JwtUtils jwtUtils;
 private final CustomUserDetailsService userDetailsService;

 public JwtAuthenticationFilter(JwtUtils jwtUtils, CustomUserDetailsService userDetailsService) {
     this.jwtUtils = jwtUtils;
     this.userDetailsService = userDetailsService;
 }

 @Override
 protected boolean shouldNotFilter(HttpServletRequest request) {
     String path = request.getRequestURI();
     // Only skip actuator endpoints, but process /api/auth/ endpoints to allow optional authentication
     // This allows admin users to create other admin users via signup with token
     boolean shouldSkip = path.startsWith("/actuator/");
     if (shouldSkip) {
         logger.debug("Skipping JWT filter for actuator endpoint: {}", path);
     }
     return shouldSkip;
 }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");
            String token = null;
            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7);
            }
            
            // Process token if provided (even for public endpoints like /api/auth/signup)
            // This allows authenticated users to create admin accounts
            if (token != null && jwtUtils.validateJwtToken(token)) {
                String username = jwtUtils.getUsernameFromJwt(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // Verify user is still enabled
                if (!userDetails.isEnabled()) {
                    logger.warn("Attempted access with disabled account: {}", username);
                    SecurityContextHolder.clearContext();
                    // For public endpoints, don't block the request, just clear context
                    if (!request.getRequestURI().startsWith("/api/auth/")) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                } else {
                    var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    logger.debug("Authenticated user: {} for request: {}", username, request.getRequestURI());
                }
            } else if (token != null) {
                logger.warn("Invalid JWT token provided for request: {}", request.getRequestURI());
                // For public endpoints, allow request to proceed even with invalid token
                // (the endpoint itself will handle authorization)
                if (!request.getRequestURI().startsWith("/api/auth/")) {
                    SecurityContextHolder.clearContext();
                }
            }
        } catch (org.springframework.security.core.userdetails.UsernameNotFoundException ex) {
            logger.warn("User not found during JWT validation: {}", ex.getMessage());
            // For public endpoints, allow request to proceed
            if (!request.getRequestURI().startsWith("/api/auth/")) {
                SecurityContextHolder.clearContext();
            }
        } catch (Exception ex) {
            logger.error("Cannot set user authentication: {}", ex.getMessage(), ex);
            // For public endpoints, allow request to proceed even if authentication fails
            if (!request.getRequestURI().startsWith("/api/auth/")) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}

