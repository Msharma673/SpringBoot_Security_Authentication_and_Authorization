# 📚 Project Overview - Complete File Structure and Run Commands

This document explains what each file does in the project and how to run the application.

## 📖 What is This Document?

**Definition**: This document is a comprehensive guide that explains every file in the project, what it does, why it exists, and how to use it.

**Purpose**: This document helps you:
- Understand the project structure (where files are located)
- Know what each file does (its responsibility)
- Understand why each file is needed (its purpose)
- Learn how to run the application (different methods)
- Understand the flow of the application (how everything connects)

**Why This Document is Important**: 
- **For Beginners**: Helps you understand how a Spring Boot project is organized
- **For Learning**: Explains the "why" behind each component
- **For Reference**: Quick lookup when you need to find a specific file
- **For Running**: Clear instructions on how to start the application
- **For Understanding**: Deep dive into how each component works and why it's structured that way

**What You'll Learn**:
- The purpose of each Java class
- Why we organize files in specific folders
- How different components work together
- Multiple ways to run and test the application
- Detailed explanations of each file's role and importance

---

## 📁 Project Structure

```
SpringBootSecurityAuthenticationAndAuthorization/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/SpringBootSecurity/.../
│   │   │   │   └── SpringBootSecurityAuthenticationAndAuthorizationApplication.java
│   │   │   ├── config/
│   │   │   │   ├── DataInitializer.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── EmployeeController.java
│   │   │   │   └── ManagerController.java
│   │   │   ├── dto/
│   │   │   │   ├── auth/
│   │   │   │   │   ├── SignupRequest.java
│   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   └── JwtResponse.java
│   │   │   │   ├── employee/
│   │   │   │   │   ├── EmployeeDto.java
│   │   │   │   │   └── EmployeeCreateRequest.java
│   │   │   │   └── manager/
│   │   │   │       ├── ManagerDto.java
│   │   │   │       └── ManagerCreateRequest.java
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   ├── model/
│   │   │   │   ├── User.java
│   │   │   │   ├── Role.java
│   │   │   │   ├── Employee.java
│   │   │   │   └── Manager.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── RoleRepository.java
│   │   │   │   ├── EmployeeRepository.java
│   │   │   │   └── ManagerRepository.java
│   │   │   ├── security/
│   │   │   │   ├── JwtUtils.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── CustomUserDetailsService.java
│   │   │   ├── service/
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── AuthServiceImpl.java
│   │   │   │   ├── ManagerService.java
│   │   │   │   └── ManagerServiceImpl.java
│   │   │   └── util/
│   │   │       └── PasswordUtil.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application.yml
│   │       ├── logback-spring.xml
│   │       └── db/
│   │           └── migration/
│   │               ├── V1__init.sql
│   │               └── V2__seed_roles_users.sql
│   └── test/
│       ├── java/
│       │   ├── controller/
│       │   │   ├── AuthControllerTest.java
│       │   │   ├── EmployeeControllerTest.java
│       │   │   └── ManagerControllerTest.java
│       │   └── com/SpringBootSecurity/.../
│       │       └── SpringBootSecurityAuthenticationAndAuthorizationApplicationTests.java
│       └── resources/
│           └── application-test.properties
├── pom.xml
├── mvnw
└── mvnw.cmd
```

---

## 📄 File Descriptions

### 🎯 Main Application Class

**File:** `SpringBootSecurityAuthenticationAndAuthorizationApplication.java`

**Definition**: This is the main class that starts the entire Spring Boot application. It's like the "ON" button for your application.

**What it does:**
- Main entry point of the Spring Boot application (where execution begins)
- Starts the Spring application context (loads all components and configurations)
- Configures component scanning for all packages (tells Spring where to find your classes)
- Initializes all Spring beans (creates instances of components)
- Starts the embedded Tomcat web server
- Connects to the database
- Runs DataInitializer to create default data

**Purpose**: This class:
- Bootstraps the entire application (starts everything)
- Initializes Spring Framework (loads all beans and configurations)
- Starts the embedded web server (Tomcat server on port 8095)
- Scans for components (finds all your controllers, services, repositories)
- Sets up the application context (the container that holds all Spring beans)

