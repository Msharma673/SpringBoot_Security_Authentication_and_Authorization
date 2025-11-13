package security;

import java.util.stream.Collectors;

//Loads user information from DB for Spring Security



import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
 private final UserRepository userRepository;
 public CustomUserDetailsService(UserRepository userRepository) { this.userRepository = userRepository; }

 @Override
 public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
     User user = userRepository.findByUsername(usernameOrEmail)
             .or(() -> userRepository.findByEmail(usernameOrEmail))
             .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail));
     var authorities = user.getRoles().stream()
             .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
             .collect(Collectors.toList());
     return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
 }
}

