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
   
   **Definition**: Dependencies are external libraries (pre-written code) that our project needs to function. Think of them as tools in a toolbox - each one does a specific job.
   
   **Purpose**: We add dependencies because we don't want to write everything from scratch. These libraries provide ready-made solutions for common tasks like connecting to databases, handling security, and creating web APIs.
   
   **Why We Need Each Dependency**:
   
   - **Spring Web**: 
     - **What it is**: A library that helps create REST APIs (web endpoints)
     - **Purpose**: Allows our application to receive HTTP requests and send HTTP responses
     - **Why needed**: Without this, we cannot create endpoints like `/api/auth/login` or `/api/employees`
     - **What it provides**: `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping` annotations
     - **Real-world analogy**: Like a waiter in a restaurant - takes orders (requests) and brings food (responses)
   
   - **Spring Data JPA**: 
     - **What it is**: A library that simplifies database operations
     - **Purpose**: Allows us to interact with the database using Java objects instead of writing SQL queries
     - **Why needed**: Without this, we would have to write complex SQL queries for every database operation
     - **What it provides**: Repository interfaces, automatic query generation, entity management
     - **Real-world analogy**: Like a translator between Java code and SQL - you speak Java, it translates to SQL
   
   - **Spring Security**: 
     - **What it is**: A comprehensive security framework for Spring applications
     - **Purpose**: Provides authentication (who you are) and authorization (what you can do)
     - **Why needed**: Without this, anyone could access any endpoint - no security at all
     - **What it provides**: Password encoding, authentication filters, role-based access control
     - **Real-world analogy**: Like a security guard at a building - checks IDs (authentication) and controls access to different floors (authorization)
   
   - **MySQL Driver**: 
     - **What it is**: A connector that allows Java to communicate with MySQL database
     - **Purpose**: Translates Java database calls into MySQL-specific commands
     - **Why needed**: Java doesn't natively understand MySQL - we need this driver as a translator
     - **What it provides**: Database connection, SQL execution, result set handling
     - **Real-world analogy**: Like a USB cable - connects your computer (Java) to your phone (MySQL database)
   
   - **Lombok**: 
     - **What it is**: A library that automatically generates boilerplate code (getters, setters, constructors)
     - **Purpose**: Reduces code you need to write manually
     - **Why needed**: Without Lombok, you'd write 10+ lines for getters/setters for each field - with Lombok, one annotation does it all
     - **What it provides**: `@Data`, `@Getter`, `@Setter`, `@AllArgsConstructor`, `@NoArgsConstructor`
     - **Real-world analogy**: Like a code generator - you describe what you want, it writes the code for you
   
   - **Validation**: 
     - **What it is**: A library that validates user input automatically
     - **Purpose**: Ensures data meets requirements before processing (e.g., email format, password strength)
     - **Why needed**: Prevents invalid data from entering the system, catches errors early
     - **What it provides**: `@NotBlank`, `@Email`, `@Pattern`, `@Size` annotations
     - **Real-world analogy**: Like a bouncer at a club - checks if you meet requirements before letting you in
   
   - **Flyway Migration**: 
     - **What it is**: A tool that manages database schema changes over time
     - **Purpose**: Tracks and applies database changes in a controlled, versioned way
     - **Why needed**: As your application evolves, database structure changes - Flyway ensures these changes are applied correctly
     - **What it provides**: Version-controlled SQL scripts, migration tracking, rollback support
     - **Real-world analogy**: Like a version control system (Git) but for database structure

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

**Detailed Explanation of Each Dependency:**

```xml
<!-- JWT Dependencies -->
<!-- 
    Definition: JWT (JSON Web Token) is a secure way to transmit information between parties.
    Purpose: We use JWT to authenticate users - when they login, we give them a token that proves they're logged in.
    Why Needed: Without JWT, we'd need to store login sessions on the server, which doesn't work well for distributed systems.
    How It Works: User logs in → Server creates a token with user info → User sends token with every request → Server validates token.
    Real-world Analogy: Like a wristband at a concert - you get it when you enter (login), and you show it to access different areas (endpoints).
-->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <!-- Definition: Group ID identifies the organization that created this library (io.jsonwebtoken) -->
    <artifactId>jjwt-api</artifactId>
    <!-- Definition: Artifact ID is the name of this specific library (jjwt-api = JWT API) -->
    <!-- Purpose: This is the main API for creating and parsing JWT tokens -->
    <!-- Why Needed: Provides the classes and methods to generate tokens (Jwts.builder()) and parse them -->
    <!-- What It Provides: Jwts class, Claims interface, JwtParser interface -->
    <version>0.12.5</version>
    <!-- Definition: Version number - specifies which version of the library to use -->
    <!-- Purpose: Ensures everyone uses the same version, preventing compatibility issues -->
    <!-- Why This Version: 0.12.5 is stable and compatible with Spring Boot 3.5.7 -->
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <!-- Definition: Implementation of the JWT API -->
    <!-- Purpose: Contains the actual code that implements the JWT API interfaces -->
    <!-- Why Needed: The API (jjwt-api) defines what methods exist, but jjwt-impl provides the actual working code -->
    <!-- Real-world Analogy: API is like a car's manual (tells you what buttons do), impl is the actual engine (makes it work) -->
    <version>0.12.5</version>
    <scope>runtime</scope>
    <!-- Definition: Scope tells Maven when this library is needed -->
    <!-- Purpose: "runtime" means it's only needed when the application runs, not during compilation -->
    <!-- Why Runtime: We don't need the implementation code when compiling, only when running -->
    <!-- What This Means: Maven won't include it in compile-time classpath, only in runtime classpath -->
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <!-- Definition: Jackson integration for JWT -->
    <!-- Purpose: Allows JWT to work with JSON serialization/deserialization using Jackson library -->
    <!-- Why Needed: JWT tokens contain JSON data - this library helps convert between Java objects and JSON in tokens -->
    <!-- What It Provides: JSON parsing for JWT claims (the data inside tokens) -->
    <version>0.12.5</version>
    <scope>runtime</scope>
    <!-- Same as above - only needed at runtime -->
</dependency>

<!-- Flyway for Database Migrations -->
<!-- 
    Definition: Flyway is a database migration tool that manages version-controlled database changes.
    Purpose: Keeps track of all database schema changes (table creation, column additions, etc.) in version-controlled SQL files.
    Why Needed: As your application evolves, database structure changes. Flyway ensures these changes are applied in order and tracked.
    How It Works: You write SQL migration files (V1__create_tables.sql, V2__add_column.sql) → Flyway applies them in order → Tracks what's been applied.
    Real-world Analogy: Like a construction blueprint - each version (V1, V2, V3) represents a stage of building, and Flyway ensures each stage is completed before moving to the next.
-->
<dependency>
    <groupId>org.flywaydb</groupId>
    <!-- Definition: Organization that created Flyway (Flyway Database) -->
    <artifactId>flyway-core</artifactId>
    <!-- Definition: Core Flyway library -->
    <!-- Purpose: Provides the main Flyway functionality for managing migrations -->
    <!-- Why Needed: Without this, you'd have to manually run SQL scripts and track what's been applied -->
    <!-- What It Provides: Migration detection, version tracking, automatic SQL execution -->
    <!-- Note: Version not specified - uses version from Spring Boot parent POM (managed dependency) -->
</dependency>

<!-- H2 for Testing -->
<!-- 
    Definition: H2 is an in-memory database (database that exists only in RAM, not on disk).
    Purpose: Used for testing - creates a temporary database that's destroyed after tests complete.
    Why Needed: We don't want tests to use the real MySQL database - that would be slow and could corrupt real data.
    How It Works: Tests start → H2 database created in memory → Tests run → Tests end → Database disappears.
    Real-world Analogy: Like a whiteboard - you write on it during a meeting (tests), then erase it when done - no permanent changes.
    Advantages: Fast (no disk I/O), isolated (doesn't affect real data), no setup needed (no MySQL installation required for tests).
-->
<dependency>
    <groupId>com.h2database</groupId>
    <!-- Definition: Organization that created H2 database -->
    <artifactId>h2</artifactId>
    <!-- Definition: H2 database library -->
    <!-- Purpose: Provides in-memory database for testing -->
    <!-- Why Needed: Tests need a database, but we don't want to use MySQL (slow, requires setup, could affect real data) -->
    <!-- What It Provides: In-memory database, JDBC driver, SQL support -->
    <scope>test</scope>
    <!-- Definition: "test" scope means this library is ONLY available during testing -->
    <!-- Purpose: Ensures H2 is not included in the production application -->
    <!-- Why Test Scope: Production uses MySQL, not H2 - we don't want H2 in the final JAR file -->
    <!-- What This Means: Maven only includes this library when running tests (mvn test), not in production build -->
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

**What Each Setting Does** (Detailed Explanation):

- **spring.application.name**: 
  - **Definition**: A unique identifier/name for your Spring Boot application
  - **Purpose**: Helps identify this application in logs, monitoring tools, and service discovery
  - **Why Needed**: When you have multiple Spring Boot applications running, this name helps distinguish them
  - **What It Does**: Sets the application name that appears in logs and management endpoints
  - **Example**: If you see "SpringBootSecurityAuthenticationAndAuthorization" in logs, you know which app it's from
  - **Real-world Analogy**: Like a name tag - helps identify which person (application) you're talking about

- **server.port**: 
  - **Definition**: The port number where the web server will listen for incoming HTTP requests
  - **Purpose**: Defines where clients (browsers, Postman, etc.) can reach your application
  - **Why Needed**: Without this, Spring Boot uses default port 8080 - we set 8095 to avoid conflicts
  - **What It Does**: Tells embedded Tomcat server to listen on port 8095
  - **How to Access**: After starting, access at `http://localhost:8095`
  - **Why 8095**: Common practice to use non-standard ports (8095) to avoid conflicts with other applications
  - **Real-world Analogy**: Like a house address - tells people where to find your application

