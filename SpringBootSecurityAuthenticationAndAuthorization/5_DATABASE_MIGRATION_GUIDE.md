# 🗄️ Database Migration and Seeding Guide

This guide explains how to set up the database, run migrations, and seed initial data.

## 📖 What is This Document?

**Definition**: This document explains how to set up and manage the database for the application, including creating tables, running migrations, and seeding initial data.

**Purpose**: This document helps you:
- Understand database setup methods (different approaches)
- Set up the database correctly (step-by-step)
- Understand migrations (how database changes are managed)
- Seed initial data (create default users and roles)
- Troubleshoot database issues (common problems and solutions)

**Why This Document is Important**:
- **Database is Critical**: Without a database, the application cannot store or retrieve data
- **Multiple Methods**: There are different ways to set up the database - this guide explains all of them
- **Data Seeding**: You need initial data (admin user, roles) to start using the application
- **Troubleshooting**: Database issues are common - this guide helps solve them

**What is a Database?**
- **Definition**: A database is a structured collection of data stored electronically. Think of it as a digital filing cabinet.
- **Purpose**: Stores all application data (users, employees, managers) in an organized way
- **Why We Need It**: Data must persist (stay saved) even when the application restarts. Without a database, all data would be lost.
- **How Databases Work**:
  - Data is stored in tables (like spreadsheets)
  - Each table has rows (records) and columns (fields)
  - Tables can be related to each other (foreign keys)
  - SQL (Structured Query Language) is used to query data
- **Why MySQL**: 
  - Free and open-source
  - Widely used and well-supported
  - Works great with Spring Boot
  - Handles large amounts of data efficiently

**What is Database Migration?**
- **Definition**: Migration is the process of applying changes to the database structure (like adding tables or columns).
- **Purpose**: Keeps database structure in sync with code changes
- **Why We Need It**: As the application evolves, the database structure needs to change. Migrations track and apply these changes safely.
- **How Migrations Work**:
  1. You write SQL scripts to change database structure
  2. Migration tool (Flyway or Hibernate) tracks which migrations have been applied
  3. When application starts, it checks for new migrations
  4. Applies new migrations in order (V1, V2, V3, etc.)
  5. Records which migrations were applied
- **Why Version-Controlled**: Each migration has a version number - ensures migrations are applied in the correct order

**What is Data Seeding?**
- **Definition**: Seeding means inserting initial/starting data into the database (like default admin user).
- **Purpose**: Provides initial data needed for the application to work
- **Why We Need It**: Without seeded data, you can't login or use the application. It's like having a house but no keys to get in.
- **What Gets Seeded**:
  - Default roles (ADMIN, USER)
  - Default users (admin/admin123, user/user123)
  - Any other initial data needed for the application to function
- **When Seeding Happens**: Automatically when application starts (via DataInitializer)

**What You'll Learn**:
- Three methods to set up the database
- How to create tables automatically or manually
- How to seed initial data (users and roles)
- How to manage database changes over time
- How to troubleshoot common database issues

---

## 📋 Table of Contents

