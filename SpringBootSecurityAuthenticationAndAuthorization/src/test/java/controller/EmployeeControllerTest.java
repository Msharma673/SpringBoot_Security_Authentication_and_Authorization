package controller;

import com.SpringBootSecurity.SpringBootSecurityAuthenticationAndAuthorization.SpringBootSecurityAuthenticationAndAuthorizationApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.employee.EmployeeCreateRequest;
import model.Employee;
import model.Manager;
import model.Role;
import model.User;
import repository.EmployeeRepository;
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
@DisplayName("EmployeeController API Tests")
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

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
    private Manager testManager;
    private String adminToken;
    private String userToken;

    @BeforeEach
    void setUp() {
        // Clean up
        employeeRepository.deleteAll();
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

        // Create test manager
        testManager = new Manager();
        testManager.setName("Test Manager");
        testManager.setDesignation("Senior Manager");
        testManager.setExperience(10);
        testManager.setCity("New York");
        managerRepository.save(testManager);
    }

    @Test
    @DisplayName("POST /api/employees - Should create employee successfully as ADMIN")
    void testCreateEmployee_AsAdmin_Success() throws Exception {
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setPhone("1234567890");
        request.setPosition("Developer");
        request.setManagerId(testManager.getId());

        mockMvc.perform(post("/api/employees")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.managerId").value(testManager.getId()));
    }

    @Test
    @DisplayName("POST /api/employees - Should create employee successfully as USER")
    void testCreateEmployee_AsUser_Success() throws Exception {
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setFirstName("Jane");
        request.setLastName("Smith");
        request.setEmail("jane.smith@example.com");
        request.setPhone("9876543210");
        request.setPosition("Designer");

        mockMvc.perform(post("/api/employees")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.email").value("jane.smith@example.com"));
    }

    @Test
    @DisplayName("POST /api/employees - Should fail without authentication")
    void testCreateEmployee_Unauthorized() throws Exception {
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setFirstName("John");
        request.setEmail("john@example.com");

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/employees - Should fail with validation error when firstName is blank")
    void testCreateEmployee_BlankFirstName() throws Exception {
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setFirstName("");
        request.setEmail("test@example.com");

        mockMvc.perform(post("/api/employees")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/employees - Should fail with validation error when email is invalid")
    void testCreateEmployee_InvalidEmail() throws Exception {
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setFirstName("John");
        request.setEmail("invalid-email");

        mockMvc.perform(post("/api/employees")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/employees/{id} - Should get employee by ID as ADMIN")
    void testGetEmployeeById_AsAdmin_Success() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john@example.com");
        employee.setPhone("1234567890");
        employee.setPosition("Developer");
        employee.setManager(testManager);
        Employee saved = employeeRepository.save(employee);

        mockMvc.perform(get("/api/employees/{id}", saved.getId())
                        .header("Authorization", adminToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    @DisplayName("GET /api/employees/{id} - Should get employee by ID as USER")
    void testGetEmployeeById_AsUser_Success() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Jane");
        employee.setEmail("jane@example.com");
        Employee saved = employeeRepository.save(employee);

        mockMvc.perform(get("/api/employees/{id}", saved.getId())
                        .header("Authorization", userToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }

    @Test
    @DisplayName("GET /api/employees/{id} - Should return 404 when employee not found")
    void testGetEmployeeById_NotFound() throws Exception {
        mockMvc.perform(get("/api/employees/{id}", 999L)
                        .header("Authorization", adminToken))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/employees/{id} - Should fail without authentication")
    void testGetEmployeeById_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/employees/{id}", 1L))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/employees - Should get all employees as ADMIN")
    void testGetAllEmployees_AsAdmin_Success() throws Exception {
        // Create multiple employees
        Employee emp1 = new Employee();
        emp1.setFirstName("John");
        emp1.setEmail("john@example.com");
        employeeRepository.save(emp1);

        Employee emp2 = new Employee();
        emp2.setFirstName("Jane");
        emp2.setEmail("jane@example.com");
        employeeRepository.save(emp2);

        mockMvc.perform(get("/api/employees")
                        .header("Authorization", adminToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/employees - Should get all employees as USER")
    void testGetAllEmployees_AsUser_Success() throws Exception {
        Employee emp = new Employee();
        emp.setFirstName("John");
        emp.setEmail("john@example.com");
        employeeRepository.save(emp);

        mockMvc.perform(get("/api/employees")
                        .header("Authorization", userToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET /api/employees - Should fail without authentication")
    void testGetAllEmployees_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("PUT /api/employees/{id} - Should update employee successfully as ADMIN")
    void testUpdateEmployee_AsAdmin_Success() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setEmail("john@example.com");
        Employee saved = employeeRepository.save(employee);

        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setFirstName("John Updated");
        request.setLastName("Doe");
        request.setEmail("john.updated@example.com");
        request.setPhone("1111111111");
        request.setPosition("Senior Developer");

        mockMvc.perform(put("/api/employees/{id}", saved.getId())
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John Updated"))
                .andExpect(jsonPath("$.email").value("john.updated@example.com"));
    }

    @Test
    @DisplayName("PUT /api/employees/{id} - Should update employee successfully as USER")
    void testUpdateEmployee_AsUser_Success() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Jane");
        employee.setEmail("jane@example.com");
        Employee saved = employeeRepository.save(employee);

        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setFirstName("Jane Updated");
        request.setEmail("jane.updated@example.com");

        mockMvc.perform(put("/api/employees/{id}", saved.getId())
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane Updated"));
    }

    @Test
    @DisplayName("PUT /api/employees/{id} - Should return 404 when employee not found")
    void testUpdateEmployee_NotFound() throws Exception {
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setFirstName("John");
        request.setEmail("john@example.com");

        mockMvc.perform(put("/api/employees/{id}", 999L)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/employees/{id} - Should fail without authentication")
    void testUpdateEmployee_Unauthorized() throws Exception {
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setFirstName("John");
        request.setEmail("john@example.com");

        mockMvc.perform(put("/api/employees/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("DELETE /api/employees/{id} - Should delete employee successfully as ADMIN")
    void testDeleteEmployee_AsAdmin_Success() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setEmail("john@example.com");
        Employee saved = employeeRepository.save(employee);

        mockMvc.perform(delete("/api/employees/{id}", saved.getId())
                        .header("Authorization", adminToken))
                .andDo(print())
                .andExpect(status().isNoContent());

        // Verify employee was deleted
        assert !employeeRepository.existsById(saved.getId());
    }

    @Test
    @DisplayName("DELETE /api/employees/{id} - Should return 403 when USER tries to delete")
    void testDeleteEmployee_AsUser_Forbidden() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setEmail("john@example.com");
        Employee saved = employeeRepository.save(employee);

        mockMvc.perform(delete("/api/employees/{id}", saved.getId())
                        .header("Authorization", userToken))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("DELETE /api/employees/{id} - Should return 404 when employee not found")
    void testDeleteEmployee_NotFound() throws Exception {
        mockMvc.perform(delete("/api/employees/{id}", 999L)
                        .header("Authorization", adminToken))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/employees/{id} - Should fail without authentication")
    void testDeleteEmployee_Unauthorized() throws Exception {
        mockMvc.perform(delete("/api/employees/{id}", 1L))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}

