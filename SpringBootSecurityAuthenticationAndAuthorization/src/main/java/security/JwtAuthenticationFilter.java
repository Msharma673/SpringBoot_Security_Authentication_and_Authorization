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
     // Skip JWT filter entirely for public endpoints
     return path.startsWith("/api/auth/") || path.startsWith("/actuator/");
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
            
            if (token != null && jwtUtils.validateJwtToken(token)) {
                String username = jwtUtils.getUsernameFromJwt(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // Verify user is still enabled
                if (!userDetails.isEnabled()) {
                    logger.warn("Attempted access with disabled account: {}", username);
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                
                var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.debug("Authenticated user: {} for request: {}", username, request.getRequestURI());
            } else if (token != null) {
                logger.warn("Invalid JWT token provided for request: {}", request.getRequestURI());
                SecurityContextHolder.clearContext();
            }
        } catch (org.springframework.security.core.userdetails.UsernameNotFoundException ex) {
            logger.warn("User not found during JWT validation: {}", ex.getMessage());
            SecurityContextHolder.clearContext();
        } catch (Exception ex) {
            logger.error("Cannot set user authentication: {}", ex.getMessage(), ex);
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}

