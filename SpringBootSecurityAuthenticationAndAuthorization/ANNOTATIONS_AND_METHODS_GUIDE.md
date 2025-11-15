# Annotations and Important Methods Guide

This document explains all the annotations and important methods used in this Spring Boot Security project in simple English.

---

## Table of Contents

1. [Spring Framework Annotations](#spring-framework-annotations)
2. [Spring Security Annotations](#spring-security-annotations)
3. [JPA/Hibernate Annotations](#jpahibernate-annotations)
4. [Jakarta Validation Annotations](#jakarta-validation-annotations)
5. [Lombok Annotations](#lombok-annotations)
6. [Important Methods](#important-methods)

---

## Spring Framework Annotations

### `@SpringBootApplication`
**What it does:** This is the main annotation that starts a Spring Boot application. It combines three annotations:
- `@Configuration` - Tells Spring this class has configuration settings
- `@EnableAutoConfiguration` - Lets Spring automatically configure the application
- `@ComponentScan` - Tells Spring to scan for components in the package

**Where used:** Main application class (`SpringBootSecurityAuthenticationAndAuthorizationApplication.java`)

**Example:**
```java
@SpringBootApplication
public class SpringBootSecurityAuthenticationAndAuthorizationApplication {
    // Application starts here
}
```

---

### `@Component`
**What it does:** Marks a class as a Spring component. Spring will automatically create an instance of this class and manage it.

**Where used:** 
- `DataInitializer.java` - Initializes default data when application starts
- `JwtUtils.java` - Utility class for JWT token operations

**Example:**
```java
@Component
public class DataInitializer {
    // Spring will create this automatically
}
```

---

### `@Service`
**What it does:** Marks a class as a service layer component. Similar to `@Component` but specifically used for business logic classes.

**Where used:**
- `AuthServiceImpl.java` - Handles authentication and authorization logic
- `ManagerServiceImpl.java` - Handles manager-related business logic
- `CustomUserDetailsService.java` - Loads user details for Spring Security

**Example:**
```java
@Service
public class AuthServiceImpl implements AuthService {
    // Business logic here
}
```

---

### `@RestController`
**What it does:** Combines `@Controller` and `@ResponseBody`. Used for REST API endpoints. Automatically converts return values to JSON.

**Where used:**
- `AuthController.java` - Authentication endpoints (signup, login, etc.)
- `EmployeeController.java` - Employee management endpoints
- `ManagerController.java` - Manager management endpoints

**Example:**
```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // REST endpoints here
}
```

---

### `@RestControllerAdvice`
**What it does:** Used for global exception handling across all controllers. Catches exceptions and returns appropriate error responses.

**Where used:** `GlobalExceptionHandler.java`

**Example:**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Handles all exceptions from controllers
}
```

---

### `@Configuration`
**What it does:** Marks a class as a configuration class. Contains bean definitions and configuration settings.

**Where used:** `SecurityConfig.java` - Security configuration

**Example:**
```java
@Configuration
public class SecurityConfig {
    // Security settings here
}
```

---

### `@Bean`
**What it does:** Tells Spring to create and manage an object. Used in configuration classes to define reusable components.

**Where used:** `SecurityConfig.java` - Creates security-related beans like password encoder, authentication manager, etc.

**Example:**
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

---

### `@Autowired`
**What it does:** Automatically injects dependencies. Spring finds the required object and provides it automatically.

**Where used:** Controllers and services to inject dependencies

**Example:**
```java
@Autowired
public AuthController(AuthService authService) {
    this.authService = authService;
}
```

---

### `@RequestMapping`
**What it does:** Maps HTTP requests to controller methods. Defines the base URL path for all methods in a controller.

**Where used:** All controllers to define base paths like `/api/auth`, `/api/employees`, `/api/managers`

**Example:**
```java
@RequestMapping("/api/auth")
public class AuthController {
    // All methods will have /api/auth prefix
}
```

---

### `@GetMapping`
**What it does:** Maps HTTP GET requests to a method. Used to retrieve data.

**Where used:** Controllers for read operations

**Example:**
```java
@GetMapping("/{id}")
public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
    // Returns employee by ID
}
```

---

### `@PostMapping`
**What it does:** Maps HTTP POST requests to a method. Used to create new resources or perform actions.

**Where used:** Controllers for create operations (signup, login, create employee, etc.)

**Example:**
```java
@PostMapping("/signup")
public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
    // Creates new user
}
```

---

### `@PutMapping`
**What it does:** Maps HTTP PUT requests to a method. Used to update existing resources.

**Where used:** Controllers for update operations

**Example:**
```java
@PutMapping("/{id}")
public ResponseEntity<EmployeeDto> update(@PathVariable Long id, @RequestBody EmployeeCreateRequest req) {
    // Updates employee
}
```

---

### `@DeleteMapping`
**What it does:** Maps HTTP DELETE requests to a method. Used to delete resources.

**Where used:** Controllers for delete operations

**Example:**
```java
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    // Deletes employee
}
```

---

### `@PathVariable`
**What it does:** Extracts values from the URL path. Used to get parameters from the URL.

**Where used:** Controller methods that need ID or other path parameters

**Example:**
```java
@GetMapping("/{id}")
public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
    // Gets ID from URL like /api/employees/123
}
```

---

### `@RequestBody`
**What it does:** Converts JSON from the request body into a Java object. Used to receive data in POST/PUT requests.

**Where used:** Controller methods that accept data in request body

**Example:**
```java
@PostMapping("/signup")
public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
    // Converts JSON to SignupRequest object
}
```

---

### `@RequestHeader`
**What it does:** Extracts values from HTTP request headers. Used to get header information like Authorization token.

**Where used:** `AuthController.logout()` method to get Authorization header

**Example:**
```java
@PostMapping("/logout")
public ResponseEntity<MessageResponse> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
    // Gets Authorization header from request
}
```

---

### `@Value`
**What it does:** Injects values from configuration files (like `application.properties`) into fields or constructor parameters.

**Where used:** `JwtUtils.java` to get JWT secret and expiration time from configuration

**Example:**
```java
public JwtUtils(@Value("${app.jwt.secret}") String secret,
                @Value("${app.jwt.expirationSeconds}") long expirationSeconds) {
    // Gets values from application.properties
}
```

---

### `@ComponentScan`
**What it does:** Tells Spring which packages to scan for components (services, controllers, etc.).

**Where used:** Main application class

**Example:**
```java
@ComponentScan(basePackages = {"config", "security", "controller", "service", "exception"})
```

---

### `@EntityScan`
**What it does:** Tells Spring which packages contain JPA entity classes.

**Where used:** Main application class

**Example:**
```java
@EntityScan(basePackages = {"model"})
```

---

### `@EnableJpaRepositories`
**What it does:** Enables JPA repository scanning. Tells Spring where to find repository interfaces.

**Where used:** Main application class

**Example:**
```java
@EnableJpaRepositories(basePackages = {"repository"})
```

---

### `@Profile`
**What it does:** Makes a component active only in specific profiles. Used to enable/disable components based on environment.

**Where used:** `DataInitializer.java` - Only runs when not in test profile

**Example:**
```java
@Profile("!test")
public class DataInitializer {
    // Only runs when not in test mode
}
```

---

### `@Override`
**What it does:** Indicates that a method is overriding a method from a parent class or interface. Helps catch errors if method signature changes.

**Where used:** All service implementations and interface implementations

**Example:**
```java
@Override
public void signup(SignupRequest request) {
    // Implements method from AuthService interface
}
```

---

### `@ExceptionHandler`
**What it does:** Handles specific exceptions thrown by controllers. Used in exception handler classes.

**Where used:** `GlobalExceptionHandler.java` to handle different types of exceptions

**Example:**
```java
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
    // Handles ResourceNotFoundException
}
```

---

## Spring Security Annotations

### `@EnableWebSecurity`
**What it does:** Enables Spring Security's web security support. Required for security configuration.

**Where used:** `SecurityConfig.java`

**Example:**
```java
@EnableWebSecurity
public class SecurityConfig {
    // Security configuration
}
```

---

### `@EnableMethodSecurity`
**What it does:** Enables method-level security. Allows using `@PreAuthorize` and other security annotations on methods.

**Where used:** `SecurityConfig.java`

**Example:**
```java
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    // Enables @PreAuthorize annotation
}
```

---

### `@PreAuthorize`
**What it does:** Checks user permissions before allowing access to a method. Used for role-based access control.

**Where used:** Controller methods to restrict access based on roles

**Example:**
```java
@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    // Only ADMIN can access this
}
```

**Common expressions:**
- `hasRole('ADMIN')` - User must have ADMIN role
- `hasAnyRole('USER','ADMIN')` - User must have either USER or ADMIN role
- `hasAuthority('ROLE_ADMIN')` - User must have specific authority

---

## JPA/Hibernate Annotations

### `@Entity`
**What it does:** Marks a class as a JPA entity. This class represents a database table.

**Where used:** All model classes (`User.java`, `Employee.java`, `Manager.java`, `Role.java`)

**Example:**
```java
@Entity
@Table(name = "users")
public class User {
    // Represents users table in database
}
```

---

### `@Table`
**What it does:** Specifies the database table name. If not provided, Spring uses the class name.

**Where used:** All entity classes

**Example:**
```java
@Table(name = "users")
public class User {
    // Maps to "users" table
}
```

---

### `@Id`
**What it does:** Marks a field as the primary key of the table.

**Where used:** All entity classes on the ID field

**Example:**
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

---

### `@GeneratedValue`
**What it does:** Specifies how the primary key value is generated. `GenerationType.IDENTITY` means the database auto-generates the ID.

**Where used:** All entity classes on the ID field

**Example:**
```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
// Database will auto-increment this value
```

---

### `@Column`
**What it does:** Maps a field to a database column. Can specify constraints like `nullable`, `unique`, etc.

**Where used:** Entity classes to define column properties

**Example:**
```java
@Column(unique=true, nullable=false)
private String username;
// Username must be unique and cannot be null
```

---

### `@ManyToMany`
**What it does:** Defines a many-to-many relationship between two entities. For example, a user can have many roles, and a role can belong to many users.

**Where used:** `User.java` - relationship between User and Role

**Example:**
```java
@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
private Set<Role> roles;
// User can have multiple roles
```

---

### `@ManyToOne`
**What it does:** Defines a many-to-one relationship. Many employees can belong to one manager.

**Where used:** `Employee.java` - relationship between Employee and Manager

**Example:**
```java
@ManyToOne
@JoinColumn(name = "manager_id")
private Manager manager;
// Many employees can have one manager
```

---

### `@JoinTable`
**What it does:** Defines the join table for many-to-many relationships. Creates an intermediate table.

**Where used:** `User.java` for user-roles relationship

**Example:**
```java
@JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
// Creates user_roles table to link users and roles
```

---

### `@JoinColumn`
**What it does:** Specifies the foreign key column name in the database.

**Where used:** Entity relationships

**Example:**
```java
@JoinColumn(name = "manager_id")
private Manager manager;
// Creates manager_id column in employees table
```

---

### `@UniqueConstraint`
**What it does:** Defines unique constraints on table columns. Ensures no duplicate values.

**Where used:** `Employee.java` to make email unique

**Example:**
```java
@Table(name = "employees",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
// Email must be unique across all employees
```

---

## Jakarta Validation Annotations

### `@Valid`
**What it does:** Triggers validation of the request body. Checks all validation annotations on the object.

**Where used:** Controller methods that accept request bodies

**Example:**
```java
@PostMapping("/signup")
public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
    // Validates request before processing
}
```

---

### `@NotBlank`
**What it does:** Ensures a field is not null, not empty, and not just whitespace.

**Where used:** DTO classes for required string fields

**Example:**
```java
@NotBlank(message = "Username is required")
private String username;
// Username cannot be empty
```

---

### `@Email`
**What it does:** Validates that a string is a valid email address format.

**Where used:** DTO classes for email fields

**Example:**
```java
@Email(message = "Email must be a valid email address")
private String email;
// Must be valid email format like user@example.com
```

---

### `@Pattern`
**What it does:** Validates a string against a regular expression pattern.

**Where used:** `ResetPasswordRequest.java` for password validation

**Example:**
```java
@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
         message = "Password must contain at least 8 characters...")
