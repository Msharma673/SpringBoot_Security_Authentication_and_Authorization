package service;

//Auth service API: signup, login, forgot-password, reset-password, logout
import dto.auth.*;

public interface AuthService {
 JwtResponse authenticate(LoginRequest request);
 void signup(SignupRequest request);
 ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request);
 void resetPassword(ResetPasswordRequest request);
 void logout(String token);
}

