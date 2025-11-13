package dto.auth;

//DTO: signup request


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupRequest {
 @NotBlank
 private String username;

 @NotBlank @Email
 private String email;

 @NotBlank
 private String password;

 // optional role hint: ADMIN can be created only by ADMIN token user
 private String role;
}
