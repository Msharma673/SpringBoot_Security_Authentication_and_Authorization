# 🏗️ Complete Step-by-Step Project Building Guide

This guide will help you build this Spring Boot Security project from scratch, step by step.

## 📖 What is This Guide?

This guide explains **how to build a complete Spring Boot Security application** from the very beginning. Each step includes:
- **Definition**: What the step is
- **Purpose**: Why we do this step
- **Reason**: Why it's important for the project
- **Explanation**: Detailed description in simple words

---

## 📋 Prerequisites

**Definition**: Prerequisites are the tools and software you need to have installed on your computer before you can start building this project.

**Purpose**: These tools are required because:
- **Java 21**: This is the programming language we use. Java 21 is needed because Spring Boot 3.5.7 requires it. Without Java, you cannot compile or run the code.
- **Maven 3.6+**: Maven is a build tool that manages all the libraries (dependencies) our project needs. It downloads them automatically and compiles our code. Without Maven, we would have to manually download hundreds of files.
- **MySQL 8.0+**: MySQL is the database where we store all our data (users, employees, managers). We need it running because our application saves and retrieves data from it.
- **IDE**: An IDE (Integrated Development Environment) is a program that helps us write code easily. It provides features like code completion, error checking, and debugging. Without an IDE, coding would be very difficult.
- **Postman**: Postman is a tool to test our APIs (the endpoints our application provides). It's optional but very helpful to verify everything works correctly.

**Why These Are Important**: Without these tools, you cannot build, run, or test the application. They are like the foundation of a house - you need them before you can build anything.

Before starting, make sure you have:

1. **Java 21** installed (check with `java -version`)
   - **What it is**: The programming language runtime
   - **Why needed**: Spring Boot requires Java 21 to run
   - **How to check**: Open terminal and type `java -version`

2. **Maven 3.6+** installed (check with `mvn -version`)
   - **What it is**: Build and dependency management tool
   - **Why needed**: Downloads libraries and compiles the project
   - **How to check**: Open terminal and type `mvn -version`

3. **MySQL 8.0+** installed and running
   - **What it is**: Database management system
   - **Why needed**: Stores all application data (users, employees, etc.)
   - **How to check**: Try connecting with `mysql -u root -p`

4. **IDE** (Spring Tool Suite 4, IntelliJ IDEA, or Eclipse)
   - **What it is**: Code editor with advanced features
   - **Why needed**: Makes coding, debugging, and running easier
   - **Options**: STS (best for Spring), IntelliJ (very popular), Eclipse (free)

5. **Postman** (optional, for API testing)
   - **What it is**: Tool to test HTTP APIs
   - **Why needed**: Easy way to test if APIs work correctly
   - **Alternative**: You can use cURL command line tool instead

---

## 🎯 Step 1: Create Project Structure

**Definition**: Creating the project structure means setting up the basic folder structure and configuration files that Spring Boot needs to work.

**Purpose**: This step creates the foundation of our project. It sets up:
- The folder structure (where files will be placed)
- The build configuration (how to compile and run)
- Basic dependencies (libraries we need)

**Why We Do This First**: Without a proper project structure, we cannot write code. It's like building a house - you need the foundation and framework first before adding rooms.

**What Happens**: Spring Boot creates folders like `src/main/java` (for our code), `src/main/resources` (for configuration), and `pom.xml` (build configuration).

### 1.1 Create Maven Project

**Definition**: A Maven project is a Java project that uses Maven to manage dependencies and build the application.

**Purpose**: Maven automatically:
- Downloads all required libraries (dependencies)
- Compiles our Java code
- Packages everything into a JAR file
- Runs tests

**Why Maven**: Without Maven, we would need to manually download and manage hundreds of library files. Maven does this automatically, saving us time and preventing errors.

**Option A: Using Spring Initializr (Recommended)**

**What is Spring Initializr**: It's a website that generates a Spring Boot project template for you. Think of it as a project generator that creates all the basic files you need.

**Why Use It**: It's the easiest and fastest way to create a Spring Boot project. It sets up everything correctly from the start.

**Step-by-Step Explanation**:

1. **Go to https://start.spring.io/**
   - **What**: This is Spring's official project generator website
   - **Why**: It creates a ready-to-use project template
   - **Purpose**: Saves time by generating all necessary files