private String newPassword;
// Validates password strength using regex
```

---

## Lombok Annotations

### `@Data`
**What it does:** Generates getters, setters, `toString()`, `equals()`, and `hashCode()` methods automatically. Reduces boilerplate code.

**Where used:** DTO classes and some entity classes

**Example:**
```java
@Data
public class SignupRequest {
    private String username;
    // Automatically generates getUsername(), setUsername(), etc.
}
```

---

### `@Getter`
**What it does:** Generates getter methods for all fields automatically.

**Where used:** Entity classes

**Example:**
```java
@Getter
public class User {
    private String username;
    // Automatically generates getUsername()
}
```

---

### `@Setter`
**What it does:** Generates setter methods for all fields automatically.

**Where used:** Entity classes

**Example:**
```java
@Setter
public class User {
    private String username;
    // Automatically generates setUsername()
}
```

---

### `@NoArgsConstructor`
**What it does:** Generates a constructor with no arguments automatically.

**Where used:** Entity classes and DTOs

**Example:**
```java
@NoArgsConstructor
public class User {
    // Automatically generates User() constructor
}
```

---

### `@AllArgsConstructor`
**What it does:** Generates a constructor with all fields as arguments automatically.

**Where used:** Entity classes and DTOs

**Example:**
```java
@AllArgsConstructor
public class User {
    private String username;
    private String email;
    // Automatically generates User(String username, String email) constructor
}
```

---

## Important Methods

### Authentication Methods

#### `authenticate(LoginRequest request)`
**Location:** `AuthServiceImpl.java`

**What it does:** Authenticates a user by checking username/email and password. If valid, generates a JWT token.

**How it works:**
1. Takes username/email and password from request
2. Uses Spring Security's `AuthenticationManager` to verify credentials
3. If valid, extracts user roles
4. Generates JWT token with username and roles
5. Returns token response

**Returns:** `JwtResponse` containing JWT token

---

#### `signup(SignupRequest request)`
**Location:** `AuthServiceImpl.java`

**What it does:** Creates a new user account with validation.

**How it works:**
1. Validates password strength (min 8 chars, uppercase, lowercase, digit, special char)
2. Checks if username already exists
3. Checks if email already exists
4. Encodes password using BCrypt
5. Assigns role (defaults to USER if not specified)
6. Saves user to database

**Returns:** Nothing (void), but throws exception if validation fails

---

#### `forgotPassword(ForgotPasswordRequest request)`
**Location:** `AuthServiceImpl.java`

**What it does:** Generates a password reset token for a user.

**How it works:**
1. Finds user by email
2. Generates a unique reset token (UUID)
3. Stores token with expiry time (15 minutes)
4. Returns token (in production, would send via email)

**Returns:** `ForgotPasswordResponse` with reset token

---

#### `resetPassword(ResetPasswordRequest request)`
**Location:** `AuthServiceImpl.java`

**What it does:** Resets user password using a valid reset token.

**How it works:**
1. Validates new password strength
2. Checks if reset token exists and is valid
3. Checks if token has expired
4. Finds user by email from token
5. Encodes and updates password
6. Removes used token

**Returns:** Nothing (void), but throws exception if token invalid/expired

---

#### `logout(String token)`
**Location:** `AuthServiceImpl.java`

**What it does:** Logs out a user. Since JWT is stateless, mainly logs the action.

**How it works:**
1. Extracts username from token
2. Logs logout action
3. In production, would add token to blacklist

**Returns:** Nothing (void)

---

### Security Methods

#### `loadUserByUsername(String usernameOrEmail)`
**Location:** `CustomUserDetailsService.java`

**What it does:** Loads user details from database for Spring Security authentication.

**How it works:**
1. Searches for user by username or email
2. Checks if user account is enabled
3. Extracts user roles
4. Creates Spring Security `UserDetails` object with username, password, and authorities

**Returns:** `UserDetails` object for Spring Security

**Throws:** `UsernameNotFoundException` if user not found or disabled

---

#### `generateToken(String username, List<String> roles)`
**Location:** `JwtUtils.java`

**What it does:** Creates a JWT token with user information.

**How it works:**
1. Creates token claims (username, roles, issue time, expiration)
2. Signs token with secret key
3. Returns compact JWT string

**Returns:** JWT token string

---

#### `validateJwtToken(String authToken)`
**Location:** `JwtUtils.java`

**What it does:** Validates if a JWT token is valid and not expired.

**How it works:**
1. Parses token and verifies signature
2. Checks if token is expired
3. Validates token has valid subject (username)
4. Handles various JWT exceptions

**Returns:** `true` if valid, `false` otherwise

---

#### `getUsernameFromJwt(String token)`
**Location:** `JwtUtils.java`

**What it does:** Extracts username from JWT token.

**How it works:**
1. Parses token
2. Extracts subject (username) from claims

**Returns:** Username string

---

#### `getRolesFromJwt(String token)`
**Location:** `JwtUtils.java`

**What it does:** Extracts user roles from JWT token.

**How it works:**
1. Parses token
2. Extracts roles from claims

**Returns:** List of role names

---

### Filter Methods

#### `doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)`
**Location:** `JwtAuthenticationFilter.java`

**What it does:** Intercepts every HTTP request to validate JWT token and set authentication.

**How it works:**
1. Extracts JWT token from Authorization header
2. Validates token if present
3. Loads user details if token valid
4. Sets authentication in Spring Security context
5. Allows request to continue

**Called:** Automatically for every request (except excluded paths)

---

#### `shouldNotFilter(HttpServletRequest request)`
**Location:** `JwtAuthenticationFilter.java`

**What it does:** Determines if JWT filter should skip processing for certain paths.

**How it works:**
1. Checks request path
2. Skips filtering for actuator endpoints

**Returns:** `true` to skip filtering, `false` to process

---

### Repository Methods

#### `findByUsername(String username)`
**Location:** `UserRepository.java`

**What it does:** Finds a user by username.

**Returns:** `Optional<User>` - empty if not found

---

#### `findByEmail(String email)`
**Location:** `UserRepository.java`

**What it does:** Finds a user by email address.

**Returns:** `Optional<User>` - empty if not found

---

#### `existsByUsername(String username)`
**Location:** `UserRepository.java`

**What it does:** Checks if a username already exists in database.

**Returns:** `Boolean` - true if exists, false otherwise

---

#### `existsByEmail(String email)`
**Location:** `UserRepository.java`

**What it does:** Checks if an email already exists in database.

**Returns:** `Boolean` - true if exists, false otherwise

---

#### `findByName(String name)`
**Location:** `RoleRepository.java`

**What it does:** Finds a role by name (e.g., "ADMIN", "USER").

**Returns:** `Optional<Role>` - empty if not found

---

#### `save(T entity)`
**Location:** All repositories (inherited from `JpaRepository`)

**What it does:** Saves an entity to database. Creates new record if ID is null, updates if ID exists.

**Returns:** Saved entity

---

#### `findById(Long id)`
**Location:** All repositories (inherited from `JpaRepository`)

**What it does:** Finds an entity by its ID.

**Returns:** `Optional<T>` - empty if not found

---

#### `findAll()`
**Location:** All repositories (inherited from `JpaRepository`)

**What it does:** Retrieves all entities from database.

**Returns:** `List<T>` of all entities

---

#### `deleteById(Long id)`
**Location:** All repositories (inherited from `JpaRepository`)

**What it does:** Deletes an entity by ID.

**Returns:** Nothing (void)

---

#### `existsById(Long id)`
**Location:** All repositories (inherited from `JpaRepository`)

**What it does:** Checks if an entity with given ID exists.

**Returns:** `Boolean` - true if exists, false otherwise

---

#### `count()`
**Location:** All repositories (inherited from `JpaRepository`)

**What it does:** Counts total number of entities in database.

**Returns:** `Long` - total count

---

### Exception Handler Methods

#### `handleNotFound(ResourceNotFoundException ex)`
**Location:** `GlobalExceptionHandler.java`

**What it does:** Handles cases when a requested resource is not found.

**Returns:** HTTP 404 (Not Found) response with error message

---

#### `handleBadRequest(IllegalArgumentException ex)`
**Location:** `GlobalExceptionHandler.java`

**What it does:** Handles invalid request parameters or arguments.

**Returns:** HTTP 400 (Bad Request) response with error message

---

#### `handleValidation(MethodArgumentNotValidException ex)`
**Location:** `GlobalExceptionHandler.java`

**What it does:** Handles validation errors from `@Valid` annotations.

**Returns:** HTTP 400 (Bad Request) response with validation error details

---

#### `handleAuthenticationException(AuthenticationException ex)`
**Location:** `GlobalExceptionHandler.java`

**What it does:** Handles authentication failures (invalid credentials).

**Returns:** HTTP 401 (Unauthorized) response

---

#### `handleAccessDeniedException(AccessDeniedException ex)`
**Location:** `GlobalExceptionHandler.java`

**What it does:** Handles authorization failures (insufficient permissions).

**Returns:** HTTP 403 (Forbidden) response

---

### Data Initialization Methods

#### `run(String... args)`
**Location:** `DataInitializer.java`

**What it does:** Runs automatically when application starts. Initializes default roles and users.

**How it works:**
1. Creates ADMIN and USER roles if they don't exist
2. Creates default admin user (username: admin, password: admin123)
3. Creates default regular user (username: user, password: user123)

**Called:** Automatically by Spring Boot on startup

---

### Password Validation Methods

#### `validatePassword(String password)`
**Location:** `AuthServiceImpl.java`

**What it does:** Validates password strength requirements.

**Requirements:**
- Minimum 8 characters
- Maximum 128 characters
- At least one uppercase letter
- At least one lowercase letter
- At least one digit
- At least one special character

**Throws:** `IllegalArgumentException` if password doesn't meet requirements

---

## Summary

This project uses:

- **Spring Framework annotations** for dependency injection, REST APIs, and configuration
- **Spring Security annotations** for authentication and authorization
- **JPA annotations** for database mapping and relationships
- **Jakarta Validation annotations** for input validation
- **Lombok annotations** to reduce boilerplate code

The important methods handle:
- User authentication and registration
- JWT token generation and validation
- Password reset functionality
- Role-based access control
- Database operations
- Exception handling
- Data initialization

All these work together to create a secure Spring Boot application with JWT-based authentication and role-based authorization.

