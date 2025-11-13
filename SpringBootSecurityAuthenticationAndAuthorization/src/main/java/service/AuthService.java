package service;

//Auth service API: signup and login
import dto.auth.*;

public interface AuthService {
 JwtResponse authenticate(LoginRequest request);
 void signup(SignupRequest request, String requesterUsername);
}