2. **Select Project: Maven**
   - **Definition**: Maven is the build tool we use
   - **Purpose**: Manages dependencies and builds the project
   - **Why Maven**: It's the standard tool for Java projects, widely used and well-supported

3. **Select Language: Java**
   - **Definition**: Java is the programming language
   - **Purpose**: We write our code in Java
   - **Why Java**: Spring Boot is built for Java, and Java is powerful and widely used

4. **Select Spring Boot: 3.5.7**
   - **Definition**: Spring Boot is the framework version
   - **Purpose**: Provides all the tools and features we need
   - **Why This Version**: 3.5.7 is stable and has all features we need (JWT, Security, JPA)

5. **Group: `com.SpringBootSecurity`**
   - **Definition**: This is the package name prefix (like a folder path)
   - **Purpose**: Organizes our code and prevents naming conflicts
   - **Why This Format**: Follows Java naming conventions (reverse domain name)

6. **Artifact: `SpringBootSecurityAuthenticationAndAuthorization`**
   - **Definition**: This is the project name
   - **Purpose**: Identifies our project uniquely
   - **Why This Name**: Describes what the project does (Security with Authentication and Authorization)

7. **Java: 21**
   - **Definition**: Java version 21
   - **Purpose**: Uses latest Java features
   - **Why 21**: Spring Boot 3.5.7 requires Java 21

8. **Add Dependencies**:
   - **Spring Web**: Creates REST APIs (endpoints)
   - **Spring Data JPA**: Connects to database easily
   - **Spring Security**: Provides security features (authentication, authorization)
   - **MySQL Driver**: Allows connection to MySQL database
   - **Lombok**: Reduces boilerplate code (getters, setters)
   - **Validation**: Validates user input
   - **Flyway Migration**: Manages database changes (optional)

9. **Generate and Download**
   - **What Happens**: Website creates a ZIP file with all project files
   - **Purpose**: You get a ready-to-use project structure
   - **Next Step**: Extract and open in your IDE

**Option B: Using IDE**

**Definition**: Using your IDE's built-in project creation wizard.

**Purpose**: Some IDEs have their own Spring Boot project generator.

**Why Use This**: If you prefer working entirely within your IDE without visiting a website.

**Steps**:
1. **Open IDE**: Launch Spring Tool Suite or IntelliJ
   - **Purpose**: Your coding environment
   
2. **File → New → Spring Starter Project**
   - **What**: Opens project creation wizard
   - **Purpose**: Guides you through project setup
   
3. **Fill Details**: Same as Option A
   - **Purpose**: Configure project settings
   
4. **Add Dependencies**: Select from list
   - **Purpose**: Choose required libraries

---

## 🎯 Step 2: Configure Project Files

**Definition**: Configuration files contain settings that tell Spring Boot how to behave (database connection, server port, etc.).

**Purpose**: These files control:
- Which database to connect to
- What port the server runs on
- Which libraries to use
- Security settings

**Why We Configure**: Without configuration, Spring Boot doesn't know where to find the database, what port to use, or how to handle security. Configuration is like giving directions to the application.

**What We Configure**:
1. **pom.xml**: Lists all libraries (dependencies) the project needs
2. **application.properties**: Contains all application settings

### 2.1 Update pom.xml

**Definition**: `pom.xml` (Project Object Model) is Maven's configuration file. It lists all libraries (dependencies) your project needs.

**Purpose**: This file tells Maven:
- Which libraries to download
- Which versions to use
- How to build the project

**Why We Update It**: The initial project doesn't include JWT libraries and some other tools we need. We must add them here so Maven can download them.

**What Happens**: When you add dependencies here, Maven automatically downloads them from the internet and makes them available to your code.

**Open `pom.xml` and add these dependencies:**

```xml
<!-- JWT Dependencies -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.5</version>
    <scope>runtime</scope>
</dependency>

<!-- Flyway for Database Migrations -->
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>

<!-- H2 for Testing -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```

### 2.2 Create application.properties

**Definition**: `application.properties` is a configuration file that contains all the settings for your Spring Boot application.

