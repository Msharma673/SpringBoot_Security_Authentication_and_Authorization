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
             var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
             SecurityContextHolder.getContext().setAuthentication(auth);
         }
     } catch (Exception ex) {
         logger.error("Cannot set user authentication: {}", ex.getMessage(), ex);
     }
     filterChain.doFilter(request, response);
 }
}

