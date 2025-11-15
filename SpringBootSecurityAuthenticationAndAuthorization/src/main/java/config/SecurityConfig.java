package config;

//Spring Security configuration: JWT filter + method security
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.List;

import security.CustomUserDetailsService;
import security.JwtAuthenticationFilter;
import security.JwtUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
 private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
 private final JwtUtils jwtUtils;
 private final CustomUserDetailsService userDetailsService;

 public SecurityConfig(JwtUtils jwtUtils, CustomUserDetailsService userDetailsService) {
     this.jwtUtils = jwtUtils;
     this.userDetailsService = userDetailsService;
 }

 @Bean
 public JwtAuthenticationFilter authenticationJwtTokenFilter() {
     return new JwtAuthenticationFilter(jwtUtils, userDetailsService);
 }

 @Bean
 public AuthenticationProvider authenticationProvider() {
     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
     authProvider.setUserDetailsService(userDetailsService);
     authProvider.setPasswordEncoder(passwordEncoder());
     return authProvider;
 }

 @Bean
 public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
     return config.getAuthenticationManager();
 }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .csrf(AbstractHttpConfigurer::disable) // Disabled for stateless JWT API
          .cors(cors -> cors.configurationSource(corsConfigurationSource()))
          .httpBasic(AbstractHttpConfigurer::disable)
          .formLogin(AbstractHttpConfigurer::disable)
          .logout(AbstractHttpConfigurer::disable)
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(auth -> {
            // Allow OPTIONS requests for CORS preflight
            auth.requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll();
            // Public endpoints - no authentication required
            auth.requestMatchers("/api/auth/**").permitAll();
            auth.requestMatchers("/actuator/**").permitAll();
            auth.requestMatchers("/error").permitAll();
            // All other requests require authentication
            auth.anyRequest().authenticated();
          })
          .exceptionHandling(exceptions -> exceptions
              .authenticationEntryPoint((request, response, authException) -> {
                  try {
                      response.setStatus(org.springframework.http.HttpStatus.UNAUTHORIZED.value());
                      response.setContentType("application/json");
                      response.setCharacterEncoding("UTF-8");
                      response.getWriter().write("{\"error\":\"Unauthorized: Invalid or missing authentication token\"}");
                      response.getWriter().flush();
                  } catch (java.io.IOException e) {
                      logger.error("Error writing authentication entry point response", e);
                  }
              })
              .accessDeniedHandler((request, response, accessDeniedException) -> {
                  try {
                      response.setStatus(org.springframework.http.HttpStatus.FORBIDDEN.value());
                      response.setContentType("application/json");
                      response.setCharacterEncoding("UTF-8");
                      response.getWriter().write("{\"error\":\"Forbidden: Insufficient permissions\"}");
                      response.getWriter().flush();
                  } catch (java.io.IOException e) {
                      logger.error("Error writing access denied response", e);
                  }
              })
          )
          .authenticationProvider(authenticationProvider())
          .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow common development origins (add more as needed)
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",
            "http://localhost:8080",
            "http://localhost:8095",
            "http://127.0.0.1:3000",
            "http://127.0.0.1:8080",
            "http://127.0.0.1:8095"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

 @Bean
 public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
 }
}