**Purpose**: This file tells Spring Boot:
- Which database to connect to and how
- What port to run the server on
- JWT secret key for token generation
- Logging levels
- Database connection details

**Why We Create This**: Spring Boot needs to know these settings to work correctly. Without this file, the application won't know where the database is or what port to use.

**What Each Setting Does**:
- **spring.application.name**: Name of your application (for identification)
- **server.port**: Port number where the server runs (8095 means you access it at http://localhost:8095)
- **spring.profiles.active**: Which environment profile to use (dev = development)
- **spring.datasource.url**: Database connection string (tells where MySQL is)
- **spring.datasource.username/password**: Database login credentials
- **app.jwt.secret**: Secret key to sign JWT tokens (must be at least 32 characters for security)
- **app.jwt.expirationSeconds**: How long JWT tokens are valid (9000 seconds = 2.5 hours)

**Why These Settings Matter**:
- Wrong database URL = application can't connect to database
- Wrong port = you can't access the application
- Weak JWT secret = tokens can be easily hacked
- Wrong credentials = database connection fails

**Create `src/main/resources/application.properties`:**

```properties
spring.application.name=SpringBootSecurityAuthenticationAndAuthorization
server.port=8095

# Active Profile
spring.profiles.active=dev

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/SpringSecurity?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD_HERE
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.generate-ddl=true

# Flyway (Optional - set to false for now)
spring.flyway.enabled=false

# JWT Configuration
app.jwt.secret=MyVeryStrongJWTSecretKey@2024!$%ABCXYZ123
app.jwt.expirationSeconds=9000

# Logging
logging.level.root=INFO
logging.level.com.SpringBootSecurity=DEBUG
```

---

## 🎯 Step 3: Create Database

**Definition**: A database is a storage system where we keep all our data (users, employees, managers, etc.). Think of it as a digital filing cabinet.

**Purpose**: We need a database because:
- Our application needs to store user accounts
- We need to save employee and manager information
- We need to remember user roles and permissions
- All data must persist (stay saved) even after the application restarts

**Why We Create It Now**: Before we can write code that saves data, we need a place to save it. The database must exist before the application tries to connect to it.

**What Happens**: We create an empty database. Later, Spring Boot will automatically create tables inside it based on our entity classes.

### 3.1 Create MySQL Database

**Definition**: MySQL is a database management system. We create a database (like creating a new folder) where all our application data will be stored.

**Purpose**: This database will hold:
- User accounts and passwords
- User roles (ADMIN, USER)
- Employee records
- Manager records
- Relationships between users and roles

**Why MySQL**: MySQL is:
- Free and open-source
- Very popular and well-supported
- Works great with Spring Boot
- Handles large amounts of data efficiently

**Step-by-Step Explanation**:

1. **Open MySQL command line or MySQL Workbench**
   - **What**: MySQL command line is a text interface to MySQL. MySQL Workbench is a graphical tool.
   - **Purpose**: We need to connect to MySQL to create the database
   - **Why Both Options**: Some people prefer command line, others prefer graphical tools

2. **Run CREATE DATABASE command**
   - **What**: This SQL command creates a new database named "SpringSecurity"
   - **Purpose**: Creates an empty container where our tables will be stored
   - **CHARACTER SET utf8mb4**: Supports all characters including emojis (important for international users)
   - **COLLATE utf8mb4_unicode_ci**: Sets sorting rules (case-insensitive, supports all languages)
   - **Why These Settings**: Ensures the database can handle any text data correctly

3. **Verify Database Created**
   - **SHOW DATABASES**: Lists all databases (you should see "SpringSecurity" in the list)
   - **USE SpringSecurity**: Switches to the SpringSecurity database (makes it active)
   - **Purpose**: Confirms the database was created successfully
   - **Why Verify**: If the database wasn't created, the application will fail to start

---

## 🎯 Step 4: Create Model Classes (Entities)

**Definition**: Model classes (also called Entities) are Java classes that represent database tables. Each class becomes a table in the database.

**Purpose**: These classes:
- Define the structure of our data (what fields each record has)
- Map Java objects to database tables
- Allow us to save and retrieve data easily

**Why We Create These**: Without model classes, we cannot save data to the database. They are like blueprints - they tell Spring Boot what tables to create and what columns each table should have.

**What Happens**: When you create these classes with `@Entity` annotation, Hibernate (the ORM tool) automatically creates corresponding tables in the database when the application starts.

**The Four Models We Create**:
1. **Role**: Represents user roles (ADMIN, USER)
2. **User**: Represents user accounts
3. **Employee**: Represents employee records
4. **Manager**: Represents manager records

### 4.1 Create Role Entity

**Definition**: The Role entity represents different roles that users can have in the system (like ADMIN or USER).

**Purpose**: This class:
- Defines what a role is (it has an ID and a name)
- Creates the `roles` table in the database
- Allows us to assign roles to users

**Why We Need This**: We need roles to control what users can do. For example, ADMIN users can delete employees, but regular USER users cannot. Roles are the foundation of our authorization system.

**What Each Part Does**:
- `@Entity`: Tells Spring this class represents a database table
- `@Table(name = "roles")`: Names the table "roles" in the database
- `@Id`: Marks this field as the primary key (unique identifier)
- `@GeneratedValue`: Makes the ID auto-increment (database assigns numbers automatically)
- `@ManyToMany`: Defines relationship - one role can belong to many users, one user can have many roles

**File:** `src/main/java/model/Role.java`

```java
package model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String name;
    
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
```

### 4.2 Create User Entity

**File:** `src/main/java/model/User.java`

```java
package model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    private Boolean enabled = true;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
```

### 4.3 Create Manager Entity

**File:** `src/main/java/model/Manager.java`

```java
package model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "managers")
@Data
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String designation;
    private Integer experience;
    private String city;
    
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Employee> employees;
}
```

### 4.4 Create Employee Entity

**File:** `src/main/java/model/Employee.java`

```java
package model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employees")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String phone;
    private String position;
    
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;
}
```

---

## 🎯 Step 5: Create Repository Interfaces

### 5.1 Create RoleRepository

**File:** `src/main/java/repository/RoleRepository.java`

```java
package repository;

import model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
```

### 5.2 Create UserRepository

**File:** `src/main/java/repository/UserRepository.java`

```java
package repository;

import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
```

### 5.3 Create ManagerRepository

**File:** `src/main/java/repository/ManagerRepository.java`

```java
package repository;

import model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
```

### 5.4 Create EmployeeRepository

**File:** `src/main/java/repository/EmployeeRepository.java`

```java
package repository;

import model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
```

---

## 🎯 Step 6: Create DTOs (Data Transfer Objects)

### 6.1 Create Auth DTOs

**File:** `src/main/java/dto/auth/SignupRequest.java`

```java
package dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
             message = "Password must be at least 8 characters with uppercase, lowercase, digit, and special character")
    private String password;
    
    private String role = "USER";
}
```

**File:** `src/main/java/dto/auth/LoginRequest.java`

```java
package dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username or email is required")
    private String usernameOrEmail;
    
    @NotBlank(message = "Password is required")
    private String password;
}
```

**File:** `src/main/java/dto/auth/JwtResponse.java`

```java
package dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String tokenType = "Bearer";
    private Long expiresInSeconds;
}
```

### 6.2 Create Employee DTOs

**File:** `src/main/java/dto/employee/EmployeeDto.java`

```java
package dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String position;
    private Long managerId;
}
```

**File:** `src/main/java/dto/employee/EmployeeCreateRequest.java`

```java
package dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeCreateRequest {
    private String firstName;
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    private String phone;
    private String position;
    private Long managerId;
}
```

### 6.3 Create Manager DTOs

**File:** `src/main/java/dto/manager/ManagerDto.java`

```java
package dto.manager;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManagerDto {
    private Long id;
    private String name;
    private String designation;
    private Integer experience;
    private String city;
}
```

**File:** `src/main/java/dto/manager/ManagerCreateRequest.java`

```java
package dto.manager;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ManagerCreateRequest {
    @NotBlank(message = "Name is required")
    private String name;
    
    private String designation;
    private Integer experience;
    private String city;
}
```

---

## 🎯 Step 7: Create Security Components

### 7.1 Create JwtUtils

**File:** `src/main/java/security/JwtUtils.java`

```java
package security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    
    @Value("${app.jwt.expirationSeconds}")
    private Long jwtExpirationMs;
    
    private SecretKey getSigningKey() {
        if (jwtSecret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters");
        }
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
    
    public String generateToken(String username, List<GrantedAuthority> authorities) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs * 1000);
        
        List<String> roles = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
        
        return Jwts.builder()
            .subject(username)
            .claim("roles", roles)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(getSigningKey())
            .compact();
    }
    
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("JWT token validation error: {}", e.getMessage());
        }
        return false;
    }
}
```

### 7.2 Create CustomUserDetailsService

**File:** `src/main/java/security/CustomUserDetailsService.java`

```java
package security;

import model.User;
import repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(usernameOrEmail)
            .orElseGet(() -> userRepository.findByEmail(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail)));
        
        if (!user.getEnabled()) {
            throw new UsernameNotFoundException("User account is disabled: " + usernameOrEmail);
        }
        
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities(getAuthorities(user))
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(!user.getEnabled())
            .build();
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
            .collect(Collectors.toList());
    }
}
```

### 7.3 Create JwtAuthenticationFilter

**File:** `src/main/java/security/JwtAuthenticationFilter.java`

```java
package security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;
    
    public JwtAuthenticationFilter(JwtUtils jwtUtils, CustomUserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateToken(jwt)) {
                String username = jwtUtils.getUsernameFromToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
```

### 7.4 Create SecurityConfig

**File:** `src/main/java/config/SecurityConfig.java`

```java
package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import security.CustomUserDetailsService;
import security.JwtAuthenticationFilter;
import security.JwtUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;
    
    public SecurityConfig(JwtUtils jwtUtils, CustomUserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }
    
    @Bean
    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationFilter(jwtUtils, userDetailsService);
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/api/auth/**").permitAll();
                auth.anyRequest().authenticated();
            })
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

---

## 🎯 Step 8: Create Service Layer

### 8.1 Create AuthService Interface

**File:** `src/main/java/service/AuthService.java`

```java
package service;

import dto.auth.JwtResponse;
import dto.auth.LoginRequest;
import dto.auth.SignupRequest;

public interface AuthService {
    void signup(SignupRequest request, String requester);
    JwtResponse authenticate(LoginRequest request);
}
```

### 8.2 Create AuthServiceImpl

**File:** `src/main/java/service/AuthServiceImpl.java`

```java
package service;

import dto.auth.JwtResponse;
import dto.auth.LoginRequest;
import dto.auth.SignupRequest;
import model.Role;
import model.User;
import repository.RoleRepository;
import repository.UserRepository;
import security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                          JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }
    
    @Override
    public void signup(SignupRequest request, String requester) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        String roleName = request.getRole() != null ? request.getRole() : "USER";
        if ("ADMIN".equals(roleName) && (requester == null || !requester.equals("admin"))) {
            throw new RuntimeException("Only existing admins can create ADMIN users");
        }
        
        Role role = roleRepository.findByName(roleName)
            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        
        userRepository.save(user);
    }
    
    @Override
    public JwtResponse authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
        );
        
        String username = authentication.getName();
        String token = jwtUtils.generateToken(username, 
            authentication.getAuthorities().stream()
                .map(a -> (GrantedAuthority) a)
                .toList());
        
        return new JwtResponse(token, "Bearer", 9000L);
    }
}
```

### 8.3 Create ManagerService

**File:** `src/main/java/service/ManagerService.java`

```java
package service;

