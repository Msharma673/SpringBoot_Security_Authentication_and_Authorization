package controller;

import com.SpringBootSecurity.SpringBootSecurityAuthenticationAndAuthorization.SpringBootSecurityAuthenticationAndAuthorizationApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.manager.ManagerCreateRequest;
import model.Manager;
import model.Role;
import model.User;
import repository.ManagerRepository;
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
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SpringBootSecurityAuthenticationAndAuthorizationApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("ManagerController API Tests")
class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ManagerRepository managerRepository;

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
    private User regularUser;
    private String adminToken;
    private String userToken;

    @BeforeEach
    void setUp() {
        // Clean up
        managerRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        // Create roles
        adminRole = new Role();
        adminRole.setName("ADMIN");
        roleRepository.save(adminRole);

        userRole = new Role();
        userRole.setName("USER");
        roleRepository.save(userRole);

        // Create admin user
        adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setEmail("admin@example.com");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setEnabled(true);
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRoles(adminRoles);
        userRepository.save(adminUser);
        adminToken = "Bearer " + jwtUtils.generateToken(adminUser.getUsername(),
                adminUser.getRoles().stream().map(Role::getName).toList());

        // Create regular user
        regularUser = new User();
        regularUser.setUsername("user");
        regularUser.setEmail("user@example.com");
        regularUser.setPassword(passwordEncoder.encode("user123"));
        regularUser.setEnabled(true);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        regularUser.setRoles(userRoles);
        userRepository.save(regularUser);
        userToken = "Bearer " + jwtUtils.generateToken(regularUser.getUsername(),
                regularUser.getRoles().stream().map(Role::getName).toList());
    }

    @Test
    @DisplayName("POST /api/managers - Should create manager successfully as ADMIN")
    void testCreateManager_AsAdmin_Success() throws Exception {
        ManagerCreateRequest request = new ManagerCreateRequest();
        request.setName("John Manager");
        request.setDesignation("Senior Manager");
        request.setExperience(10);
        request.setCity("New York");

        mockMvc.perform(post("/api/managers")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Manager"))
                .andExpect(jsonPath("$.designation").value("Senior Manager"))
                .andExpect(jsonPath("$.experience").value(10))
                .andExpect(jsonPath("$.city").value("New York"));
    }

    @Test
    @DisplayName("POST /api/managers - Should return 403 when USER tries to create")
    void testCreateManager_AsUser_Forbidden() throws Exception {
        ManagerCreateRequest request = new ManagerCreateRequest();
        request.setName("John Manager");

        mockMvc.perform(post("/api/managers")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /api/managers - Should fail without authentication")
    void testCreateManager_Unauthorized() throws Exception {
        ManagerCreateRequest request = new ManagerCreateRequest();
        request.setName("John Manager");

        mockMvc.perform(post("/api/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/managers - Should fail with validation error when name is blank")
    void testCreateManager_BlankName() throws Exception {
        ManagerCreateRequest request = new ManagerCreateRequest();
        request.setName("");

        mockMvc.perform(post("/api/managers")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/managers/{id} - Should get manager by ID as ADMIN")
    void testGetManagerById_AsAdmin_Success() throws Exception {
        Manager manager = new Manager();
        manager.setName("Test Manager");
        manager.setDesignation("Senior Manager");
        manager.setExperience(10);
        manager.setCity("New York");
        Manager saved = managerRepository.save(manager);

        mockMvc.perform(get("/api/managers/{id}", saved.getId())
                        .header("Authorization", adminToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.name").value("Test Manager"))
                .andExpect(jsonPath("$.designation").value("Senior Manager"));
    }

    @Test
    @DisplayName("GET /api/managers/{id} - Should get manager by ID as USER")
    void testGetManagerById_AsUser_Success() throws Exception {
        Manager manager = new Manager();
        manager.setName("Test Manager");
        Manager saved = managerRepository.save(manager);

        mockMvc.perform(get("/api/managers/{id}", saved.getId())
                        .header("Authorization", userToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.name").value("Test Manager"));
    }

    @Test
    @DisplayName("GET /api/managers/{id} - Should return 404 when manager not found")
    void testGetManagerById_NotFound() throws Exception {
        mockMvc.perform(get("/api/managers/{id}", 999L)
                        .header("Authorization", adminToken))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/managers/{id} - Should fail without authentication")
    void testGetManagerById_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/managers/{id}", 1L))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/managers - Should get all managers as ADMIN")
    void testGetAllManagers_AsAdmin_Success() throws Exception {
        // Create multiple managers
        Manager m1 = new Manager();
        m1.setName("Manager 1");
        managerRepository.save(m1);

        Manager m2 = new Manager();
        m2.setName("Manager 2");
        managerRepository.save(m2);

        mockMvc.perform(get("/api/managers")
                        .header("Authorization", adminToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/managers - Should return 403 when USER tries to get all")
    void testGetAllManagers_AsUser_Forbidden() throws Exception {
        mockMvc.perform(get("/api/managers")
                        .header("Authorization", userToken))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /api/managers - Should fail without authentication")
    void testGetAllManagers_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/managers"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("PUT /api/managers/{id} - Should update manager successfully as ADMIN")
    void testUpdateManager_AsAdmin_Success() throws Exception {
        Manager manager = new Manager();
        manager.setName("Original Name");
        manager.setDesignation("Manager");
        Manager saved = managerRepository.save(manager);

        ManagerCreateRequest request = new ManagerCreateRequest();
        request.setName("Updated Name");
        request.setDesignation("Senior Manager");
        request.setExperience(15);
        request.setCity("Los Angeles");

        mockMvc.perform(put("/api/managers/{id}", saved.getId())
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.designation").value("Senior Manager"))
                .andExpect(jsonPath("$.experience").value(15))
                .andExpect(jsonPath("$.city").value("Los Angeles"));
    }

    @Test
    @DisplayName("PUT /api/managers/{id} - Should return 403 when USER tries to update")
    void testUpdateManager_AsUser_Forbidden() throws Exception {
        Manager manager = new Manager();
        manager.setName("Test Manager");
        Manager saved = managerRepository.save(manager);

        ManagerCreateRequest request = new ManagerCreateRequest();
        request.setName("Updated Name");

        mockMvc.perform(put("/api/managers/{id}", saved.getId())
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("PUT /api/managers/{id} - Should return 404 when manager not found")
    void testUpdateManager_NotFound() throws Exception {
        ManagerCreateRequest request = new ManagerCreateRequest();
        request.setName("Test Manager");

        mockMvc.perform(put("/api/managers/{id}", 999L)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/managers/{id} - Should fail without authentication")
    void testUpdateManager_Unauthorized() throws Exception {
        ManagerCreateRequest request = new ManagerCreateRequest();
        request.setName("Test Manager");

        mockMvc.perform(put("/api/managers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("PUT /api/managers/{id} - Should fail with validation error when name is blank")
    void testUpdateManager_BlankName() throws Exception {
        Manager manager = new Manager();
        manager.setName("Test Manager");
        Manager saved = managerRepository.save(manager);

        ManagerCreateRequest request = new ManagerCreateRequest();
        request.setName("");

        mockMvc.perform(put("/api/managers/{id}", saved.getId())
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/managers/{id} - Should delete manager successfully as ADMIN")
    void testDeleteManager_AsAdmin_Success() throws Exception {
        Manager manager = new Manager();
        manager.setName("Test Manager");
        Manager saved = managerRepository.save(manager);

        mockMvc.perform(delete("/api/managers/{id}", saved.getId())
                        .header("Authorization", adminToken))
                .andDo(print())
                .andExpect(status().isNoContent());

        // Verify manager was deleted
        assert !managerRepository.existsById(saved.getId());
    }

    @Test
    @DisplayName("DELETE /api/managers/{id} - Should return 403 when USER tries to delete")
    void testDeleteManager_AsUser_Forbidden() throws Exception {
        Manager manager = new Manager();
        manager.setName("Test Manager");
        Manager saved = managerRepository.save(manager);

        mockMvc.perform(delete("/api/managers/{id}", saved.getId())
                        .header("Authorization", userToken))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("DELETE /api/managers/{id} - Should return 404 when manager not found")
    void testDeleteManager_NotFound() throws Exception {
        mockMvc.perform(delete("/api/managers/{id}", 999L)
                        .header("Authorization", adminToken))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/managers/{id} - Should fail without authentication")
    void testDeleteManager_Unauthorized() throws Exception {
        mockMvc.perform(delete("/api/managers/{id}", 1L))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}