- **spring.profiles.active**: 
  - **Definition**: Specifies which environment profile is currently active
  - **Purpose**: Allows different configurations for different environments (dev, test, production)
  - **Why Needed**: Development settings differ from production (e.g., different databases, logging levels)
  - **What It Does**: Activates the "dev" profile, which can have its own `application-dev.properties` file
  - **How It Works**: Spring Boot loads `application.properties` + `application-dev.properties` (if exists)
  - **Why "dev"**: Indicates this is development environment - you might have "prod" for production
  - **Real-world Analogy**: Like switching between work mode and home mode on your phone - different settings for different contexts

- **spring.datasource.url**: 
  - **Definition**: The connection string that tells Spring Boot how to connect to the MySQL database
  - **Purpose**: Specifies database location, name, and connection parameters
  - **Why Needed**: Spring Boot needs to know WHERE the database is and HOW to connect to it
  - **What It Contains**: 
    - `jdbc:mysql://` - Protocol (how to connect - JDBC for MySQL)
    - `localhost:3306` - Database server location (localhost = same machine, 3306 = MySQL default port)
    - `SpringSecurity` - Database name
    - `useSSL=false` - Don't use SSL (for local development)
    - `allowPublicKeyRetrieval=true` - Allow retrieving public key (MySQL 8+ requirement)
    - `serverTimezone=UTC` - Set timezone to UTC (prevents timezone issues)
    - `createDatabaseIfNotExist=true` - Automatically create database if it doesn't exist
  - **Why Each Parameter**: 
    - `useSSL=false`: Local development doesn't need encryption (production should use SSL)
    - `allowPublicKeyRetrieval=true`: MySQL 8+ requires this for authentication
    - `serverTimezone=UTC`: Prevents date/time conversion errors
    - `createDatabaseIfNotExist=true`: Convenience - creates database automatically
  - **Real-world Analogy**: Like a GPS address with directions - tells Spring Boot exactly how to reach the database

- **spring.datasource.username**: 
  - **Definition**: The MySQL username for database authentication
  - **Purpose**: Identifies which MySQL user account to use when connecting
  - **Why Needed**: MySQL requires authentication - you need a username and password
  - **What It Does**: Tells Spring Boot to connect as "root" user (MySQL administrator)
  - **Why "root"**: Root has full permissions - good for development, but use limited user in production
  - **Security Note**: In production, create a dedicated database user with limited permissions
  - **Real-world Analogy**: Like your login username for a website

- **spring.datasource.password**: 
  - **Definition**: The MySQL password for database authentication
  - **Purpose**: Proves you have permission to access the database
  - **Why Needed**: MySQL requires password authentication for security
  - **What It Does**: Provides the password that matches the username
  - **Security Warning**: Never commit real passwords to version control - use environment variables in production
  - **Real-world Analogy**: Like your password for logging into a website

- **spring.datasource.driver-class-name**: 
  - **Definition**: The Java class name of the MySQL JDBC driver
  - **Purpose**: Tells Spring Boot which driver to use for MySQL connections
  - **Why Needed**: Different databases need different drivers - this specifies MySQL driver
  - **What It Does**: Loads the MySQL JDBC driver class (`com.mysql.cj.jdbc.Driver`)
  - **Why This Class**: This is the official MySQL Connector/J driver class
  - **Real-world Analogy**: Like specifying which type of USB cable to use (USB-C vs USB-A)