import dto.manager.ManagerCreateRequest;
import dto.manager.ManagerDto;
import java.util.List;

public interface ManagerService {
    ManagerDto create(ManagerCreateRequest request);
    ManagerDto getById(Long id);
    List<ManagerDto> getAll();
    ManagerDto update(Long id, ManagerCreateRequest request);
    void delete(Long id);
}
```

### 8.4 Create ManagerServiceImpl

**File:** `src/main/java/service/ManagerServiceImpl.java`

```java
package service;

import dto.manager.ManagerCreateRequest;
import dto.manager.ManagerDto;
import exception.ResourceNotFoundException;
import model.Manager;
import repository.ManagerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;
    
    public ManagerServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }
    
    @Override
    public ManagerDto create(ManagerCreateRequest request) {
        Manager manager = new Manager();
        manager.setName(request.getName());
        manager.setDesignation(request.getDesignation());
        manager.setExperience(request.getExperience());
        manager.setCity(request.getCity());
        Manager saved = managerRepository.save(manager);
        return toDto(saved);
    }
    
    @Override
    public ManagerDto getById(Long id) {
        Manager manager = managerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
        return toDto(manager);
    }
    
    @Override
    public List<ManagerDto> getAll() {
        return managerRepository.findAll().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    
    @Override
    public ManagerDto update(Long id, ManagerCreateRequest request) {
        Manager manager = managerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
        manager.setName(request.getName());
        manager.setDesignation(request.getDesignation());
        manager.setExperience(request.getExperience());
        manager.setCity(request.getCity());
        Manager saved = managerRepository.save(manager);
        return toDto(saved);
    }
    
    @Override
    public void delete(Long id) {
        if (!managerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Manager", "id", id);
        }
        managerRepository.deleteById(id);
    }
    
    private ManagerDto toDto(Manager manager) {
        return new ManagerDto(
            manager.getId(),
            manager.getName(),
            manager.getDesignation(),
            manager.getExperience(),
            manager.getCity()
        );
    }
}
```

---

## 🎯 Step 9: Create Controllers

### 9.1 Create AuthController

**File:** `src/main/java/controller/AuthController.java`

```java
package controller;

