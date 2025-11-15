Project name: com.example.corporate (artifactId: corporate-app)
Java: 17+
Spring Boot: 3.2.x (specified in pom)

Quick start (local MySQL - recommended):
1. Install MySQL locally and create database:
   $ mysql -u root -p
   CREATE DATABASE corporate_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   CREATE USER 'corp'@'localhost' IDENTIFIED BY 'password';
   GRANT ALL PRIVILEGES ON corporate_db.* TO 'corp'@'localhost';
   FLUSH PRIVILEGES;

2. Copy .env.example -> .env and edit if desired (or set env vars). Default application.yml uses placeholders.

3. Build and run:
   $ mvn clean package
   $ mvn spring-boot:run
   OR run the generated jar:
   $ java -jar target/corporate-app-0.0.1-SNAPSHOT.jar

4. Default app will connect to local MySQL using values in application.yml / environment.

Docker (optional):
- Dockerfile provided. Optional docker-compose to stand up MySQL and the app:
  $ docker-compose up --build
- By default the README and configs assume local MySQL. Use docker-compose only if you prefer containerized MySQL.

Run tests:
  $ mvn test

Useful commands:
  - Package: mvn clean package
  - Run: mvn spring-boot:run
  - Tests: mvn test

Postman / curl:
- Example curl commands included at the end of this doc.

Notes:
- Logging: Logback configured in src/main/resources/logback-spring.xml prints timestamp, level, thread, logger, class, file:line and stacktraces.
- Flyway: migrations located in src/main/resources/db/migration and run on startup.
- JWT secret: set via environment var JWT_SECRET or in application.yml for dev profile.




Security improvements completed
1. JWT token validation (JwtUtils.java)
Proper expiration checks
Specific exception handling (ExpiredJwtException, MalformedJwtException, etc.)
Secret key length validation (minimum 32 characters)
Roles included in JWT claims
Methods to extract roles and expiration dates from tokens
2. User authentication (CustomUserDetailsService.java)
Checks if user account is enabled before authentication
Improved error messages (doesn't leak user existence)
Enhanced logging for security events
Uses User.builder() for better configuration
3. Password security (AuthServiceImpl.java)
Password strength validation:
Minimum 8 characters, maximum 128
Requires uppercase, lowercase, digit, and special character
New users are enabled by default
Better error messages for validation failures
4. JWT authentication filter (JwtAuthenticationFilter.java)
Verifies user is still enabled on each request
Clears security context on errors
Improved error handling and logging
Returns appropriate HTTP status codes
5. Security configuration (SecurityConfig.java)
CORS configuration for cross-origin requests
Custom authentication entry point for 401 responses
Custom access denied handler for 403 responses
Better exception handling configuration
6. Exception handling (GlobalExceptionHandler.java)
Handles SecurityException with 403 responses
Handles AuthenticationException with 401 responses
Handles AccessDeniedException with 403 responses
Generic error messages to avoid information leakage
7. DTO validation (SignupRequest.java)
Custom validation messages
Email format validation
8. Controller improvements (EmployeeController.java)
Proper exception handling using ResourceNotFoundException
Better error messages for missing resources
9. Password utility (PasswordUtil.java)
Utility class for password validation
Method to get password requirements message
10. Configuration (application.properties)
Security note about using environment variables for JWT secret in production
Documentation about secret key length requirements
Security best practices implemented
Strong password requirements
JWT token validation with expiration checks
User account status verification
Proper error handling without information leakage
CORS configuration
Security context cleanup on errors
Enhanced logging for security events
Role-based access control maintained
Recommendations for production
Use environment variables for JWT secret: export APP_JWT_SECRET="your-secret"
Consider implementing:
Rate limiting for login attempts
Account lockout after failed attempts
Refresh token mechanism
Token blacklisting for logout
Password reset functionality
Audit logging for security events
All changes have been tested for compilation errors and follow Spring Security best practices. The code is now more secure and production-ready.