- **spring.jpa.hibernate.ddl-auto=update**: 
  - **Definition**: Tells Hibernate (JPA implementation) how to handle database schema
  - **Purpose**: Automatically creates/updates database tables based on entity classes
  - **Why Needed**: Without this, you'd have to manually create tables - this does it automatically
  - **What "update" Means**: 
    - Creates tables if they don't exist
    - Updates tables if entity classes change (adds new columns, but doesn't delete existing ones)
    - Does NOT drop tables or data (safer than "create" or "create-drop")
  - **Other Options**: 
    - `none`: Don't auto-manage schema (use Flyway or manual SQL)
    - `create`: Drop and recreate tables on startup (DESTROYS DATA!)
    - `create-drop`: Create on startup, drop on shutdown (DESTROYS DATA!)
    - `validate`: Only validate schema matches entities (doesn't change anything)
  - **Why "update"**: Safe for development - preserves data while allowing schema changes
  - **Real-world Analogy**: Like auto-sync - keeps your database structure in sync with your code automatically

- **spring.jpa.show-sql=true**: 
  - **Definition**: Tells Hibernate to print SQL queries to the console
  - **Purpose**: Helps you see what SQL queries Hibernate is executing
  - **Why Needed**: Useful for debugging - you can see exactly what database operations are happening
  - **What It Does**: Prints every SQL statement to console/logs
  - **Why Enable**: Helps understand what Hibernate is doing, useful for learning and debugging
  - **Production Note**: Usually set to `false` in production (too much logging)
  - **Real-world Analogy**: Like a transcript - shows you exactly what was said (SQL queries executed)

- **spring.jpa.properties.hibernate.format_sql=true**: 
  - **Definition**: Formats SQL queries to be more readable
  - **Purpose**: Makes SQL queries easier to read in logs
  - **Why Needed**: Without this, SQL is one long line - hard to read
  - **What It Does**: Adds line breaks and indentation to SQL queries
  - **Why Enable**: Makes debugging easier - formatted SQL is much more readable
  - **Real-world Analogy**: Like formatting code - makes it easier to understand

- **spring.jpa.properties.hibernate.dialect**: 
  - **Definition**: Tells Hibernate which database dialect (language) to use
  - **Purpose**: Different databases have slightly different SQL syntax - dialect handles these differences
  - **Why Needed**: MySQL has some SQL features that PostgreSQL doesn't (and vice versa) - dialect translates
  - **What It Does**: Uses MySQL-specific SQL syntax and features
  - **Why MySQLDialect**: We're using MySQL, so we need MySQL dialect
  - **Real-world Analogy**: Like translating between British English and American English - same language, slight differences

- **spring.jpa.generate-ddl=true**: 
  - **Definition**: Enables automatic DDL (Data Definition Language) generation
  - **Purpose**: Allows Hibernate to generate CREATE/ALTER table statements
  - **Why Needed**: Works with `ddl-auto=update` to automatically manage database schema
  - **What It Does**: Enables Hibernate's DDL generation feature
  - **Why Enable**: Required for `ddl-auto=update` to work
  - **Real-world Analogy**: Like giving Hibernate permission to modify the database structure

- **spring.flyway.enabled=false**: 
  - **Definition**: Disables Flyway database migration tool
  - **Purpose**: We're using Hibernate auto-DDL, so we don't need Flyway right now
  - **Why Disable**: Hibernate is managing schema automatically - Flyway would conflict
  - **What It Does**: Prevents Flyway from running migrations
  - **When to Enable**: If you want to use SQL migration files instead of Hibernate auto-DDL
  - **Real-world Analogy**: Like turning off one tool when using another - prevents conflicts

- **app.jwt.secret**: 
  - **Definition**: A secret key used to sign and verify JWT tokens
  - **Purpose**: Ensures tokens haven't been tampered with - only someone with this secret can create valid tokens
  - **Why Needed**: Without a secret, anyone could create fake tokens - this secret prevents that
  - **What It Does**: Used to cryptographically sign tokens (like a signature) and verify them
  - **Why Must Be 32+ Characters**: Shorter secrets are easier to crack - 32+ characters provides strong security
  - **Security Warning**: 
    - Never share this secret publicly
    - Use different secrets for different environments
    - In production, store in environment variables or secret management system
  - **How It Works**: 
    - When creating token: Secret + token data → signed token
    - When validating token: Secret + token → verify signature matches
  - **Real-world Analogy**: Like a secret password that only you and the server know - used to verify tokens are authentic

- **app.jwt.expirationSeconds**: 
  - **Definition**: How long (in seconds) a JWT token remains valid
  - **Purpose**: Limits how long a token can be used - prevents indefinite access
  - **Why Needed**: Security - if a token is stolen, it will expire eventually
  - **What It Does**: Sets token expiration time - after this time, token becomes invalid
  - **Why 9000 Seconds (2.5 hours)**: 
    - Long enough for a work session
    - Short enough that stolen tokens expire relatively quickly
    - Balance between security and user convenience
  - **How It Works**: Token creation time + expiration seconds = expiration time
  - **Real-world Analogy**: Like a parking ticket - valid for a certain time, then expires

- **logging.level.root=INFO**: 
  - **Definition**: Sets the default logging level for all loggers to INFO
  - **Purpose**: Controls how much detail appears in logs
  - **Why Needed**: Too much logging slows down application, too little makes debugging hard
  - **What INFO Means**: Shows informational messages, warnings, and errors (but not debug/trace)
  - **Log Levels (from least to most verbose)**: ERROR < WARN < INFO < DEBUG < TRACE
  - **Why INFO**: Good balance - shows important information without overwhelming detail
  - **Real-world Analogy**: Like volume control - INFO is medium volume, shows important stuff

- **logging.level.com.SpringBootSecurity=DEBUG**: 
  - **Definition**: Sets logging level to DEBUG for our specific package
  - **Purpose**: Shows more detailed logs for our application code
  - **Why Needed**: Helps debug our code - shows what methods are called, what values are used
  - **What DEBUG Shows**: Everything INFO shows, plus detailed debug information
  - **Why Only Our Package**: We want detailed logs for our code, but not for Spring framework internals (too verbose)
  - **Real-world Analogy**: Like turning on detailed mode for your own code, but keeping it simple for everything else

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

**What Each Part Does** (Detailed Explanation):

- **`@Entity`**: 
  - **Definition**: A JPA (Java Persistence API) annotation that marks this class as a database entity
  - **Purpose**: Tells Hibernate (JPA implementation) that this Java class represents a database table
  - **Why Needed**: Without this annotation, Hibernate won't know this class should be mapped to a database table
  - **What It Does**: Makes Hibernate scan this class and create a corresponding table in the database
  - **How It Works**: When application starts, Hibernate reads `@Entity` classes and creates/updates tables
  - **Real-world Analogy**: Like a label on a box that says "This box contains something important (a database table)"

- **`@Table(name = "roles")`**: 
  - **Definition**: Specifies the exact name of the database table
  - **Purpose**: Tells Hibernate what to name the table in the database
  - **Why Needed**: Without this, Hibernate would use the class name "Role" as table name (which works, but "roles" is more conventional - plural)
  - **What It Does**: Creates a table named "roles" instead of "Role"
  - **Why "roles" (plural)**: Database convention - table names are usually plural (roles, users, employees)
  - **Real-world Analogy**: Like naming a file - you could use "Role.java" but "roles" table is clearer

- **`@Id`**: 
  - **Definition**: Marks a field as the primary key of the table
  - **Purpose**: Identifies the unique identifier for each record in the table
  - **Why Needed**: Every database table needs a primary key - this tells Hibernate which field is the primary key
  - **What It Does**: Makes this field the unique identifier - no two records can have the same ID
  - **Why Primary Key**: Allows fast lookups, ensures uniqueness, enables relationships with other tables
  - **Real-world Analogy**: Like a social security number - unique identifier for each person (record)

- **`@GeneratedValue(strategy = GenerationType.IDENTITY)`**: 
  - **Definition**: Tells the database to automatically generate values for this field
  - **Purpose**: Automatically assigns sequential numbers (1, 2, 3, ...) to new records
  - **Why Needed**: Without this, you'd have to manually assign IDs - this does it automatically
  - **What "IDENTITY" Strategy Means**: 
    - Database handles ID generation (auto-increment)
    - Most efficient for MySQL
    - ID is generated when record is inserted
  - **Other Strategies**: 
    - `AUTO`: Let Hibernate choose (may not work well with MySQL)
    - `SEQUENCE`: Use database sequence (PostgreSQL style)
    - `TABLE`: Use a separate table to track IDs (slower, rarely used)
  - **Why IDENTITY**: Best for MySQL - uses MySQL's AUTO_INCREMENT feature
  - **How It Works**: When you save a new Role, database automatically assigns next number (1, 2, 3, ...)
  - **Real-world Analogy**: Like a ticket number machine - automatically gives next number when you press the button

- **`@Column(unique = true, nullable = false, length = 50)`**: 
  - **Definition**: Configures how this column appears in the database table
  - **Purpose**: Adds constraints and specifications to the database column
  - **Why Needed**: Ensures data quality - prevents duplicates, requires values, limits length
  - **What Each Parameter Does**: 
    - `unique = true`: No two roles can have the same name (e.g., can't have two "ADMIN" roles)
    - `nullable = false`: Name field is required - cannot be null/empty
    - `length = 50`: Maximum 50 characters allowed for role name
  - **Why These Constraints**: 
    - `unique`: Prevents duplicate roles (only one "ADMIN", one "USER")
    - `nullable = false`: Every role must have a name - it's required
    - `length = 50`: Limits name length for database efficiency and data integrity
  - **Real-world Analogy**: Like a form with required fields and character limits

- **`@ManyToMany(mappedBy = "roles")`**: 
  - **Definition**: Defines a many-to-many relationship between Role and User
  - **Purpose**: Allows one role to belong to many users, and one user to have many roles
  - **Why Needed**: Users can have multiple roles (e.g., ADMIN and USER), and roles can belong to multiple users
  - **What "mappedBy = 'roles'" Means**: 
    - This is the "inverse" side of the relationship
    - The actual relationship is defined in the User class (in the "roles" field)
    - This side just references that relationship
  - **How It Works**: 
    - User class has `@ManyToMany` with `@JoinTable` (defines the join table)
    - Role class has `@ManyToMany(mappedBy = "roles")` (references the relationship)
    - Hibernate creates a `user_roles` join table to link users and roles
  - **Why mappedBy**: Prevents Hibernate from creating two join tables - tells it to use the one defined in User class
  - **Real-world Analogy**: Like a many-to-many relationship between students and courses - one student takes many courses, one course has many students, and there's a "enrollments" table linking them

- **`Set<User> users = new HashSet<>()`**: 
  - **Definition**: A collection that holds all users who have this role
  - **Purpose**: Allows you to access all users with a specific role (e.g., all users with ADMIN role)
  - **Why Set (not List)**: 
    - Set prevents duplicates (same user can't be added twice)
    - Set has faster lookup performance
    - Set is unordered (we don't care about order for this relationship)
  - **Why HashSet**: Fastest Set implementation for this use case
  - **Why Initialize with `new HashSet<>()`**: Prevents NullPointerException - ensures collection exists even if empty
  - **Real-world Analogy**: Like a list of all students enrolled in a course

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

**Definition**: The User entity represents user accounts in the system. Each user has login credentials (username, email, password) and can have one or more roles.

**Purpose**: This class:
- Defines what a user account is (username, email, password, enabled status)
- Creates the `users` table in the database
- Stores user authentication information
- Links users to their roles through a many-to-many relationship

**Why We Need This**: We need users to authenticate and authorize. Users are the foundation of our security system - without users, there's no one to authenticate.

**Key Features**:
- **Username and Email**: Both must be unique - no two users can have the same username or email
- **Password**: Stored as BCrypt hash (encrypted) - never stored in plain text
- **Enabled**: Allows disabling accounts without deleting them
- **Roles**: Many-to-many relationship - users can have multiple roles

**What Each Annotation Does** (Detailed Explanation):

- **`@Column(unique = true, nullable = false)` on username**:
  - **Definition**: Makes username field unique and required
  - **Purpose**: Ensures no duplicate usernames and username is always provided
  - **Why Needed**: Usernames must be unique for login - can't have two users with same username
  - **What It Does**: Database enforces uniqueness - trying to create duplicate username will fail
  - **Real-world Analogy**: Like a unique email address - no two people can have the same one

- **`@Column(unique = true, nullable = false)` on email**:
  - **Definition**: Makes email field unique and required
  - **Purpose**: Ensures no duplicate emails and email is always provided
  - **Why Needed**: Emails are used for login and password reset - must be unique
  - **What It Does**: Database enforces uniqueness - trying to create duplicate email will fail
  - **Real-world Analogy**: Like a unique phone number - each person has their own

- **`@Column(nullable = false)` on password**:
  - **Definition**: Makes password field required (cannot be null)
  - **Purpose**: Ensures every user has a password
  - **Why Needed**: Security - users must have passwords to authenticate
  - **What It Does**: Database prevents creating users without passwords
  - **Security Note**: Password is stored as BCrypt hash, never plain text
  - **Real-world Analogy**: Like requiring a key to enter a building

- **`Boolean enabled = true`**:
  - **Definition**: Field that indicates if user account is active
  - **Purpose**: Allows disabling accounts without deleting them
  - **Why Needed**: Sometimes you want to temporarily disable an account (e.g., suspicious activity)
  - **What It Does**: When `false`, user cannot login even with correct password
  - **Why Default `true`**: New users should be enabled by default
  - **Real-world Analogy**: Like an on/off switch for an account

- **`@ManyToMany(fetch = FetchType.EAGER)`**:
  - **Definition**: Defines many-to-many relationship with Role, with eager fetching
  - **Purpose**: Links users to their roles
  - **Why Needed**: Users can have multiple roles (e.g., ADMIN and USER)
  - **What "EAGER" Fetch Type Means**:
    - When you load a User, roles are loaded immediately (not lazy)
    - Roles are fetched in the same database query as the user
    - Opposite of LAZY (which would load roles only when accessed)
  - **Why EAGER for Roles**: 
    - Security checks need roles immediately - can't wait to load them
    - Roles are small data - loading them eagerly doesn't hurt performance
    - Prevents N+1 query problem (loading user then loading roles separately)
  - **Real-world Analogy**: Like getting your ID card and access badges together, not separately

- **`@JoinTable(name = "user_roles", ...)`**:
  - **Definition**: Defines the join table that links users and roles
  - **Purpose**: Creates a bridge table to connect users and roles (many-to-many relationship)
  - **Why Needed**: Many-to-many relationships need a join table (can't directly link two tables)
  - **What It Creates**: A `user_roles` table with `user_id` and `role_id` columns
  - **How It Works**:
    - `name = "user_roles"`: Names the join table "user_roles"
    - `joinColumns = @JoinColumn(name = "user_id")`: Column in join table that references users table
    - `inverseJoinColumns = @JoinColumn(name = "role_id")`: Column in join table that references roles table
  - **Why Join Table**: 
    - One user can have many roles
    - One role can belong to many users
    - Need a middle table to store these relationships
  - **Real-world Analogy**: Like a class enrollment table - links students (users) to courses (roles)

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

**Definition**: Repository interfaces are Java interfaces that provide methods to interact with the database. They act as a bridge between your Java code and the database.

**Purpose**: Repositories:
- Provide methods to save, find, update, and delete data
- Automatically generate SQL queries based on method names
- Handle database operations without writing SQL manually
- Make database access simple and consistent

**Why We Create These**: Without repositories, you'd have to write SQL queries for every database operation. Repositories provide ready-made methods and can generate queries automatically from method names.

**How They Work**:
1. You define an interface extending `JpaRepository<Entity, ID>`
2. Spring Data JPA automatically creates an implementation
3. You can add custom methods following naming conventions
4. Spring generates SQL queries automatically

**Real-world Analogy**: Like a library catalog system - you ask for a book by title (method name), and the system finds it for you (generates and executes SQL).

### 5.1 Create RoleRepository

**Definition**: RoleRepository is an interface that provides database operations for the Role entity.

**Purpose**: This repository allows you to:
- Save new roles to the database
- Find roles by ID or name
- Delete roles
- Count roles
- List all roles

**Why We Need This**: We need to find roles by name (e.g., "ADMIN", "USER") when assigning roles to users. Without this repository, we'd have to write SQL queries manually.

**What Each Part Does** (Detailed Explanation):

- **`extends JpaRepository<Role, Long>`**:
  - **Definition**: Extends Spring Data JPA's repository interface
  - **Purpose**: Provides basic CRUD (Create, Read, Update, Delete) operations automatically
  - **Why Needed**: Gives you methods like `save()`, `findById()`, `findAll()`, `delete()` without writing them
  - **What `<Role, Long>` Means**:
    - `Role`: The entity type this repository works with
    - `Long`: The type of the primary key (ID) - Role has Long id
  - **What Methods You Get Automatically**:
    - `save(Role role)` - Save or update a role
    - `findById(Long id)` - Find role by ID
    - `findAll()` - Get all roles
    - `deleteById(Long id)` - Delete role by ID
    - `count()` - Count total roles
    - `existsById(Long id)` - Check if role exists
  - **Real-world Analogy**: Like getting a toolbox with all basic tools - you don't need to make them, they're already there

- **`Optional<Role> findByName(String name)`**:
  - **Definition**: Custom method to find a role by its name
  - **Purpose**: Allows finding roles by name (e.g., find "ADMIN" role)
  - **Why Needed**: We need to find roles by name when assigning them to users (e.g., "ADMIN", "USER")
  - **How It Works**: 
    - Spring Data JPA reads the method name
    - Sees "findBy" + "Name"
    - Generates SQL: `SELECT * FROM roles WHERE name = ?`
    - Executes query and returns result
  - **What "Optional" Means**:
    - `Optional<Role>` can contain a Role or be empty
    - Prevents NullPointerException - if role not found, returns empty Optional, not null
    - Forces you to check if role exists before using it
  - **Why Optional**: Better than returning null - makes it explicit that role might not exist
  - **Real-world Analogy**: Like asking "Do you have a book titled X?" - answer is either "Yes, here it is" or "No, we don't have it" (not just null)

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

**Definition**: UserRepository is an interface that provides database operations for the User entity.

**Purpose**: This repository allows you to:
- Find users by username or email (for login)
- Check if username or email already exists (for signup validation)
- Save, update, and delete users
- Perform all standard CRUD operations

**Why We Need This**: 
- Login needs to find users by username or email
- Signup needs to check if username/email already exists
- User management needs to save, update, and delete users

**What Each Method Does** (Detailed Explanation):

- **`Optional<User> findByUsername(String username)`**:
  - **Definition**: Finds a user by their username
  - **Purpose**: Used during login when user provides username
  - **Why Needed**: Login process needs to find the user account by username
  - **How It Works**: Spring generates SQL: `SELECT * FROM users WHERE username = ?`
  - **Returns**: Optional containing User if found, empty Optional if not found
  - **Real-world Analogy**: Like looking up a person by their name in a directory

- **`Optional<User> findByEmail(String email)`**:
  - **Definition**: Finds a user by their email address
  - **Purpose**: Used during login when user provides email instead of username
  - **Why Needed**: Users can login with either username OR email - this handles email login
  - **How It Works**: Spring generates SQL: `SELECT * FROM users WHERE email = ?`
  - **Returns**: Optional containing User if found, empty Optional if not found
  - **Real-world Analogy**: Like looking up a person by their email address

- **`boolean existsByUsername(String username)`**:
  - **Definition**: Checks if a username already exists in the database
  - **Purpose**: Used during signup to prevent duplicate usernames
  - **Why Needed**: Usernames must be unique - this checks before creating new user
  - **How It Works**: Spring generates SQL: `SELECT COUNT(*) > 0 FROM users WHERE username = ?`
  - **Returns**: `true` if username exists, `false` if available
  - **Why Boolean (not Optional)**: We only need to know if it exists, not the actual user
  - **Real-world Analogy**: Like checking if a username is taken on a website

- **`boolean existsByEmail(String email)`**:
  - **Definition**: Checks if an email already exists in the database
  - **Purpose**: Used during signup to prevent duplicate emails
  - **Why Needed**: Emails must be unique - this checks before creating new user
  - **How It Works**: Spring generates SQL: `SELECT COUNT(*) > 0 FROM users WHERE email = ?`
  - **Returns**: `true` if email exists, `false` if available
  - **Why Boolean (not Optional)**: We only need to know if it exists, not the actual user
  - **Real-world Analogy**: Like checking if an email is already registered

**Why These Methods Are Important**:
- **Login Flow**: Uses `findByUsername()` or `findByEmail()` to find user account
- **Signup Flow**: Uses `existsByUsername()` and `existsByEmail()` to validate uniqueness
- **Security**: Prevents duplicate accounts and ensures proper authentication

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

**Definition**: DTOs (Data Transfer Objects) are simple Java classes used to transfer data between different layers of the application (client ↔ server, controller ↔ service).

**Purpose**: DTOs:
- Separate API data structure from database entity structure
- Control what data is sent to/received from clients
- Add validation rules for incoming data
- Hide internal database structure from API consumers
- Provide a clean contract between frontend and backend

**Why We Create These**: 
- **Security**: Don't expose internal entity structure (e.g., password hashes, internal IDs)
- **Flexibility**: API structure can differ from database structure
- **Validation**: Validate data before it reaches business logic
- **Versioning**: Can change DTOs without changing database entities
- **Performance**: Only send/receive necessary data

**Real-world Analogy**: Like a form at a doctor's office - you fill out a form (DTO) with your info, but the doctor's internal records (Entity) have much more detail.

**DTO vs Entity**:
- **Entity**: Represents database table structure, includes relationships, used internally
- **DTO**: Represents API request/response structure, simple data only, used for communication

### 6.1 Create Auth DTOs

**Definition**: Auth DTOs handle data for authentication operations (signup, login, token responses).

**Purpose**: These DTOs:
- Receive signup/login data from clients
- Validate input before processing
- Return token information to clients
- Provide clear API contracts

**Why We Need These**: Authentication needs specific data structures that differ from User entity (e.g., password in plain text for signup, but hashed in entity).

#### SignupRequest DTO

**Definition**: SignupRequest is a DTO that receives user registration data from clients.

**Purpose**: This DTO:
- Captures signup information (username, email, password, role)
- Validates input data before creating user
- Provides clear error messages for invalid data

**Why We Need This**: Signup needs to receive and validate data before creating a User entity. This DTO acts as the input form.

**What Each Part Does** (Detailed Explanation):

- **`@Data`**:
  - **Definition**: Lombok annotation that generates getters, setters, toString, equals, hashCode
  - **Purpose**: Reduces boilerplate code - no need to write getters/setters manually
  - **Why Needed**: Saves time and reduces code - one annotation instead of 50+ lines
  - **What It Generates**: All standard Java bean methods automatically
  - **Real-world Analogy**: Like a code generator - you describe what you want, it writes the code

- **`@NotBlank(message = "Username is required")`**:
  - **Definition**: Validation annotation that ensures field is not null, empty, or whitespace
  - **Purpose**: Validates that username is provided and not empty
  - **Why Needed**: Username is required for signup - this ensures it's always provided
  - **What It Does**: 
    - Checks if username is null, empty string, or only whitespace
    - If invalid, returns error message "Username is required"
    - Validation happens automatically when DTO is received
  - **When It Runs**: Before the controller method executes - Spring validates automatically
  - **Real-world Analogy**: Like a bouncer checking if you have an ID before entering

- **`@Email(message = "Email should be valid")`**:
  - **Definition**: Validation annotation that ensures field contains a valid email format
  - **Purpose**: Validates email format (must have @, domain, etc.)
  - **Why Needed**: Email must be valid format - "invalid-email" should be rejected
  - **What It Does**: 
    - Checks if email matches standard email pattern (user@domain.com)
    - If invalid, returns error message "Email should be valid"
    - Validates format, not whether email actually exists
  - **Why Both @NotBlank and @Email**: @NotBlank ensures it's provided, @Email ensures it's valid format
  - **Real-world Analogy**: Like checking if an address is in the correct format (street, city, zip)

- **`@Pattern(regexp = "...", message = "...")`**:
  - **Definition**: Validation annotation that checks if field matches a regular expression pattern
  - **Purpose**: Validates password meets security requirements
  - **Why Needed**: Strong passwords prevent hacking - this enforces password strength rules
  - **What The Pattern Means**:
    - `^(?=.*[a-z])` - Must contain at least one lowercase letter
    - `(?=.*[A-Z])` - Must contain at least one uppercase letter
    - `(?=.*\\d)` - Must contain at least one digit (number)
    - `(?=.*[@$!%*?&])` - Must contain at least one special character
    - `[A-Za-z\\d@$!%*?&]{8,}$` - Must be at least 8 characters long, only allowed characters
  - **Why This Pattern**: Enforces strong password policy - prevents weak passwords like "password123"
  - **How It Works**: 
    - Tests password against the pattern
    - If doesn't match, returns error message
    - Validation happens before password is processed
  - **Real-world Analogy**: Like a password strength checker - ensures password meets security requirements

- **`private String role = "USER"`**:
  - **Definition**: Field with default value "USER"
  - **Purpose**: Sets default role for new users
  - **Why Default "USER"**: Most users should be regular users, not admins - this is the safe default
  - **What It Does**: If client doesn't specify role, it defaults to "USER"
  - **Why Not Required**: Role is optional in signup - defaults to USER if not specified
  - **Real-world Analogy**: Like a default setting - if you don't choose, it uses the safe default

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

#### LoginRequest DTO

**Definition**: LoginRequest is a DTO that receives login credentials from clients.

**Purpose**: This DTO:
- Captures login information (username/email and password)
- Validates that credentials are provided
- Allows flexible login (username OR email)

**Why We Need This**: Login needs to receive credentials and validate them. This DTO provides a clean structure for login data.

**What Each Part Does** (Detailed Explanation):

- **`@NotBlank(message = "Username or email is required")` on usernameOrEmail**:
  - **Definition**: Ensures username or email is provided
  - **Purpose**: Login requires either username or email - this validates it's provided
  - **Why Needed**: Can't login without username/email - this ensures it's always provided
  - **What It Does**: Checks if field is not null/empty, returns error if missing
  - **Why "usernameOrEmail"**: Users can login with either username OR email - flexible login
  - **Real-world Analogy**: Like requiring either a username or email to login to a website

- **`@NotBlank(message = "Password is required")` on password**:
  - **Definition**: Ensures password is provided
  - **Purpose**: Login requires password - this validates it's provided
  - **Why Needed**: Can't login without password - this ensures it's always provided
  - **What It Does**: Checks if password is not null/empty, returns error if missing
  - **Security Note**: Password is sent in request (will be encrypted in HTTPS), validated on server
  - **Real-world Analogy**: Like requiring a password to unlock your phone

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

#### JwtResponse DTO

**Definition**: JwtResponse is a DTO that returns JWT token information to clients after successful login.

**Purpose**: This DTO:
- Returns the JWT token to the client
- Provides token metadata (type, expiration)
- Gives client all information needed to use the token

**Why We Need This**: After login, client needs the token and information about it. This DTO provides a structured response.

**What Each Part Does** (Detailed Explanation):

- **`@AllArgsConstructor`**:
  - **Definition**: Lombok annotation that generates a constructor with all fields
  - **Purpose**: Allows creating JwtResponse with all values at once
  - **Why Needed**: Convenient way to create response: `new JwtResponse(token, "Bearer", 9000L)`
  - **What It Generates**: Constructor that takes all fields as parameters
  - **Real-world Analogy**: Like a form with all fields filled - creates object with all data at once

- **`private String token`**:
  - **Definition**: The actual JWT token string
  - **Purpose**: This is what the client uses for authentication
  - **Why Needed**: Client needs this token to access protected endpoints
  - **What It Contains**: Encoded JWT with username, roles, expiration, signature
  - **How Client Uses It**: Sends in Authorization header: `Authorization: Bearer <token>`
  - **Real-world Analogy**: Like a ticket you get at an event - you show it to access different areas

- **`private String tokenType = "Bearer"`**:
  - **Definition**: The type of token (always "Bearer" for JWT)
  - **Purpose**: Tells client how to use the token
  - **Why Needed**: Standard HTTP authentication format - "Bearer" indicates token-based auth
  - **What "Bearer" Means**: Standard OAuth 2.0 token type - means "whoever has this token can use it"
  - **Why Default "Bearer"**: JWT tokens are always Bearer tokens - this is the standard
  - **Real-world Analogy**: Like a label that says "VIP Pass" - tells you what type of access you have

- **`private Long expiresInSeconds`**:
  - **Definition**: How many seconds until the token expires
  - **Purpose**: Tells client when token will expire
  - **Why Needed**: Client needs to know when to refresh token or login again
  - **What It Contains**: Number of seconds (e.g., 9000 = 2.5 hours)
  - **How Client Uses It**: Can calculate expiration time and refresh token before it expires
  - **Real-world Analogy**: Like an expiration date on food - tells you when it's no longer valid

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

**Definition**: Security components are classes that handle authentication and authorization in the application. They ensure only authorized users can access protected resources.

**Purpose**: Security components:
- Authenticate users (verify who they are)
- Authorize access (control what they can do)
- Generate and validate JWT tokens
- Load user details for authentication
- Filter requests to check authentication

**Why We Create These**: Without security components, anyone could access any endpoint. These components protect the application and ensure only authenticated, authorized users can access resources.

**Real-world Analogy**: Like a security system for a building - checks IDs (authentication), controls access to different floors (authorization), and issues access cards (JWT tokens).

### 7.1 Create JwtUtils

**Definition**: JwtUtils is a utility class that handles JWT token operations - creating tokens, validating tokens, and extracting information from tokens.

**Purpose**: This class:
- Generates JWT tokens after successful login
- Validates tokens on incoming requests
- Extracts username and roles from tokens
- Handles token expiration and errors

**Why We Need This**: JWT tokens are the core of our authentication system. This class provides all the methods needed to work with tokens.

**What Each Part Does** (Detailed Explanation):

- **`@Component`**:
  - **Definition**: Spring annotation that marks this class as a Spring-managed component
  - **Purpose**: Makes this class available for dependency injection
  - **Why Needed**: Other classes need to use JwtUtils - Spring injects it automatically
  - **What It Does**: Spring creates an instance and manages it - you don't create it manually
  - **Real-world Analogy**: Like registering a service - Spring knows it exists and can provide it when needed

- **`@Value("${app.jwt.secret}")`**:
  - **Definition**: Injects value from application.properties
  - **Purpose**: Gets JWT secret key from configuration
  - **Why Needed**: Secret key is in configuration file, not hardcoded - this reads it
  - **What It Does**: Reads `app.jwt.secret` from application.properties and sets jwtSecret field
  - **Why From Properties**: Allows changing secret without recompiling code
  - **Real-world Analogy**: Like reading a password from a config file instead of hardcoding it

- **`@Value("${app.jwt.expirationSeconds}")`**:
  - **Definition**: Injects token expiration time from configuration
  - **Purpose**: Gets how long tokens should be valid
  - **Why Needed**: Expiration time is configurable - this reads it from properties
  - **What It Does**: Reads `app.jwt.expirationSeconds` (9000) and sets jwtExpirationMs field
  - **Why Configurable**: Allows changing expiration without code changes
  - **Real-world Analogy**: Like reading a time limit from settings

- **`private SecretKey getSigningKey()`**:
  - **Definition**: Private method that creates the secret key for signing tokens
  - **Purpose**: Converts the secret string into a cryptographic key
  - **Why Needed**: JWT library needs a SecretKey object, not a string - this converts it
  - **What It Does**: 
    - Validates secret is at least 32 characters (security requirement)
    - Converts secret string to SecretKey using HMAC-SHA algorithm
    - Returns key used to sign and verify tokens
  - **Why Private**: Only used internally - other classes don't need direct access
  - **Why 32+ Characters**: Shorter keys are easier to crack - 32+ provides strong security
  - **Real-world Analogy**: Like converting a password into an encryption key

- **`public String generateToken(String username, List<GrantedAuthority> authorities)`**:
  - **Definition**: Creates a new JWT token with user information
  - **Purpose**: Generates token after successful login
  - **Why Needed**: Client needs a token to access protected endpoints
  - **What It Does**:
    1. Gets current time (token creation time)
    2. Calculates expiration time (now + expiration seconds)
    3. Extracts role names from authorities
    4. Builds JWT with username, roles, creation time, expiration
    5. Signs token with secret key
    6. Returns encoded token string
  - **Parameters**:
    - `username`: User's username (becomes token subject)
    - `authorities`: User's roles/permissions (stored in token claims)
  - **Returns**: Encoded JWT token string
  - **Real-world Analogy**: Like creating an ID card with your name and access levels

- **`public String getUsernameFromToken(String token)`**:
  - **Definition**: Extracts username from a JWT token
  - **Purpose**: Gets the username from token without validating (for quick lookup)
  - **Why Needed**: Need to know which user the token belongs to
  - **What It Does**: 
    1. Parses the token
    2. Extracts the "subject" claim (which contains username)
    3. Returns username
  - **Security Note**: This doesn't validate the token - use `validateToken()` first
  - **Real-world Analogy**: Like reading a name from an ID card

- **`public boolean validateToken(String token)`**:
  - **Definition**: Validates if a JWT token is valid and not expired
  - **Purpose**: Checks if token can be trusted and is still valid
  - **Why Needed**: Prevents use of expired or tampered tokens
  - **What It Does**:
    1. Tries to parse and verify token signature
    2. Checks if token is expired
    3. Checks if token format is valid
    4. Returns true if valid, false if invalid/expired
  - **Error Handling**: 
    - Catches `ExpiredJwtException` - token expired
    - Catches `MalformedJwtException` - token format is wrong
    - Catches other exceptions - other validation errors
  - **Why Boolean**: Simple yes/no answer - is token valid or not
  - **Real-world Analogy**: Like checking if an ID card is still valid and not fake

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

**Definition**: CustomUserDetailsService is a service that loads user information from the database for Spring Security authentication.

**Purpose**: This class:
- Implements Spring Security's UserDetailsService interface
- Loads user details from database when authentication is needed
- Converts our User entity to Spring Security's UserDetails format
- Handles username/email login (flexible login)
- Checks if user account is enabled

**Why We Need This**: Spring Security needs user details to authenticate. This class bridges our User entity with Spring Security's authentication system.

**What Each Part Does** (Detailed Explanation):

- **`@Service`**:
  - **Definition**: Spring annotation that marks this as a service component
  - **Purpose**: Makes this class available for dependency injection
  - **Why Needed**: Spring Security needs to use this service - Spring injects it automatically
  - **What It Does**: Spring creates and manages an instance of this service
  - **Real-world Analogy**: Like registering a service provider - Spring knows where to find it

- **`implements UserDetailsService`**:
  - **Definition**: Implements Spring Security's interface for loading user details
  - **Purpose**: Tells Spring Security this class can load user information
  - **Why Needed**: Spring Security requires this interface to authenticate users
  - **What It Requires**: Must implement `loadUserByUsername()` method
  - **Real-world Analogy**: Like signing a contract - you agree to provide user details when asked

- **`public UserDetails loadUserByUsername(String usernameOrEmail)`**:
  - **Definition**: Main method that loads user details for authentication
  - **Purpose**: Finds user in database and converts to Spring Security format
  - **Why Needed**: Spring Security calls this method during authentication
  - **What It Does**:
    1. Tries to find user by username first
    2. If not found, tries to find by email
    3. If still not found, throws UsernameNotFoundException
    4. Checks if user account is enabled
    5. Converts User entity to Spring Security UserDetails
    6. Returns UserDetails with username, password, roles, account status
  - **Why "usernameOrEmail"**: Users can login with either username OR email - this handles both
  - **Real-world Analogy**: Like a receptionist looking up a person by name or ID number

- **`private Collection<? extends GrantedAuthority> getAuthorities(User user)`**:
  - **Definition**: Converts user roles to Spring Security authorities
  - **Purpose**: Transforms our Role entities into Spring Security's authority format
  - **Why Needed**: Spring Security uses "ROLE_ADMIN", "ROLE_USER" format - this converts
  - **What It Does**:
    1. Gets all roles from user
    2. Converts each role name to "ROLE_" + role name format
    3. Creates SimpleGrantedAuthority objects
    4. Returns collection of authorities
  - **Why "ROLE_" Prefix**: Spring Security convention - roles must have "ROLE_" prefix
  - **Real-world Analogy**: Like converting job titles to access levels

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

**Definition**: JwtAuthenticationFilter is a filter that intercepts HTTP requests, extracts JWT tokens, validates them, and sets up authentication in Spring Security context.

**Purpose**: This filter:
- Runs before every HTTP request
- Extracts JWT token from Authorization header
- Validates the token
- Loads user details if token is valid
- Sets authentication in Spring Security context
- Allows request to continue to controller

**Why We Need This**: This filter is the gatekeeper - it checks every request for a valid token and sets up authentication so controllers know who is making the request.

**What Each Part Does** (Detailed Explanation):

- **`@Component`**:
  - **Definition**: Marks this as a Spring component
  - **Purpose**: Makes this class available for Spring to manage
  - **Why Needed**: Spring needs to create and configure this filter
  - **What It Does**: Spring creates instance and can inject it into SecurityConfig
  - **Real-world Analogy**: Like registering a security guard - Spring knows to use it

- **`extends OncePerRequestFilter`**:
  - **Definition**: Extends Spring's filter that runs once per request
  - **Purpose**: Ensures filter logic runs exactly once per HTTP request
  - **Why Needed**: Prevents filter from running multiple times (which would be inefficient)
  - **What It Provides**: Framework for creating request filters
  - **Real-world Analogy**: Like a checkpoint that each person goes through once

- **`protected void doFilterInternal(...)`**:
  - **Definition**: Main method that processes each request
  - **Purpose**: Contains the actual filter logic
  - **Why Needed**: This is where token extraction and validation happens
  - **What It Does**:
    1. Extracts JWT token from request header
    2. If token exists and is valid:
       - Gets username from token
       - Loads user details from database
       - Creates authentication object
       - Sets authentication in SecurityContext
    3. Continues request to next filter/controller
  - **Parameters**:
    - `HttpServletRequest`: The incoming HTTP request
    - `HttpServletResponse`: The HTTP response
    - `FilterChain`: Chain of filters - call to continue to next filter
  - **Real-world Analogy**: Like a security checkpoint - checks your ID, then lets you through

- **`private String parseJwt(HttpServletRequest request)`**:
  - **Definition**: Extracts JWT token from Authorization header
  - **Purpose**: Gets the token string from "Authorization: Bearer <token>" header
  - **Why Needed**: Token is in header, need to extract it
  - **What It Does**:
    1. Gets "Authorization" header value
    2. Checks if it starts with "Bearer "
    3. If yes, extracts token (removes "Bearer " prefix)
    4. Returns token string or null if not found
  - **Why "Bearer " Prefix**: Standard HTTP authentication format
  - **Real-world Analogy**: Like reading a ticket number from a ticket stub

**How The Filter Works** (Step-by-Step Flow):

1. **Request Arrives**: HTTP request comes to server
2. **Filter Intercepts**: JwtAuthenticationFilter runs first (before controller)
3. **Extract Token**: Looks for "Authorization: Bearer <token>" header
4. **Validate Token**: If token exists, validates it using JwtUtils
5. **Load User**: If valid, loads user details from database
6. **Set Authentication**: Sets authentication in Spring Security context
7. **Continue Request**: Request continues to controller with authentication set
8. **Controller Executes**: Controller can now check user roles and permissions

**Why This Order Matters**: Filter must run before controllers so authentication is set up when controller methods execute.

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

**Definition**: SecurityConfig is the main security configuration class that configures Spring Security for the entire application.

**Purpose**: This class:
- Configures which endpoints are public (no auth required)
- Configures which endpoints require authentication
- Sets up JWT authentication filter
- Configures password encoding
- Disables unnecessary security features (CSRF, form login, etc.)
- Enables method-level security (@PreAuthorize)

**Why We Need This**: Spring Security needs configuration to know how to protect the application. This class is the central place where all security settings are defined.

**What Each Part Does** (Detailed Explanation):

- **`@Configuration`**:
  - **Definition**: Marks this class as a Spring configuration class
  - **Purpose**: Tells Spring this class contains configuration beans
  - **Why Needed**: Spring needs to know this class provides configuration
  - **What It Does**: Spring processes this class and creates beans defined in it
  - **Real-world Analogy**: Like a settings file - Spring reads it to configure security

- **`@EnableWebSecurity`**:
  - **Definition**: Enables Spring Security web security features
  - **Purpose**: Activates Spring Security for web requests
  - **Why Needed**: Without this, Spring Security won't protect web endpoints
  - **What It Does**: Enables security filters, authentication, authorization
  - **Real-world Analogy**: Like turning on a security system

- **`@EnableMethodSecurity(prePostEnabled = true)`**:
  - **Definition**: Enables method-level security annotations
  - **Purpose**: Allows using @PreAuthorize, @PostAuthorize annotations on methods
  - **Why Needed**: We use @PreAuthorize("hasRole('ADMIN')") on controller methods
  - **What "prePostEnabled" Means**: Enables @PreAuthorize (check before method) and @PostAuthorize (check after method)
  - **Real-world Analogy**: Like enabling permission checks on individual rooms

- **`@Bean public JwtAuthenticationFilter authenticationJwtTokenFilter()`**:
  - **Definition**: Creates a bean for the JWT authentication filter
  - **Purpose**: Makes the filter available to Spring Security
  - **Why Needed**: Spring Security needs the filter instance to add it to the filter chain
  - **What It Does**: Creates and returns JwtAuthenticationFilter instance
  - **Real-world Analogy**: Like providing a security guard to the security system

- **`@Bean public AuthenticationProvider authenticationProvider()`**:
  - **Definition**: Creates authentication provider that uses our UserDetailsService
  - **Purpose**: Tells Spring Security how to authenticate users
  - **Why Needed**: Spring Security needs to know how to verify user credentials
  - **What It Does**: 
    - Creates DaoAuthenticationProvider (database-based authentication)
    - Sets our CustomUserDetailsService to load users
    - Sets BCrypt password encoder to verify passwords
  - **Real-world Analogy**: Like setting up an ID verification system

- **`@Bean public AuthenticationManager authenticationManager(...)`**:
  - **Definition**: Creates the authentication manager
  - **Purpose**: Handles authentication requests
  - **Why Needed**: Login process needs AuthenticationManager to verify credentials
  - **What It Does**: Gets AuthenticationManager from Spring Security configuration
  - **Real-world Analogy**: Like a manager who oversees the authentication process

- **`@Bean public SecurityFilterChain filterChain(HttpSecurity http)`**:
  - **Definition**: Main security configuration method
  - **Purpose**: Configures all security settings in one place
  - **Why Needed**: This is where we define what's protected and what's public
  - **What It Configures**:
    - **CSRF Disabled**: CSRF protection disabled (not needed for stateless JWT)
    - **HTTP Basic Disabled**: No basic authentication (we use JWT)
    - **Form Login Disabled**: No form-based login (we use JWT)
    - **Stateless Sessions**: No server-side sessions (JWT is stateless)
    - **Public Endpoints**: `/api/auth/**` is public (signup, login)
    - **Protected Endpoints**: All other endpoints require authentication
    - **JWT Filter**: Adds JWT filter before username/password filter
  - **Real-world Analogy**: Like setting up security rules for a building

- **`@Bean public PasswordEncoder passwordEncoder()`**:
  - **Definition**: Creates BCrypt password encoder
  - **Purpose**: Encodes passwords when saving, verifies passwords when authenticating
  - **Why Needed**: Passwords must be hashed, not stored in plain text
  - **What BCrypt Does**: 
    - One-way hashing (can't reverse to get original password)
    - Includes salt (prevents rainbow table attacks)
    - Slow by design (prevents brute force attacks)
  - **Real-world Analogy**: Like a one-way encryption system for passwords

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

**Definition**: Service layer contains business logic - the core functionality of the application. It sits between controllers (which handle HTTP) and repositories (which handle database).

**Purpose**: Service layer:
- Contains business logic (rules, validations, processing)
- Coordinates between controllers and repositories
- Handles complex operations that involve multiple steps
- Provides a clean interface for controllers
- Can be reused by multiple controllers

**Why We Create This**: 
- **Separation of Concerns**: Controllers handle HTTP, services handle business logic
- **Reusability**: Same service can be used by multiple controllers
- **Testability**: Easier to test business logic separately
- **Maintainability**: Business logic in one place, easier to change

**Real-world Analogy**: Like a restaurant - controllers are waiters (take orders), services are chefs (prepare food), repositories are pantry (get ingredients).

### 8.1 Create AuthService Interface

**Definition**: AuthService interface defines the contract for authentication operations.

**Purpose**: This interface:
- Defines what authentication operations are available
- Provides a clean API for authentication
- Allows different implementations (though we only have one)

**Why We Need This**: Interface allows flexibility - we can change implementation without changing controllers. Also follows good design principles.

**What Each Method Does** (Detailed Explanation):

- **`void signup(SignupRequest request, String requester)`**:
  - **Definition**: Method to register a new user
  - **Purpose**: Creates a new user account in the system
  - **Parameters**:
    - `request`: User registration data (username, email, password, role)
    - `requester`: Who is requesting this signup (for admin creation checks)
  - **Returns**: void (nothing) - just creates user
  - **Why Needed**: New users need to be able to register

- **`JwtResponse authenticate(LoginRequest request)`**:
  - **Definition**: Method to authenticate a user and return JWT token
  - **Purpose**: Verifies credentials and returns authentication token
  - **Parameters**: `request` - Login credentials (username/email, password)
  - **Returns**: JwtResponse containing token, token type, expiration
  - **Why Needed**: Users need to login and get tokens to access protected endpoints

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

**Definition**: AuthServiceImpl is the implementation of AuthService interface. It contains the actual business logic for authentication operations.

**Purpose**: This class:
- Implements signup logic (creates new users)
- Implements login logic (authenticates users and generates tokens)
- Validates user data before creating accounts
- Handles password encoding
- Manages role assignment

**Why We Need This**: This is where the actual authentication work happens. Controllers call this service to perform authentication operations.

**What Each Part Does** (Detailed Explanation):

- **`@Service`**:
  - **Definition**: Marks this as a Spring service component
  - **Purpose**: Makes this class available for dependency injection
  - **Why Needed**: Controllers need to use this service - Spring injects it automatically
  - **What It Does**: Spring creates and manages an instance of this service

- **Constructor Injection**:
  - **Definition**: Dependencies are provided through constructor
  - **Purpose**: Gets all required dependencies (repositories, encoders, managers)
  - **Why This Way**: Constructor injection is preferred - makes dependencies explicit and testable
  - **Dependencies**:
    - `UserRepository`: To save/find users
    - `RoleRepository`: To find roles
    - `PasswordEncoder`: To hash passwords
    - `AuthenticationManager`: To authenticate users
    - `JwtUtils`: To generate tokens

- **`public void signup(SignupRequest request, String requester)`**:
  - **Definition**: Creates a new user account
  - **Purpose**: Handles user registration
  - **What It Does Step-by-Step**:
    1. **Check Username**: Verifies username doesn't already exist
    2. **Check Email**: Verifies email doesn't already exist
    3. **Determine Role**: Gets role from request or defaults to "USER"
    4. **Admin Check**: If creating ADMIN, checks if requester is admin (security)
    5. **Find Role**: Gets Role entity from database
    6. **Create User**: Creates new User entity
    7. **Encode Password**: Hashes password using BCrypt (never store plain text)
    8. **Set Enabled**: Sets account as enabled (active)
    9. **Assign Role**: Assigns role to user
    10. **Save User**: Saves user to database
  - **Why Each Step**:
    - Username/Email check: Prevents duplicate accounts
    - Admin check: Security - prevents unauthorized admin creation
    - Password encoding: Security - passwords must be hashed
    - Role assignment: Determines user permissions
  - **Real-world Analogy**: Like creating a new account at a bank - verify info, set permissions, create account

- **`public JwtResponse authenticate(LoginRequest request)`**:
  - **Definition**: Authenticates user and returns JWT token
  - **Purpose**: Handles user login
  - **What It Does Step-by-Step**:
    1. **Authenticate**: Uses AuthenticationManager to verify credentials
    2. **Get Username**: Extracts username from authentication result
    3. **Get Authorities**: Gets user roles/permissions
    4. **Generate Token**: Creates JWT token with username and roles
    5. **Return Response**: Returns token, type, and expiration
  - **Why AuthenticationManager**: Spring Security handles password verification - we don't do it manually
  - **How Authentication Works**:
    - AuthenticationManager calls CustomUserDetailsService to load user
    - Compares provided password with stored hash
    - Returns authentication if password matches
  - **Real-world Analogy**: Like checking ID and password at a secure building, then issuing an access card

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

**Definition**: Controllers are classes that handle HTTP requests and responses. They are the entry point for API calls from clients.

**Purpose**: Controllers:
- Receive HTTP requests from clients
- Validate request data
- Call service methods to perform business logic
- Return HTTP responses to clients
- Handle URL routing (which URL calls which method)

**Why We Create These**: Controllers are the interface between clients (web, mobile apps) and our application. Without controllers, there's no way for external systems to interact with our application.

**Real-world Analogy**: Like a reception desk - receives requests, routes them to the right department (service), and sends back responses.

### 9.1 Create AuthController

**Definition**: AuthController handles authentication-related HTTP requests (signup, login).

**Purpose**: This controller:
- Handles user registration (signup endpoint)
- Handles user login (login endpoint)
- Returns appropriate HTTP status codes
- Validates request data before processing

**Why We Need This**: Clients need endpoints to signup and login. This controller provides those endpoints.

**What Each Part Does** (Detailed Explanation):

- **`@RestController`**:
  - **Definition**: Combines @Controller and @ResponseBody annotations
  - **Purpose**: Marks this class as a REST controller
  - **Why Needed**: Tells Spring this class handles HTTP requests and returns JSON responses
  - **What It Does**: 
    - Makes Spring scan this class for request mappings
    - Automatically converts return values to JSON
    - Handles HTTP request/response

- **`@RequestMapping("/api/auth")`**:
  - **Definition**: Sets base URL path for all methods in this controller
  - **Purpose**: All endpoints in this controller start with "/api/auth"
  - **Why Needed**: Groups related endpoints together
  - **What It Creates**: 
    - `/api/auth/signup` (from @PostMapping("/signup"))
    - `/api/auth/login` (from @PostMapping("/login"))
  - **Real-world Analogy**: Like a department name - all requests go to "/api/auth" department

- **`@PostMapping("/signup")`**:
  - **Definition**: Maps HTTP POST requests to "/api/auth/signup" URL
  - **Purpose**: Handles user registration requests
  - **Why POST**: Creating new resource (user) - POST is appropriate
  - **What It Does**: 
    - Receives SignupRequest in request body
    - Validates data (@Valid annotation)
    - Calls authService.signup()
    - Returns 201 Created status
  - **`@Valid`**: Triggers validation annotations on SignupRequest
  - **`@RequestBody`**: Converts JSON request body to SignupRequest object
  - **Real-world Analogy**: Like a registration form submission

- **`@PostMapping("/login")`**:
  - **Definition**: Maps HTTP POST requests to "/api/auth/login" URL
  - **Purpose**: Handles user login requests
  - **Why POST**: Sending credentials (sensitive data) - POST is more secure than GET
  - **What It Does**: 
    - Receives LoginRequest in request body
    - Validates data
    - Calls authService.authenticate()
    - Returns JwtResponse with token
  - **Returns**: JwtResponse containing token, type, expiration
  - **Real-world Analogy**: Like a login form submission

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

**Definition**: EmployeeController handles all HTTP requests related to employee operations (CRUD - Create, Read, Update, Delete).

**Purpose**: This controller:
- Provides endpoints to manage employees
- Enforces role-based access control (@PreAuthorize)
- Handles GET, POST, PUT, DELETE operations
- Converts between DTOs and entities

**Why We Need This**: Clients need endpoints to create, read, update, and delete employees. This controller provides those endpoints with proper security.

**What Each Part Does** (Detailed Explanation):

- **`@PreAuthorize("hasAnyRole('USER','ADMIN')")`**:
  - **Definition**: Security annotation that checks user roles before method executes
  - **Purpose**: Restricts access to users with USER or ADMIN role
  - **Why Needed**: Not everyone should access employee data - only authenticated users
  - **What It Does**: 
    - Checks if current user has USER or ADMIN role
    - If yes, allows method to execute
    - If no, returns 403 Forbidden
  - **When It Runs**: Before the controller method executes
  - **Real-world Analogy**: Like a security check - "Do you have USER or ADMIN access?"

- **`@PreAuthorize("hasRole('ADMIN')")`**:
  - **Definition**: More restrictive - only ADMIN role allowed
  - **Purpose**: Protects sensitive operations (like delete)
  - **Why Needed**: Only admins should delete employees
  - **What It Does**: Only allows ADMIN role, rejects USER role
  - **Real-world Analogy**: Like a VIP area - only admins allowed

- **`@GetMapping`**: Maps HTTP GET requests - used for retrieving data
- **`@PostMapping`**: Maps HTTP POST requests - used for creating new resources
- **`@PutMapping`**: Maps HTTP PUT requests - used for updating existing resources
- **`@DeleteMapping`**: Maps HTTP DELETE requests - used for deleting resources

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

**Definition**: ManagerController handles all HTTP requests related to manager operations. Most operations require ADMIN role.

**Purpose**: This controller:
- Provides endpoints to manage managers
- Enforces strict role-based access (mostly ADMIN-only)
- Handles CRUD operations for managers
- Uses service layer for business logic

**Why We Need This**: Clients need endpoints to manage managers. This controller provides those endpoints with proper security (mostly admin-only).

**Key Difference from EmployeeController**: Manager operations are more restricted - most require ADMIN role, while employee operations allow USER role.

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

**Definition**: Exception handlers are classes that catch and handle errors that occur in the application. They convert exceptions into proper HTTP responses.

**Purpose**: Exception handlers:
- Catch exceptions thrown anywhere in the application
- Convert exceptions to user-friendly error messages
- Return appropriate HTTP status codes
- Provide consistent error response format

**Why We Create These**: Without exception handlers, errors would return ugly stack traces or 500 errors. Exception handlers provide clean, user-friendly error responses.

**Real-world Analogy**: Like a customer service department - when something goes wrong, they handle it professionally and provide helpful responses.

### 10.1 Create ResourceNotFoundException

**Definition**: ResourceNotFoundException is a custom exception thrown when a requested resource (employee, manager, user) is not found.

**Purpose**: This exception:
- Represents "not found" errors
- Provides clear error messages
- Can be caught by GlobalExceptionHandler

**Why We Need This**: When someone requests an employee/manager that doesn't exist, we need a clear way to signal this error.

**What It Does**:
- Extends RuntimeException (unchecked exception)
- Takes resource name, field name, and field value
- Creates error message like "Employee not found with id : '123'"

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

**Definition**: GlobalExceptionHandler is a class that catches all exceptions in the application and converts them to proper HTTP responses.

**Purpose**: This class:
- Catches exceptions from all controllers
- Converts exceptions to JSON error responses
- Returns appropriate HTTP status codes
- Provides consistent error format

**Why We Need This**: Provides centralized error handling - all errors are handled in one place with consistent format.

**What Each Part Does** (Detailed Explanation):

- **`@RestControllerAdvice`**:
  - **Definition**: Combines @ControllerAdvice and @ResponseBody
  - **Purpose**: Makes this class handle exceptions from all controllers
  - **Why Needed**: Tells Spring this class handles exceptions globally
  - **What It Does**: Intercepts exceptions before they reach the client

- **`@ExceptionHandler(ResourceNotFoundException.class)`**:
  - **Definition**: Method that handles ResourceNotFoundException
  - **Purpose**: Catches "not found" errors and returns 404 status
  - **Why Needed**: Provides user-friendly 404 responses
  - **Returns**: 404 Not Found with error message

- **`@ExceptionHandler(MethodArgumentNotValidException.class)`**:
  - **Definition**: Handles validation errors
  - **Purpose**: Catches validation failures (e.g., blank username, invalid email)
  - **Why Needed**: Provides detailed validation error messages
  - **Returns**: 400 Bad Request with field-level error messages

- **`@ExceptionHandler(RuntimeException.class)`**:
  - **Definition**: Catches general runtime exceptions
  - **Purpose**: Handles unexpected errors
  - **Why Needed**: Catches errors not handled by specific handlers
  - **Returns**: 400 Bad Request with error message

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

**Definition**: DataInitializer is a class that automatically runs when the application starts and creates initial data (roles and default users).

**Purpose**: This class:
- Creates default roles (ADMIN, USER) if they don't exist
- Creates default users (admin/admin123, user/user123) if they don't exist
- Ensures application has initial data to work with
- Only runs in non-test environments

**Why We Need This**: When application starts for the first time, database is empty. This class populates it with essential data so you can immediately start using the application.

**What Each Part Does** (Detailed Explanation):

- **`@Component`**: Marks this as a Spring component - Spring will create and manage it
- **`@Profile("!test")`**: Only runs when NOT in test profile - prevents running during tests
- **`implements CommandLineRunner`**: Interface that runs code after application starts
- **`run(String... args)`**: Method that executes after Spring Boot fully starts
- **`initializeRoles()`**: Creates ADMIN and USER roles if they don't exist
- **`initializeUsers()`**: Creates default admin and user accounts if they don't exist

**Why This Order**: Roles must be created before users (users need roles to be assigned).

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

**Definition**: The main application class is the entry point of the Spring Boot application. It's the class that starts everything.

**Purpose**: This class:
- Starts the Spring Boot application
- Configures component scanning (where to find classes)
- Configures entity scanning (where to find entities)
- Configures repository scanning (where to find repositories)
- Initializes the application context

**Why We Need This**: This is the "ON" button for the application. Without this class, Spring Boot doesn't know where to start or what to scan.

**What Each Part Does** (Detailed Explanation):

- **`@SpringBootApplication`**:
  - **Definition**: Main Spring Boot annotation - combines multiple annotations
  - **Purpose**: Marks this as the main Spring Boot application
  - **What It Includes**:
    - `@Configuration`: This class contains configuration
    - `@EnableAutoConfiguration`: Enable Spring Boot auto-configuration
    - `@ComponentScan`: Scan for Spring components
  - **Why Needed**: Tells Spring Boot this is the main application class

- **`@ComponentScan(basePackages = {...})`**:
  - **Definition**: Tells Spring where to look for components (controllers, services, etc.)
  - **Purpose**: Without this, Spring might not find your classes
  - **Why Needed**: Our classes are in different packages - this tells Spring to scan them
  - **What It Scans**: config, security, controller, service, exception packages

- **`@EntityScan(basePackages = {...})`**:
  - **Definition**: Tells Spring where to find entity classes
  - **Purpose**: Hibernate needs to know where entities are
  - **Why Needed**: Our entities are in "model" package - this tells Hibernate where to look

- **`@EnableJpaRepositories(basePackages = {...})`**:
  - **Definition**: Tells Spring where to find repository interfaces
  - **Purpose**: Spring Data JPA needs to know where repositories are
  - **Why Needed**: Our repositories are in "repository" package - this tells Spring where to look

- **`public static void main(String[] args)`**:
  - **Definition**: Java entry point - where execution begins
  - **Purpose**: Starts the Spring Boot application
  - **What It Does**: Calls SpringApplication.run() which starts everything
  - **Why Static**: Java requirement - main method must be static

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