1. [Database Setup Methods](#database-setup-methods)
2. [Method 1: Hibernate Auto-DDL (Current Method)](#method-1-hibernate-auto-ddl-current-method)
3. [Method 2: Flyway Migrations (Optional)](#method-2-flyway-migrations-optional)
4. [Method 3: Manual SQL Scripts](#method-3-manual-sql-scripts)
5. [Data Seeding](#data-seeding)
6. [Database Schema](#database-schema)
7. [Troubleshooting](#troubleshooting)

---

## 🎯 Database Setup Methods

There are three ways to set up the database:

1. **Hibernate Auto-DDL** (Currently Active) - Automatic table creation
2. **Flyway Migrations** (Optional) - Version-controlled migrations
3. **Manual SQL Scripts** - Manual database setup

---

## 🔧 Method 1: Hibernate Auto-DDL (Current Method)

**Definition**: Hibernate Auto-DDL means Hibernate (the ORM tool) automatically creates and updates database tables based on your Java entity classes.

**Purpose**: This method:
- Automatically creates tables when application starts
- Updates table structure when you change entity classes
- Saves you from writing SQL manually
- Keeps database in sync with your code

**Why This is the Default**: It's the easiest method for development. You just write Java classes, and Hibernate handles the database. Perfect for learning and rapid development.

**Advantages**:
- ✅ Very easy to use (no SQL needed)
- ✅ Fast development (changes apply automatically)
- ✅ Less code to write (Hibernate does the work)

**Disadvantages**:
- ❌ Can lose data if you change table structure incorrectly
- ❌ Not ideal for production (can be risky)
- ❌ Less control over exact table structure

This is the **default and currently active** method. Hibernate automatically creates/updates tables based on your entity classes.

### How It Works

**Step-by-Step Process** (Detailed Explanation):

1. **Spring Boot starts**
   - **What**: Application begins loading
   - **Purpose**: Initializes all components
   - **Why First**: Everything else depends on Spring Boot being started
   - **What Happens**: Spring Boot framework initializes, reads configuration files, starts application context

2. **Hibernate reads entity classes (`User`, `Role`, `Employee`, `Manager`)**
   - **What**: Hibernate scans your Java classes marked with `@Entity`
   - **Purpose**: Understands what tables need to exist
   - **How**: Reads annotations like `@Table`, `@Column`, `@Id` to understand structure
   - **Why**: These annotations tell Hibernate how to create tables
   - **What Hibernate Reads**:
     - `@Entity`: Knows this class is a database entity
     - `@Table(name = "...")`: Knows the table name
     - `@Id`, `@GeneratedValue`: Knows the primary key
     - `@Column`: Knows column names, types, constraints
     - `@ManyToMany`, `@OneToMany`, etc.: Knows relationships
   - **Result**: Hibernate builds an internal model of what the database should look like

3. **Hibernate compares with existing database**
   - **What**: Hibernate connects to database and checks current structure
   - **Purpose**: Determines what changes need to be made
   - **How**: 
     - Reads existing table structures from database
     - Compares with entity class definitions
     - Identifies differences (new tables, new columns, changed columns)
   - **Why**: Only applies necessary changes, doesn't recreate everything

4. **Hibernate creates/updates database tables automatically**
   - **What**: Hibernate generates and executes SQL CREATE/ALTER statements
   - **Purpose**: Makes database structure match your entity classes
   - **How**: 
     - Generates SQL DDL (Data Definition Language) statements
     - Executes CREATE TABLE for new tables
     - Executes ALTER TABLE for new/modified columns
     - Does NOT drop existing columns (safe update mode)
   - **Why**: Ensures database is always in sync with code
   - **What SQL Gets Generated**:
     - `CREATE TABLE IF NOT EXISTS users (...)`
     - `ALTER TABLE users ADD COLUMN ...` (if new field added)
     - `CREATE TABLE user_roles (...)` (for join tables)

5. **`DataInitializer` runs and seeds initial data**
   - **What**: Custom code that runs after tables are created
   - **Purpose**: Inserts default data (admin user, roles)
   - **How**: 
     - Implements CommandLineRunner interface
     - Runs after Spring Boot fully starts
     - Checks if roles exist, creates if missing
     - Checks if users exist, creates if missing
   - **Why**: Provides initial data needed to use the application
   - **What Gets Created**:
     - Roles: ADMIN, USER
     - Users: admin (password: admin123), user (password: user123)

### Configuration

**File:** `src/main/resources/application.properties`

```properties
# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.generate-ddl=true

# Flyway - DISABLED
spring.flyway.enabled=false
```

### Step-by-Step Process

#### Step 1: Create MySQL Database

1. Open MySQL command line or MySQL Workbench
2. Connect as root user
3. Run:

```sql
CREATE DATABASE IF NOT EXISTS SpringSecurity 
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE SpringSecurity;
```

**Or use the provided script:**

```bash
mysql -u root -p < create_database.sql
```

#### Step 2: Configure Database Connection

**File:** `src/main/resources/application.properties`

Update these values:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/SpringSecurity?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD_HERE
```

#### Step 3: Run the Application

```bash
mvn spring-boot:run
```

#### Step 4: Verify Tables Created

1. Check console logs for SQL statements
2. Or connect to MySQL:

```sql
USE SpringSecurity;
SHOW TABLES;
```

**Expected Tables:**
- `users`
- `roles`
- `user_roles`
- `employees`
- `managers`

#### Step 5: Verify Data Seeded

```sql
-- Check roles
SELECT * FROM roles;

-- Check users
SELECT * FROM users;

-- Check user roles
SELECT u.username, r.name as role 
FROM users u 
JOIN user_roles ur ON u.id = ur.user_id 
JOIN roles r ON ur.role_id = r.id;
```

**Expected Output:**
- Roles: `ADMIN`, `USER`
- Users: `admin` (password: `admin123`), `user` (password: `user123`)

---

## 🚀 Method 2: Flyway Migrations (Optional)

Flyway is a database migration tool that manages version-controlled SQL scripts.

### Enable Flyway

**File:** `src/main/resources/application.properties`

```properties
# Enable Flyway
spring.flyway.enabled=true

# Optional: Disable Hibernate DDL
spring.jpa.hibernate.ddl-auto=none
```

### Migration Files Location

```
src/main/resources/db/migration/
├── V1__init.sql          # Creates all tables
└── V2__seed_roles_users.sql  # Seeds initial data
```

### Migration File Naming Convention

- `V{version}__{description}.sql`
- Example: `V1__init.sql`, `V2__seed_roles_users.sql`
- Versions must be sequential (1, 2, 3, ...)

### Step-by-Step Process

#### Step 1: Create Database

```sql
CREATE DATABASE IF NOT EXISTS SpringSecurity 
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### Step 2: Enable Flyway

Update `application.properties`:

```properties
spring.flyway.enabled=true
spring.jpa.hibernate.ddl-auto=none
```

#### Step 3: Review Migration Files

**File:** `src/main/resources/db/migration/V1__init.sql`

This file creates all tables:
- `roles`
- `users`
- `user_roles`
- `managers`
- `employees`

**File:** `src/main/resources/db/migration/V2__seed_roles_users.sql`

This file seeds initial data:
- Creates ADMIN and USER roles
- Creates admin and user accounts

**Note:** Password hashes in SQL are examples. `DataInitializer.java` generates them dynamically.

#### Step 4: Run the Application

```bash
mvn spring-boot:run
```

#### Step 5: Verify Migrations

Check Flyway logs in console:

```
Flyway Community Edition 9.x.x by Redgate
Database: jdbc:mysql://localhost:3306/SpringSecurity
Successfully validated 2 migrations
Current version of schema `SpringSecurity`: << Empty Schema >>
Migrating schema `SpringSecurity` to version "1 - init"
Migrating schema `SpringSecurity` to version "2 - seed roles users"
Successfully applied 2 migrations
```

#### Step 6: Check Flyway Schema History

```sql
SELECT * FROM flyway_schema_history;
```

This table tracks all applied migrations.

### Creating New Migration

To add a new migration:

1. Create new file: `V3__add_new_column.sql`
2. Add SQL:

```sql
ALTER TABLE employees ADD COLUMN salary DECIMAL(10,2);
```

3. Restart application
4. Flyway will automatically apply the migration

---

## 📝 Method 3: Manual SQL Scripts

You can also set up the database manually using SQL scripts.

### Step 1: Create Database

```sql
CREATE DATABASE IF NOT EXISTS SpringSecurity 
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE SpringSecurity;
```

### Step 2: Run Table Creation Script

**File:** `src/main/resources/db/migration/V1__init.sql`

Copy and run the SQL:

```sql
-- Create roles table
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
);

-- Create user_roles join table
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Create managers table
CREATE TABLE IF NOT EXISTS managers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    designation VARCHAR(255),
    experience INT,
    city VARCHAR(255)
);

-- Create employees table
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(255),
    position VARCHAR(255),
    manager_id BIGINT,
    FOREIGN KEY (manager_id) REFERENCES managers(id) ON DELETE SET NULL
);
```

### Step 3: Verify Tables

```sql
SHOW TABLES;
```

**Expected:**
- roles
- users
- user_roles
- managers
- employees

### Step 4: Disable Hibernate DDL

**File:** `src/main/resources/application.properties`

```properties
spring.jpa.hibernate.ddl-auto=none
```

This prevents Hibernate from modifying your manually created tables.

---

## 🌱 Data Seeding

Data seeding means inserting initial/starting data into the database.

### Current Method: DataInitializer.java

**File:** `src/main/java/config/DataInitializer.java`

This class automatically seeds data when the application starts.

#### How It Works

1. Implements `CommandLineRunner`
2. Runs after Spring Boot starts
3. Checks if roles/users exist
4. Creates them if they don't exist
5. Only runs in non-test profiles (`@Profile("!test")`)

#### What It Seeds

**Roles:**
- `ADMIN` - Administrator role
- `USER` - Regular user role

**Users:**
- Username: `admin`
- Email: `admin@example.com`
- Password: `admin123` (BCrypt hashed)
- Role: `ADMIN`

- Username: `user`
- Email: `user@example.com`
- Password: `user123` (BCrypt hashed)
- Role: `USER`

#### Code Structure

```java
@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {
    
    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeUsers();
    }
    
    private void initializeRoles() {
        if (roleRepository.count() == 0) {
            // Create ADMIN and USER roles
        }
    }
    
    private void initializeUsers() {
        if (userRepository.count() == 0) {
            // Create admin and user accounts
        }
    }
}
```

### Alternative: SQL Seeding Script

**File:** `src/main/resources/db/migration/V2__seed_roles_users.sql`

This file contains SQL for seeding data (used when Flyway is enabled).

**Note:** Password hashes need to be generated using BCrypt. The `DataInitializer` does this automatically.

### Manual Seeding

You can also seed data manually:

#### Step 1: Create Roles

```sql
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');
```

#### Step 2: Generate Password Hash

Use BCrypt to hash passwords. In Java:

```java
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hashedPassword = encoder.encode("admin123");
System.out.println(hashedPassword);
```

#### Step 3: Create Users

```sql
-- Get role IDs first
SET @admin_role_id = (SELECT id FROM roles WHERE name = 'ADMIN');
SET @user_role_id = (SELECT id FROM roles WHERE name = 'USER');

-- Create admin user (replace password hash with actual BCrypt hash)
INSERT INTO users (username, email, password, enabled) 
VALUES ('admin', 'admin@example.com', '$2a$10$...', true);

-- Create regular user
INSERT INTO users (username, email, password, enabled) 
VALUES ('user', 'user@example.com', '$2a$10$...', true);

-- Assign roles
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, @admin_role_id FROM users u WHERE u.username = 'admin';

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, @user_role_id FROM users u WHERE u.username = 'user';
```

---

## 📊 Database Schema

### Complete Schema Diagram

```
┌─────────────┐
│   roles     │
├─────────────┤
│ id (PK)     │
│ name (UK)   │
└─────────────┘
       │
       │ (Many-to-Many)
       │
┌─────────────┐      ┌──────────────┐
│   users     │      │  user_roles  │
├─────────────┤      ├──────────────┤
│ id (PK)     │◄─────┤ user_id (FK) │
│ username(UK)│      │ role_id (FK) │
│ email (UK)  │      └──────────────┘
│ password    │
│ enabled     │
└─────────────┘

┌─────────────┐
│  managers   │
├─────────────┤
│ id (PK)     │
│ name        │
│ designation │
│ experience  │
│ city        │
└─────────────┘
       │
       │ (One-to-Many)
       │
┌─────────────┐
│  employees  │
├─────────────┤
│ id (PK)     │
│ first_name  │
│ last_name   │
│ email (UK)  │
│ phone       │
│ position    │
│ manager_id  │──────┘ (FK, nullable)
└─────────────┘
```

### Table Details

#### roles

| Column | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Role ID |
| name | VARCHAR(50) | NOT NULL, UNIQUE | Role name (ADMIN, USER) |

#### users

| Column | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | User ID |
| username | VARCHAR(255) | NOT NULL, UNIQUE | Username |
| email | VARCHAR(255) | NOT NULL, UNIQUE | Email address |
| password | VARCHAR(255) | NOT NULL | BCrypt hashed password |
| enabled | BOOLEAN | DEFAULT TRUE | Account enabled status |

#### user_roles

| Column | Type | Constraints | Description |
|-------|------|-------------|-------------|
| user_id | BIGINT | PRIMARY KEY, FK → users.id | User ID |
| role_id | BIGINT | PRIMARY KEY, FK → roles.id | Role ID |

#### managers

| Column | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Manager ID |
| name | VARCHAR(255) | NOT NULL | Manager name |
| designation | VARCHAR(255) | | Job designation |
| experience | INT | | Years of experience |
| city | VARCHAR(255) | | City location |

#### employees

| Column | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Employee ID |
| first_name | VARCHAR(255) | | First name |
| last_name | VARCHAR(255) | | Last name |
| email | VARCHAR(255) | NOT NULL, UNIQUE | Email address |
| phone | VARCHAR(255) | | Phone number |
| position | VARCHAR(255) | | Job position |
| manager_id | BIGINT | FK → managers.id, NULLABLE | Assigned manager |

---

## 🔄 Migration Workflow

### Adding New Table

#### Using Hibernate (Current Method)

1. Create new entity class:

```java
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    // ... other fields
}
```

2. Restart application
3. Hibernate creates table automatically

#### Using Flyway

1. Create migration file: `V3__create_departments.sql`

```sql
CREATE TABLE departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
```

2. Restart application
3. Flyway applies migration

### Adding New Column

#### Using Hibernate

1. Add field to entity:

```java
@Column(name = "salary")
private BigDecimal salary;
```

2. Restart application
3. Hibernate adds column automatically

#### Using Flyway

1. Create migration: `V4__add_salary_to_employees.sql`

```sql
ALTER TABLE employees ADD COLUMN salary DECIMAL(10,2);
```

2. Restart application

### Modifying Column

#### Using Hibernate

1. Modify entity field
2. Hibernate may update column (depending on `ddl-auto` setting)

**Note:** Hibernate may not handle all column modifications safely. Use Flyway for complex changes.

#### Using Flyway

1. Create migration: `V5__modify_email_column.sql`

```sql
ALTER TABLE employees MODIFY COLUMN email VARCHAR(500);
```

2. Restart application

---

## 🛠️ Troubleshooting

### Issue 1: Tables Not Created

**Problem:** Tables don't exist after starting application

**Solutions:**
1. Check `spring.jpa.hibernate.ddl-auto=update` is set
2. Check database connection is working
3. Check console for SQL errors
4. Verify database exists: `SHOW DATABASES;`

### Issue 2: Data Not Seeded

**Problem:** Default users/roles not created

**Solutions:**
1. Check `DataInitializer` is running (check logs)
2. Verify `@Profile("!test")` - won't run in test profile
3. Check if data already exists (DataInitializer skips if exists)
4. Manually check database:

```sql
SELECT * FROM roles;
SELECT * FROM users;
```

### Issue 3: Flyway Migration Fails

**Problem:** Flyway migration errors

**Solutions:**
1. Check migration file syntax
2. Check version numbers are sequential
3. Check if migration was partially applied
4. Check `flyway_schema_history` table:

```sql
SELECT * FROM flyway_schema_history;
```

5. If needed, manually fix and mark as resolved:

```sql
INSERT INTO flyway_schema_history 
(installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success)
VALUES (3, '3', 'fix migration', 'SQL', 'V3__fix.sql', 0, 'root', NOW(), 0, 1);
```

### Issue 4: Foreign Key Constraint Errors

**Problem:** Cannot delete user/role due to foreign key

**Solutions:**
1. Delete from `user_roles` first:

```sql
DELETE FROM user_roles WHERE user_id = 1;
DELETE FROM users WHERE id = 1;
```

2. Or use CASCADE (already configured in schema)

### Issue 5: Password Hash Issues

**Problem:** Cannot login with default passwords

**Solutions:**
1. Verify `DataInitializer` ran and created users
2. Check password encoder matches (BCrypt)
3. Reset password manually:

```java
// In a test or utility class
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hash = encoder.encode("newpassword123");
// Update in database
```

---

## 📝 Best Practices

### 1. Use Hibernate for Development

- Fast iteration
- Automatic schema updates
- Good for prototyping

### 2. Use Flyway for Production

- Version-controlled migrations
- Reproducible deployments
- Better for team collaboration

### 3. Always Backup Before Migrations

```bash
mysqldump -u root -p SpringSecurity > backup.sql
```

### 4. Test Migrations on Staging First

- Never run untested migrations on production
- Test rollback procedures

### 5. Keep Migration Files Small

- One logical change per migration
- Easier to debug and rollback

---

## ✅ Summary

### Current Setup (Default)

- **Method:** Hibernate Auto-DDL
- **Tables:** Created automatically from entities
- **Data:** Seeded by `DataInitializer.java`
- **Migrations:** Flyway disabled

### To Switch to Flyway

1. Set `spring.flyway.enabled=true`
2. Set `spring.jpa.hibernate.ddl-auto=none`
3. Migration files in `src/main/resources/db/migration/`
4. Restart application

### Default Data

**Roles:**
- ADMIN
- USER

**Users:**
- admin / admin123 (ADMIN role)
- user / user123 (USER role)

---

**Happy Database Management! 🎉**

