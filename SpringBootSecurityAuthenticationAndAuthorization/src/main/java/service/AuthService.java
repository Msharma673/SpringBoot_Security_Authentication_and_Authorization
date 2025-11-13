package service;

//Auth service API: signup and login


//Auth service API: signup and login
package com.example.corporate.service;

import com.example.corporate.dto.auth.*;

public interface AuthService {
 JwtResponse authenticate(LoginRequest request);
 void signup(SignupRequest request, String requesterUsername);
}

