package dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForgotPasswordResponse {
    private String message;
    private String resetToken; // In production, this would be sent via email, not returned in response
}

