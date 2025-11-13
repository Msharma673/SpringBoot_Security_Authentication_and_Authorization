package security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



//JWT utility using jjwt library: generate and validate tokens


@Component
public class JwtUtils {
 private final SecretKey key;
 private final long expirationSeconds;

 public JwtUtils(@Value("${app.jwt.secret}") String secret,
                 @Value("${app.jwt.expirationSeconds}") long expirationSeconds) {
     // Use HMAC-SHA for symmetric key
     this.key = Keys.hmacShaKeyFor(secret.getBytes());
     this.expirationSeconds = expirationSeconds;
 }

 public String generateToken(String username) {
     Date now = new Date();
     Date exp = new Date(now.getTime() + expirationSeconds * 1000);
     return Jwts.builder()
         .setSubject(username)
         .setIssuedAt(now)
         .setExpiration(exp)
         .signWith(key)
         .compact();
 }

 public String getUsernameFromJwt(String token) {
     return Jwts.parser().verifyWith(key).build()
             .parseClaimsJws(token).getBody().getSubject();
 }

 public boolean validateJwtToken(String authToken) {
     try {
         Jwts.parser().verifyWith(key).build().parse(authToken);
         return true;
     } catch (JwtException e) {
         return false;
     }
 }

 public long getExpirationSeconds() {
     return expirationSeconds;
 }
}
