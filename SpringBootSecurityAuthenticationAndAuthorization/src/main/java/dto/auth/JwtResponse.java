package dto.auth;

//DTO: JWT response
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
 private String token;
 private String tokenType = "Bearer";
 private Long expiresInSeconds;
}
