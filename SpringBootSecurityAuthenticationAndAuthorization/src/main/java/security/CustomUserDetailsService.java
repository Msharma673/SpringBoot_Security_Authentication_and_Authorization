package security;

import java.util.stream.Collectors;

//Loads user information from DB for Spring Security with enhanced security checks
import model.User;
import repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UserRepository userRepository;
    
    public CustomUserDetailsService(UserRepository userRepository) { 
        this.userRepository = userRepository; 
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> {
                    logger.warn("User not found: {}", usernameOrEmail);
                    return new UsernameNotFoundException("Invalid credentials");
                });
        
        // Check if user account is enabled
        if (!userEntity.isEnabled()) {
            logger.warn("Attempted login with disabled account: {}", usernameOrEmail);
            throw new UsernameNotFoundException("Account is disabled");
        }
        
        var authorities = userEntity.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                .collect(Collectors.toList());
        
        if (authorities.isEmpty()) {
            logger.warn("User {} has no roles assigned", usernameOrEmail);
        }
        
        return org.springframework.security.core.userdetails.User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!userEntity.isEnabled())
                .build();
    }
}

