package security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.UnsupportedJwtException;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//JWT utility using jjwt library: generate and validate tokens with enhanced security

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private final SecretKey key;
    private final long expirationSeconds;

    public JwtUtils(@Value("${app.jwt.secret}") String secret,
                    @Value("${app.jwt.expirationSeconds}") long expirationSeconds) {
        // Validate secret key length (minimum 256 bits / 32 bytes for HS256)
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long for security");
        }
        // Use HMAC-SHA for symmetric key
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationSeconds = expirationSeconds;
        logger.info("JWT Utils initialized with expiration: {} seconds", expirationSeconds);
    }

    public String generateToken(String username, List<String> roles) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationSeconds * 1000);
        
        Claims claims = Jwts.claims()
                .subject(username)
                .add("roles", roles)
                .issuedAt(now)
                .expiration(exp)
                .build();
        
        return Jwts.builder()
                .claims(claims)
                .signWith(key)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    public List<String> getRolesFromJwt(String token) {
        Claims claims = getClaimsFromToken(token);
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) claims.get("roles");
        return roles != null ? roles : List.of();
    }

    public Date getExpirationDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Claims claims = getClaimsFromToken(authToken);
            
            // Check if token is expired
            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                logger.warn("JWT token is expired");
                return false;
            }
            
            // Validate subject exists
            String subject = claims.getSubject();
            if (subject == null || subject.isEmpty()) {
                logger.warn("JWT token has invalid subject");
                return false;
            }
            
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("JWT token is expired: {}", e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            logger.warn("Invalid JWT token format: {}", e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            logger.warn("Unsupported JWT token: {}", e.getMessage());
            return false;
        } catch (SecurityException e) {
            logger.warn("JWT signature validation failed: {}", e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            logger.warn("JWT token is empty or null: {}", e.getMessage());
            return false;
        } catch (JwtException e) {
            logger.warn("JWT validation failed: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error validating JWT token: {}", e.getMessage(), e);
            return false;
        }
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }
}