**Why We Need This**: Without this class, Spring Boot doesn't know where to start. It's like having a car but no ignition key. This class is the key that starts everything.

**How It Works** (Step-by-Step):
1. **Java Starts**: When you run this class, Java calls the `main()` method
2. **Spring Boot Initializes**: `SpringApplication.run()` starts Spring Boot framework
3. **Read Configuration**: Spring Boot reads `application.properties` for all settings
4. **Scan Components**: Spring scans all packages for classes with Spring annotations (@Component, @Service, @Controller, etc.)
5. **Create Beans**: Spring creates instances (beans) of all components
6. **Wire Dependencies**: Spring injects dependencies (constructor injection, field injection)
7. **Initialize Database**: Hibernate creates/updates database tables based on entities
8. **Run DataInitializer**: DataInitializer creates default roles and users
9. **Start Web Server**: Embedded Tomcat server starts on port 8095
10. **Application Ready**: Application is now running and ready to accept HTTP requests

**What Happens When Application Starts**:
- All controllers are registered and ready to handle requests
- Security configuration is loaded and active
- Database connection is established
- Default users (admin/admin123, user/user123) are created
- JWT filter is active and checking tokens
- All services are initialized and ready

**Location:** `src/main/java/com/SpringBootSecurity/.../`

---

### ⚙️ Configuration Files

#### 1. **SecurityConfig.java**

**What it does:**
- Configures Spring Security
- Sets up JWT authentication filter
- Defines which endpoints are public (signup, login) and which require authentication
- Configures password encoder (BCrypt)
- Enables method-level security (`@PreAuthorize`)

**Key Features:**
- Public endpoints: `/api/auth/**`
- All other endpoints require JWT token
- Stateless session management (no server-side sessions)

**Location:** `src/main/java/config/`

---

#### 2. **DataInitializer.java**

**What it does:**
- Runs automatically when application starts
- Creates default roles (ADMIN, USER) if they don't exist
- Creates default users (admin/admin123, user/user123) if they don't exist
- Only runs in non-test profiles

**Location:** `src/main/java/config/`

---

### 🎮 Controllers (API Endpoints)

#### 1. **AuthController.java**

**What it does:**
- Handles authentication endpoints
- `/api/auth/signup` - Create new user account
- `/api/auth/login` - Login and get JWT token

**Location:** `src/main/java/controller/`

**Endpoints:**
- `POST /api/auth/signup` - No authentication required
- `POST /api/auth/login` - No authentication required

---

#### 2. **EmployeeController.java**

**What it does:**
- Handles all employee-related operations
- Uses role-based access control

**Location:** `src/main/java/controller/`

**Endpoints:**
- `GET /api/employees` - Get all employees (USER, ADMIN)
- `POST /api/employees` - Create employee (USER, ADMIN)
- `GET /api/employees/{id}` - Get employee by ID (USER, ADMIN)
- `PUT /api/employees/{id}` - Update employee (USER, ADMIN)
- `DELETE /api/employees/{id}` - Delete employee (ADMIN only)

---

#### 3. **ManagerController.java**

**What it does:**
- Handles all manager-related operations
- Most endpoints require ADMIN role

**Location:** `src/main/java/controller/`

**Endpoints:**
- `GET /api/managers` - Get all managers (ADMIN only)
- `POST /api/managers` - Create manager (ADMIN only)
- `GET /api/managers/{id}` - Get manager by ID (USER, ADMIN)
- `PUT /api/managers/{id}` - Update manager (ADMIN only)
- `DELETE /api/managers/{id}` - Delete manager (ADMIN only)

---

### 🗄️ Model Classes (Database Entities)

#### 1. **User.java**

**What it does:**
- Represents user accounts in the database
- Stores username, email, password, enabled status
- Has many-to-many relationship with Role

**Database Table:** `users`

**Fields:**
- `id` - Primary key
- `username` - Unique username
- `email` - Unique email
- `password` - BCrypt hashed password
- `enabled` - Account status
- `roles` - Set of roles assigned to user

**Location:** `src/main/java/model/`

---

#### 2. **Role.java**

**What it does:**
- Represents user roles (ADMIN, USER)
- Has many-to-many relationship with User

**Database Table:** `roles`

