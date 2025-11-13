package config;

//Spring Security configuration: JWT filter + method security
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import security.CustomUserDetailsService;
import security.JwtAuthenticationFilter;
import security.JwtUtils;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
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
 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     http
       .csrf().disable()
       .authorizeHttpRequests()
         .requestMatchers("/api/auth/**", "/actuator/**").permitAll()
         .anyRequest().authenticated()
       .and()
         .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
     return http.build();
 }

 @Bean
 public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
 }
}
