package security;

import java.util.stream.Collectors;

//Loads user information from DB for Spring Security
import model.User;
import repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
 private final UserRepository userRepository;
 public CustomUserDetailsService(UserRepository userRepository) { this.userRepository = userRepository; }

 @Override
 public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
     User userEntity = userRepository.findByUsername(usernameOrEmail)
             .or(() -> userRepository.findByEmail(usernameOrEmail))
             .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail));
     var authorities = userEntity.getRoles().stream()
             .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
             .collect(Collectors.toList());
     return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(), authorities);
 }
}