import dto.auth.*;
import service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request, null);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
```

### 9.2 Create EmployeeController

**File:** `src/main/java/controller/EmployeeController.java`

```java
package controller;

import dto.employee.*;
import exception.ResourceNotFoundException;
import model.Employee;
import model.Manager;
import repository.EmployeeRepository;
import repository.ManagerRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;
    
    public EmployeeController(EmployeeRepository employeeRepository, 
                             ManagerRepository managerRepository) {
        this.employeeRepository = employeeRepository;
        this.managerRepository = managerRepository;
    }
    
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll() {
        List<EmployeeDto> list = employeeRepository.findAll().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
    
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeDto> create(@Valid @RequestBody EmployeeCreateRequest req) {
        Employee e = new Employee();
        e.setFirstName(req.getFirstName());
        e.setLastName(req.getLastName());
        e.setEmail(req.getEmail());
        e.setPhone(req.getPhone());
        e.setPosition(req.getPosition());
        if (req.getManagerId() != null) {
            Manager m = managerRepository.findById(req.getManagerId()).orElse(null);
            e.setManager(m);
        }
        Employee saved = employeeRepository.save(e);
        return ResponseEntity.ok(toDto(saved));
    }
    
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        Employee e = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        return ResponseEntity.ok(toDto(e));
    }
    
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable Long id, 
                                               @Valid @RequestBody EmployeeCreateRequest req) {
        Employee e = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        e.setFirstName(req.getFirstName());
        e.setLastName(req.getLastName());
        e.setEmail(req.getEmail());
        e.setPhone(req.getPhone());
        e.setPosition(req.getPosition());
        if (req.getManagerId() != null) {
            Manager m = managerRepository.findById(req.getManagerId()).orElse(null);
            e.setManager(m);
        }
        Employee saved = employeeRepository.save(e);
        return ResponseEntity.ok(toDto(saved));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee", "id", id);
        }
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    private EmployeeDto toDto(Employee e) {
        Long mid = e.getManager() != null ? e.getManager().getId() : null;
        return new EmployeeDto(e.getId(), e.getFirstName(), e.getLastName(), 
                              e.getEmail(), e.getPhone(), e.getPosition(), mid);
    }
}
```

### 9.3 Create ManagerController

**File:** `src/main/java/controller/ManagerController.java`

```java
package controller;