**Fields:**
- `id` - Primary key
- `name` - Role name (ADMIN, USER)
- `users` - Set of users with this role

**Location:** `src/main/java/model/`

---

#### 3. **Employee.java**

**What it does:**
- Represents employee records
- Can be linked to a Manager

**Database Table:** `employees`

**Fields:**
- `id` - Primary key
- `firstName` - Employee first name
- `lastName` - Employee last name
- `email` - Unique email
- `phone` - Phone number
- `position` - Job position
- `manager` - Reference to Manager (optional)

**Location:** `src/main/java/model/`

---

#### 4. **Manager.java**

**What it does:**
- Represents manager records
- Can have multiple employees

**Database Table:** `managers`

**Fields:**
- `id` - Primary key
- `name` - Manager name
- `designation` - Job designation
- `experience` - Years of experience
- `city` - City location
- `employees` - List of employees under this manager

**Location:** `src/main/java/model/`

---

### 📦 Repository Interfaces (Database Access)

#### 1. **UserRepository.java**

**What it does:**
- Provides database operations for User entity
- Methods: `findByUsername()`, `findByEmail()`, `existsByUsername()`, etc.

**Location:** `src/main/java/repository/`

---

#### 2. **RoleRepository.java**

**What it does:**
- Provides database operations for Role entity
- Methods: `findByName()`, etc.

**Location:** `src/main/java/repository/`

---

#### 3. **EmployeeRepository.java**

**What it does:**
- Provides database operations for Employee entity
- Standard JPA repository methods

**Location:** `src/main/java/repository/`

---

#### 4. **ManagerRepository.java**

**What it does:**
- Provides database operations for Manager entity
- Standard JPA repository methods

**Location:** `src/main/java/repository/`

---

### 🔐 Security Components

#### 1. **JwtUtils.java**

**What it does:**
- Generates JWT tokens
- Validates JWT tokens
- Extracts username from tokens
- Handles token expiration

**Location:** `src/main/java/security/`

**Key Methods:**
- `generateToken()` - Creates JWT token with username and roles
- `validateToken()` - Checks if token is valid
- `getUsernameFromToken()` - Extracts username from token

---

#### 2. **JwtAuthenticationFilter.java**

**What it does:**
- Intercepts HTTP requests
- Extracts JWT token from Authorization header
- Validates token and sets authentication in Spring Security context
- Runs before every request

**Location:** `src/main/java/security/`

---

#### 3. **CustomUserDetailsService.java**

**What it does:**
- Loads user details from database for authentication
- Implements Spring Security's UserDetailsService
- Converts User entity to Spring Security UserDetails
- Checks if user account is enabled

**Location:** `src/main/java/security/`

---

### 🛠️ Service Layer

#### 1. **AuthService / AuthServiceImpl.java**

**What it does:**
- Handles user registration (signup)
- Handles user authentication (login)
- Validates user credentials
- Generates JWT tokens after successful login

**Location:** `src/main/java/service/`

**Key Methods:**
- `signup()` - Creates new user account
- `authenticate()` - Validates credentials and returns JWT token

---

#### 2. **ManagerService / ManagerServiceImpl.java**

**What it does:**
- Business logic for manager operations
- Converts between Manager entity and ManagerDto
- Handles CRUD operations for managers

**Location:** `src/main/java/service/`

**Key Methods:**
- `create()` - Create new manager
- `getById()` - Get manager by ID
- `getAll()` - Get all managers
- `update()` - Update manager
- `delete()` - Delete manager

---

### 📋 DTOs (Data Transfer Objects)

**What they do:**
- Transfer data between client and server
- Separate from database entities
- Used in API requests and responses

**Location:** `src/main/java/dto/`

**DTOs:**
- `SignupRequest` - User registration data
- `LoginRequest` - Login credentials
- `JwtResponse` - JWT token response
- `EmployeeDto` - Employee data for API
- `EmployeeCreateRequest` - Employee creation data
- `ManagerDto` - Manager data for API
- `ManagerCreateRequest` - Manager creation data

---

### ⚠️ Exception Handling

#### 1. **GlobalExceptionHandler.java**

**What it does:**
- Catches all exceptions in the application
- Converts exceptions to proper HTTP responses
- Handles validation errors
- Handles resource not found errors

