package dto.auth;

//DTO: signup request


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupRequest {
 @NotBlank(message = "Username is required")
 private String username;

 @NotBlank(message = "Email is required") 
 @Email(message = "Email must be a valid email address")
 private String email;

 @NotBlank(message = "Password is required")
 private String password;

 // optional role hint: ADMIN can be created only by ADMIN token user
 private String role;
}