import dto.manager.*;
import service.ManagerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {
    private final ManagerService managerService;
    
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ManagerDto>> getAll() {
        return ResponseEntity.ok(managerService.getAll());
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ManagerDto> create(@Valid @RequestBody ManagerCreateRequest request) {
        return ResponseEntity.ok(managerService.create(request));
    }
    
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ManagerDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.getById(id));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ManagerDto> update(@PathVariable Long id, 
                                            @Valid @RequestBody ManagerCreateRequest request) {
        return ResponseEntity.ok(managerService.update(id, request));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        managerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 🎯 Step 10: Create Exception Handlers

### 10.1 Create ResourceNotFoundException

**File:** `src/main/java/exception/ResourceNotFoundException.java`

```java
package exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
```

### 10.2 Create GlobalExceptionHandler

**File:** `src/main/java/exception/GlobalExceptionHandler.java`

```java
package exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
```

---

## 🎯 Step 11: Create Data Initializer

**File:** `src/main/java/config/DataInitializer.java`

```java
package config;

import model.Role;
import model.User;
import repository.RoleRepository;
import repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public DataInitializer(RoleRepository roleRepository,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeUsers();
    }
    
    private void initializeRoles() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
            
            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }
    }
    
    private void initializeUsers() {
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new IllegalStateException("ADMIN role not found"));
            Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("USER role not found"));
            
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEnabled(true);
                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(adminRole);
                admin.setRoles(adminRoles);
                userRepository.save(admin);
            }
            
            if (!userRepository.existsByUsername("user")) {
                User user = new User();
                user.setUsername("user");
                user.setEmail("user@example.com");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setEnabled(true);
                Set<Role> userRoles = new HashSet<>();
                userRoles.add(userRole);
                user.setRoles(userRoles);
                userRepository.save(user);
            }
        }
    }
}
```

---

## 🎯 Step 12: Create Main Application Class

**File:** `src/main/java/com/SpringBootSecurity/SpringBootSecurityAuthenticationAndAuthorization/SpringBootSecurityAuthenticationAndAuthorizationApplication.java`

```java
package com.SpringBootSecurity.SpringBootSecurityAuthenticationAndAuthorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"config", "security", "controller", "service", "exception", 
                               "com.SpringBootSecurity.SpringBootSecurityAuthenticationAndAuthorization"})
@EntityScan(basePackages = {"model", "com.SpringBootSecurity.SpringBootSecurityAuthenticationAndAuthorization.model"})
@EnableJpaRepositories(basePackages = {"repository", 
                                       "com.SpringBootSecurity.SpringBootSecurityAuthenticationAndAuthorization.repository"})
public class SpringBootSecurityAuthenticationAndAuthorizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityAuthenticationAndAuthorizationApplication.class, args);
    }
}
```

---

## 🎯 Step 13: Build and Run

### 13.1 Build the Project

```bash
mvn clean install
```

### 13.2 Run the Application

**Option A: Using Maven**
```bash
mvn spring-boot:run
```

**Option B: Using IDE**
1. Right-click on `SpringBootSecurityAuthenticationAndAuthorizationApplication.java`
2. Select "Run As" → "Java Application"

**Option C: Using JAR**
```bash
java -jar target/SpringBootSecurityAuthenticationAndAuthorization-0.0.1-SNAPSHOT.jar
```

### 13.3 Verify Application is Running

1. Check console logs for "Started SpringBootSecurityAuthenticationAndAuthorizationApplication"
2. Open browser: `http://localhost:8095`
3. You should see a security error (which is expected - means app is running!)

---

## 🎯 Step 14: Test the Application

### 14.1 Test Signup

```bash
curl -X POST http://localhost:8095/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "Test123!@#",
    "role": "USER"
  }'
```

### 14.2 Test Login

```bash
curl -X POST http://localhost:8095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "testuser",
    "password": "Test123!@#"
  }'
```

Copy the token from the response!

### 14.3 Test Protected Endpoint

```bash
curl -X GET http://localhost:8095/api/employees \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

---

## ✅ Summary

You have successfully built a complete Spring Boot Security application with:

1. ✅ JWT-based authentication
2. ✅ Role-based authorization (USER, ADMIN)
3. ✅ RESTful APIs for Employees and Managers
4. ✅ Database integration with MySQL
5. ✅ Data initialization on startup
6. ✅ Exception handling
7. ✅ Input validation

**Next Steps:**
- Read `2_PROJECT_OVERVIEW.md` to understand the project structure
- Read `3_API_DOCUMENTATION.md` for complete API usage
- Read `4_TESTING_GUIDE.md` for testing instructions
- Read `5_DATABASE_MIGRATION_GUIDE.md` for database setup

---

**Happy Coding! 🎉**

