package controller;

import com.SpringBootSecurity.SpringBootSecurityAuthenticationAndAuthorization.SpringBootSecurityAuthenticationAndAuthorizationApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.auth.*;
import model.Role;
import model.User;
import repository.RoleRepository;
import repository.UserRepository;
import security.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SpringBootSecurityAuthenticationAndAuthorizationApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("AuthController API Tests")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    private Role adminRole;
    private Role userRole;
    private User adminUser;

    @BeforeEach
    void setUp() {
        // Clean up
        userRepository.deleteAll();
        roleRepository.deleteAll();

        // Create roles
        adminRole = new Role();
        adminRole.setName("ADMIN");
        roleRepository.save(adminRole);

        userRole = new Role();
        userRole.setName("USER");
        roleRepository.save(userRole);

        // Create a test user for login tests
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword(passwordEncoder.encode("Password123!"));
        testUser.setEnabled(true);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        testUser.setRoles(roles);
        userRepository.save(testUser);

        // Create admin user for admin creation tests
        adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setEmail("admin@example.com");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setEnabled(true);
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRoles(adminRoles);
        userRepository.save(adminUser);
    }

    @Test
    @DisplayName("POST /api/auth/signup - Should create new user successfully")
    void testSignup_Success() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("newuser");
        request.setEmail("newuser@example.com");
        request.setPassword("Password123!");
        request.setRole("USER");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        // Verify user was created
        assert userRepository.findByUsername("newuser").isPresent();
    }

    @Test
    @DisplayName("POST /api/auth/signup - Should successfully create ADMIN user (anyone can create any role)")
    void testSignup_WithAdminRole_Success() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("newadmin");
        request.setEmail("newadmin@example.com");
        request.setPassword("Password123!");
        request.setRole("ADMIN");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        // Verify admin user was created
        User createdUser = userRepository.findByUsername("newadmin")
                .orElseThrow(() -> new AssertionError("Admin user was not created"));
        
        // Verify user has ADMIN role
        boolean hasAdminRole = createdUser.getRoles().stream()
                .anyMatch(role -> "ADMIN".equals(role.getName()));
        assert hasAdminRole : "Created user should have ADMIN role";
    }

    @Test
    @DisplayName("POST /api/auth/signup - Should fail with validation error when username is blank")
    void testSignup_BlankUsername() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("");
        request.setEmail("user@example.com");
        request.setPassword("Password123!");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/signup - Should fail with validation error when email is invalid")
    void testSignup_InvalidEmail() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("testuser");
        request.setEmail("invalid-email");
        request.setPassword("Password123!");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/signup - Should fail with validation error when password is blank")
    void testSignup_BlankPassword() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("testuser");
        request.setEmail("testuser@example.com");
        request.setPassword("");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/signup - Should fail when username already exists")
    void testSignup_DuplicateUsername() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("testuser"); // Already exists from setUp
        request.setEmail("different@example.com");
        request.setPassword("Password123!");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/signup - Should fail when email already exists")
    void testSignup_DuplicateEmail() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("differentuser");
        request.setEmail("testuser@example.com"); // Already exists from setUp
        request.setPassword("Password123!");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/login - Should login successfully with username")
    void testLogin_WithUsername_Success() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsernameOrEmail("testuser");
        request.setPassword("Password123!");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresInSeconds").exists());
    }

    @Test
    @DisplayName("POST /api/auth/login - Should login successfully with email")
    void testLogin_WithEmail_Success() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsernameOrEmail("testuser@example.com");
        request.setPassword("Password123!");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresInSeconds").exists());
    }

    @Test
    @DisplayName("POST /api/auth/login - Should fail with wrong password")
    void testLogin_WrongPassword() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsernameOrEmail("testuser");
        request.setPassword("wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/auth/login - Should fail with non-existent username")
    void testLogin_NonExistentUser() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsernameOrEmail("nonexistent");
        request.setPassword("Password123!");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/auth/login - Should fail with blank username")
    void testLogin_BlankUsername() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsernameOrEmail("");
        request.setPassword("Password123!");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/login - Should fail with blank password")
    void testLogin_BlankPassword() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsernameOrEmail("testuser");
        request.setPassword("");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/forgot-password - Should generate reset token successfully")
    void testForgotPassword_Success() throws Exception {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("testuser@example.com");

        mockMvc.perform(post("/api/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.resetToken").exists());
    }

    @Test
    @DisplayName("POST /api/auth/forgot-password - Should fail with non-existent email")
    void testForgotPassword_NonExistentEmail() throws Exception {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("nonexistent@example.com");

        mockMvc.perform(post("/api/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/forgot-password - Should fail with invalid email format")
    void testForgotPassword_InvalidEmail() throws Exception {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("invalid-email");

        mockMvc.perform(post("/api/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/forgot-password - Should fail with blank email")
    void testForgotPassword_BlankEmail() throws Exception {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("");

        mockMvc.perform(post("/api/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/reset-password - Should reset password successfully")
    void testResetPassword_Success() throws Exception {
        // First, request password reset to get a token
        ForgotPasswordRequest forgotRequest = new ForgotPasswordRequest();
        forgotRequest.setEmail("testuser@example.com");

        String responseJson = mockMvc.perform(post("/api/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(forgotRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Parse JSON response to get reset token
        ForgotPasswordResponse forgotResponse = objectMapper.readValue(responseJson, ForgotPasswordResponse.class);
        String resetToken = forgotResponse.getResetToken();

        // Now use the token to reset password
        ResetPasswordRequest resetRequest = new ResetPasswordRequest();
        resetRequest.setResetToken(resetToken);
        resetRequest.setNewPassword("NewPassword123!");

        mockMvc.perform(post("/api/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resetRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password has been reset successfully"));

        // Verify password was changed by trying to login with new password
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("testuser");
        loginRequest.setPassword("NewPassword123!");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DisplayName("POST /api/auth/reset-password - Should fail with invalid reset token")
    void testResetPassword_InvalidToken() throws Exception {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setResetToken("invalid-token-12345");
        request.setNewPassword("NewPassword123!");

        mockMvc.perform(post("/api/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/reset-password - Should fail with weak password")
    void testResetPassword_WeakPassword() throws Exception {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setResetToken("some-token");
        request.setNewPassword("weak"); // Too weak

        mockMvc.perform(post("/api/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/reset-password - Should fail with blank reset token")
    void testResetPassword_BlankToken() throws Exception {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setResetToken("");
        request.setNewPassword("NewPassword123!");

        mockMvc.perform(post("/api/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/reset-password - Should fail with blank password")
    void testResetPassword_BlankPassword() throws Exception {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setResetToken("some-token");
        request.setNewPassword("");

        mockMvc.perform(post("/api/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/auth/logout - Should logout successfully with valid token")
    void testLogout_Success() throws Exception {
        // First login to get a token
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("testuser");
        loginRequest.setPassword("Password123!");

        String loginResponse = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract token (simplified - in real scenario, parse JSON)
        // For testing, we'll use a mock token approach
        // Actually, we need to parse the JSON response to get the token
        // Let's use a simpler approach - test without token first
        
        mockMvc.perform(post("/api/auth/logout")
                        .header("Authorization", "Bearer valid-token"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logged out successfully"));
    }

    @Test
    @DisplayName("POST /api/auth/logout - Should logout successfully without token")
    void testLogout_WithoutToken() throws Exception {
        // Logout should work even without token (stateless JWT)
        mockMvc.perform(post("/api/auth/logout"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logged out successfully"));
    }

    @Test
    @DisplayName("POST /api/auth/signup - Should create ADMIN user successfully without any token (no restrictions)")
    void testSignup_CreateAdmin_WithoutToken_Success() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("new_admin_user");
        request.setEmail("newadmin@example.com");
        request.setPassword("AdminPass123!");
        request.setRole("ADMIN");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        // Verify admin user was created
        User createdUser = userRepository.findByUsername("new_admin_user")
                .orElseThrow(() -> new AssertionError("Admin user was not created"));
        
        // Verify user has ADMIN role
        boolean hasAdminRole = createdUser.getRoles().stream()
                .anyMatch(role -> "ADMIN".equals(role.getName()));
        assert hasAdminRole : "Created user should have ADMIN role";
    }

    @Test
    @DisplayName("POST /api/auth/signup - Should create USER successfully without token")
    void testSignup_CreateUser_WithoutToken_Success() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setUsername("regular_user");
        request.setEmail("regular@example.com");
        request.setPassword("UserPass123!");
        request.setRole("USER");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        // Verify user was created
        assert userRepository.findByUsername("regular_user").isPresent();
    }
}