**Location:** `src/main/java/exception/`

---

#### 2. **ResourceNotFoundException.java**

**What it does:**
- Custom exception for when resources are not found
- Used when employee/manager/user doesn't exist

**Location:** `src/main/java/exception/`

---

### 📝 Configuration Files

#### 1. **application.properties**

**What it does:**
- Main configuration file
- Database connection settings
- JWT secret and expiration
- Server port (8095)
- JPA/Hibernate settings
- Flyway migration settings

**Location:** `src/main/resources/`

**Key Settings:**
- `server.port=8095` - Application runs on port 8095
- `spring.datasource.*` - MySQL database connection
- `app.jwt.secret` - Secret key for JWT tokens
- `app.jwt.expirationSeconds=9000` - Token expires in 9000 seconds (2.5 hours)

---

#### 2. **application-test.properties**

**What it does:**
- Configuration for testing
- Uses H2 in-memory database instead of MySQL
- Disables Flyway migrations
- Different logging levels

**Location:** `src/test/resources/`

---

#### 3. **logback-spring.xml**

**What it does:**
- Logging configuration
- Defines log format and levels
- Configures console and file logging

**Location:** `src/main/resources/`

---

### 🗄️ Database Migration Files

#### 1. **V1__init.sql**

**What it does:**
- Creates all database tables
- Defines table structure and relationships
- Creates: roles, users, user_roles, managers, employees tables

**Location:** `src/main/resources/db/migration/`

**Note:** Only runs if Flyway is enabled

---

#### 2. **V2__seed_roles_users.sql**

**What it does:**
- Seeds initial data (roles and users)
- Inserts ADMIN and USER roles
- Inserts default admin and user accounts

**Location:** `src/main/resources/db/migration/`

**Note:** Only runs if Flyway is enabled. Currently, DataInitializer.java handles this automatically.

---

### 🧪 Test Files

#### 1. **AuthControllerTest.java**

**What it does:**
- Tests signup endpoint
- Tests login endpoint
- Tests validation errors
- Tests duplicate username/email

**Location:** `src/test/java/controller/`

---

#### 2. **EmployeeControllerTest.java**

**What it does:**
- Tests all employee endpoints
- Tests authorization (USER vs ADMIN)
- Tests CRUD operations

**Location:** `src/test/java/controller/`

---

#### 3. **ManagerControllerTest.java**

**What it does:**
- Tests all manager endpoints
- Tests ADMIN-only access
- Tests CRUD operations

**Location:** `src/test/java/controller/`

---

## 🚀 How to Run the Application

### Method 1: Using Command Line (Terminal/Console)

#### Step 1: Navigate to Project Directory

```bash
cd /path/to/SpringBootSecurityAuthenticationAndAuthorization
```

#### Step 2: Build the Project

```bash
mvn clean install
```

#### Step 3: Run the Application

```bash
mvn spring-boot:run
```

**Or using the wrapper:**

```bash
./mvnw spring-boot:run
```

**On Windows:**

```bash
mvnw.cmd spring-boot:run
```

#### Step 4: Verify Application is Running

- Check console for: `Started SpringBootSecurityAuthenticationAndAuthorizationApplication`
- Open browser: `http://localhost:8095`
- You should see an error (this is normal - means security is working!)

---

### Method 2: Using Spring Tool Suite (STS)

#### Step 1: Import Project

1. Open STS
2. File → Import → Existing Maven Projects
3. Select project folder
4. Click Finish

#### Step 2: Configure Database

1. Open `src/main/resources/application.properties`
2. Update MySQL password:
   ```properties
   spring.datasource.password=YOUR_PASSWORD
   ```

#### Step 3: Run Application

1. Right-click on `SpringBootSecurityAuthenticationAndAuthorizationApplication.java`
2. Run As → Spring Boot App
3. Or use the Run button (green play icon)

#### Step 4: Check Console

- Look for "Started" message
- Check for any errors
- Application runs on `http://localhost:8095`

---

### Method 3: Using IntelliJ IDEA

#### Step 1: Open Project

1. File → Open
2. Select project folder
3. Click OK

#### Step 2: Configure Database

1. Open `src/main/resources/application.properties`
2. Update MySQL password:
   ```properties
   spring.datasource.password=YOUR_PASSWORD
   ```

#### Step 3: Run Application

1. Find `SpringBootSecurityAuthenticationAndAuthorizationApplication.java`
2. Right-click → Run 'SpringBootSecurityAuthenticationAndAuthorizationApplication'
3. Or click the green play icon next to the main method

#### Step 4: Check Run Tab

- Look for "Started" message
- Check for any errors
- Application runs on `http://localhost:8095`

---

### Method 4: Using JAR File

#### Step 1: Build JAR

```bash
mvn clean package
```

This creates: `target/SpringBootSecurityAuthenticationAndAuthorization-0.0.1-SNAPSHOT.jar`

#### Step 2: Run JAR

```bash
java -jar target/SpringBootSecurityAuthenticationAndAuthorization-0.0.1-SNAPSHOT.jar
```

---

## 🧪 How to Run Tests

### Method 1: Command Line

#### Run All Tests

```bash
mvn test
```

#### Run Specific Test Class

```bash
mvn test -Dtest=AuthControllerTest
```

#### Run with Test Profile

```bash
mvn test -Dspring.profiles.active=test
```

---

### Method 2: Spring Tool Suite (STS)

1. Right-click on project
2. Run As → Maven Test
3. Or right-click on specific test class → Run As → JUnit Test

---

### Method 3: IntelliJ IDEA

1. Right-click on `src/test/java` folder
2. Run 'All Tests'
3. Or right-click on specific test class → Run

---

## 🔧 Common Commands

### Build Commands

```bash
# Clean and compile
mvn clean compile

# Clean, compile, and package
mvn clean package

# Skip tests during build
mvn clean package -DskipTests

# Install to local Maven repository
mvn clean install
```

### Run Commands

```bash
# Run application
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Run JAR file
java -jar target/SpringBootSecurityAuthenticationAndAuthorization-0.0.1-SNAPSHOT.jar
```

### Test Commands

```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=AuthControllerTest

# Run tests with coverage
mvn test jacoco:report
```

### Database Commands

```bash
# Create database (MySQL)
mysql -u root -p
CREATE DATABASE SpringSecurity CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# Check database connection
mysql -u root -p SpringSecurity
```

---

## 📊 Application Flow

### 1. Application Startup

1. Spring Boot starts
2. `DataInitializer` runs (creates roles and default users)
3. Database tables are created/updated (via Hibernate)
4. Security configuration is loaded
5. Application is ready on port 8095

### 2. User Registration Flow

1. Client sends POST to `/api/auth/signup`
2. `AuthController` receives request
3. `AuthService.signup()` validates and creates user
4. Password is hashed with BCrypt
5. User is saved to database
6. Returns 201 Created

### 3. User Login Flow

1. Client sends POST to `/api/auth/login`
2. `AuthController` receives request
3. `AuthService.authenticate()` validates credentials
4. `JwtUtils` generates JWT token
5. Token is returned to client

### 4. Protected API Flow

1. Client sends request with JWT token in Authorization header
2. `JwtAuthenticationFilter` intercepts request
3. Token is extracted and validated
4. User details are loaded from database
5. Authentication is set in Security Context
6. Controller method executes
7. Response is returned

---

## 🎯 Key Features

1. **JWT Authentication** - Stateless token-based authentication
2. **Role-Based Access Control** - USER and ADMIN roles
3. **Password Encryption** - BCrypt password hashing
4. **Input Validation** - Bean validation on DTOs
5. **Exception Handling** - Global exception handler
6. **Database Integration** - MySQL with JPA/Hibernate
7. **Auto Data Initialization** - Default users and roles created on startup

---

## 📝 Summary

This project is a complete Spring Boot Security application with:

- ✅ Authentication (Signup/Login)
- ✅ Authorization (Role-based)
- ✅ RESTful APIs
- ✅ Database Integration
- ✅ Testing Support
- ✅ Exception Handling
- ✅ Input Validation

**Next Steps:**
- Read `3_API_DOCUMENTATION.md` for API usage
- Read `4_TESTING_GUIDE.md` for testing instructions
- Read `5_DATABASE_MIGRATION_GUIDE.md` for database setup

---

**Happy Coding! 🎉**

