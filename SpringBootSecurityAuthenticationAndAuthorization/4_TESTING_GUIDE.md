# 🧪 Complete Testing Guide - All APIs

This guide explains how to test all APIs in the project using different methods.

## 📖 What is This Document?

**Definition**: This document is a comprehensive testing guide that explains how to verify that all APIs work correctly using different testing methods.

**Purpose**: This document helps you:
- Understand why testing is important (catch bugs before production)
- Learn different testing methods (automated vs manual)
- Run automated tests (JUnit tests)
- Test APIs manually (using cURL or Postman)
- Understand test structure (how tests are organized)
- Debug test failures (what to do when tests fail)

**Why Testing is Important**:
- **Prevents Bugs**: Catches errors before users encounter them
- **Ensures Quality**: Verifies that code works as expected
- **Saves Time**: Automated tests run quickly and repeatedly
- **Builds Confidence**: Knowing tests pass gives confidence in the code
- **Documentation**: Tests serve as examples of how to use the code

**What is Testing?**
- **Definition**: Testing is the process of verifying that your code works correctly by running it and checking the results.
- **Purpose**: To find and fix bugs before the application is used by real users.
- **Why We Test**: Without testing, we don't know if our code works. It's like building a car without test-driving it - you might discover problems too late.
- **How Testing Works**:
  1. Write test code that calls your application code
  2. Provide test data (inputs)
  3. Execute the code
  4. Check the results (outputs) match expected values
  5. If results match, test passes; if not, test fails
- **Benefits of Testing**:
  - **Catches Bugs Early**: Find problems before users encounter them
  - **Prevents Regressions**: Ensures new changes don't break existing features
  - **Documents Behavior**: Tests serve as examples of how code should work
  - **Builds Confidence**: Knowing tests pass gives confidence in the code
  - **Enables Refactoring**: Can change code safely if tests pass

**Types of Testing in This Project**:
1. **Unit Tests**: Test individual components (like controllers) in isolation
2. **Integration Tests**: Test how different components work together
3. **Manual Tests**: Test APIs manually using tools like Postman or cURL

**What You'll Learn**:
- How to run automated tests
- How to write test cases
- How to test APIs manually
- How to verify authentication and authorization
- How to debug test failures

---

## 📋 Table of Contents

1. [Testing Methods](#testing-methods)
2. [Running Tests](#running-tests)
3. [Test Structure](#test-structure)
4. [Testing Authentication APIs](#testing-authentication-apis)
5. [Testing Employee APIs](#testing-employee-apis)
6. [Testing Manager APIs](#testing-manager-apis)
7. [Manual Testing with cURL](#manual-testing-with-curl)
8. [Manual Testing with Postman](#manual-testing-with-postman)
9. [Test Examples](#test-examples)

---

## 🎯 Testing Methods

**Definition**: Testing methods are different ways to verify that your APIs work correctly. Each method has its own advantages.

**Purpose**: Different testing methods serve different needs:
- **Automated Tests**: Run quickly and repeatedly, catch regressions
- **Manual Tests**: Give you hands-on experience, test real scenarios
- **cURL**: Quick command-line testing, good for scripts
- **Postman**: Visual testing, good for exploring APIs

**Why Multiple Methods**: Each method is useful in different situations:
- Use automated tests during development (catch bugs early)
- Use manual tests to verify user experience (how it feels to use)
- Use cURL for quick checks or automation scripts
- Use Postman for detailed exploration and documentation

There are three main ways to test APIs:

1. **Automated Unit Tests** - JUnit tests in the project
   - **Definition**: Pre-written test code that runs automatically
   - **Purpose**: Tests run every time you build, catching bugs immediately
   - **Why Use**: Saves time, ensures consistency, prevents regressions
   - **When to Use**: Always - these should run before every commit

2. **Manual Testing with cURL** - Command line testing
   - **Definition**: Using command-line tool to send HTTP requests
   - **Purpose**: Quick way to test APIs without opening a GUI
   - **Why Use**: Fast, scriptable, works in any terminal
   - **When to Use**: Quick checks, automation scripts, CI/CD pipelines

3. **Manual Testing with Postman** - GUI-based testing
   - **Definition**: Using graphical tool to send HTTP requests
   - **Purpose**: Visual interface makes testing easier and more intuitive
   - **Why Use**: Easy to use, saves requests, visual feedback
   - **When to Use**: Exploring APIs, sharing with team, documentation

---

## 🚀 Running Tests

### Method 1: Command Line (Terminal/Console)

#### Run All Tests

```bash
cd /path/to/SpringBootSecurityAuthenticationAndAuthorization
mvn test
```

#### Run Specific Test Class

```bash
# Run only AuthController tests
mvn test -Dtest=AuthControllerTest

# Run only EmployeeController tests
mvn test -Dtest=EmployeeControllerTest

# Run only ManagerController tests
mvn test -Dtest=ManagerControllerTest
```

#### Run with Test Profile

```bash
mvn test -Dspring.profiles.active=test
```

#### Run with Verbose Output

```bash
mvn test -X
```

---

### Method 2: Spring Tool Suite (STS)

#### Run All Tests

1. Right-click on project
2. Select **"Run As"** → **"Maven Test"**
3. Or use shortcut: `Alt + Shift + X, T`

#### Run Specific Test Class

1. Right-click on test class (e.g., `AuthControllerTest.java`)
2. Select **"Run As"** → **"JUnit Test"**
3. Or use shortcut: `Alt + Shift + X, T`

#### Run Individual Test Method

1. Right-click on specific test method
2. Select **"Run As"** → **"JUnit Test"**

---

### Method 3: IntelliJ IDEA

#### Run All Tests

1. Right-click on `src/test/java` folder
2. Select **"Run 'All Tests'"**
3. Or use shortcut: `Ctrl + Shift + F10` (Windows/Linux) or `Cmd + Shift + R` (Mac)

#### Run Specific Test Class

1. Right-click on test class
2. Select **"Run 'TestClassName'"**
3. Or click green arrow next to class name

#### Run Individual Test Method

1. Click green arrow next to test method
2. Or right-click method → **"Run 'methodName()'"**

---

## 📁 Test Structure

### Test File Locations

```
src/test/java/
├── controller/
│   ├── AuthControllerTest.java
│   ├── EmployeeControllerTest.java
│   └── ManagerControllerTest.java
└── com/SpringBootSecurity/.../
    └── SpringBootSecurityAuthenticationAndAuthorizationApplicationTests.java
```

### Test Configuration

**File:** `src/test/resources/application-test.properties`

- Uses H2 in-memory database (no MySQL needed for tests)
- Disables Flyway migrations
- Uses test JWT secret
- Different logging levels

---

## 🔐 Testing Authentication APIs

### Test File: `AuthControllerTest.java`

### Test Cases Covered

1. ✅ **Signup Success** - Creates new user successfully
2. ✅ **Signup with Admin Role** - Fails when non-admin tries to create ADMIN user
3. ✅ **Signup Validation** - Tests blank username, invalid email, blank password
4. ✅ **Signup Duplicate** - Tests duplicate username and email
5. ✅ **Login with Username** - Login using username
6. ✅ **Login with Email** - Login using email
7. ✅ **Login Wrong Password** - Fails with wrong password
8. ✅ **Login Non-existent User** - Fails with non-existent username
9. ✅ **Login Validation** - Tests blank username and password

### Example Test: Signup Success

```java
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
```

### Example Test: Login Success

```java
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
```

---

## 👥 Testing Employee APIs

### Test File: `EmployeeControllerTest.java`

### Test Cases Covered

1. ✅ **Create Employee as ADMIN** - Success
2. ✅ **Create Employee as USER** - Success
3. ✅ **Create Employee Unauthorized** - Fails without token
4. ✅ **Create Employee Validation** - Tests blank fields, invalid email
5. ✅ **Get Employee by ID** - As ADMIN and USER
6. ✅ **Get Employee Not Found** - Returns 404
7. ✅ **Get All Employees** - As ADMIN and USER
8. ✅ **Update Employee** - As ADMIN and USER
9. ✅ **Update Employee Not Found** - Returns 404
10. ✅ **Delete Employee as ADMIN** - Success
11. ✅ **Delete Employee as USER** - Returns 403 Forbidden
12. ✅ **Delete Employee Not Found** - Returns 404

### Example Test: Create Employee

```java
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
```

### Example Test: Delete Employee (Authorization Test)

```java
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
```

---

## 👔 Testing Manager APIs

### Test File: `ManagerControllerTest.java`

### Test Cases Covered

1. ✅ **Create Manager as ADMIN** - Success
2. ✅ **Create Manager as USER** - Returns 403 Forbidden
3. ✅ **Create Manager Unauthorized** - Fails without token
4. ✅ **Create Manager Validation** - Tests blank name
5. ✅ **Get Manager by ID** - As ADMIN and USER
6. ✅ **Get Manager Not Found** - Returns 404
7. ✅ **Get All Managers as ADMIN** - Success
8. ✅ **Get All Managers as USER** - Returns 403 Forbidden
9. ✅ **Update Manager as ADMIN** - Success
10. ✅ **Update Manager as USER** - Returns 403 Forbidden
11. ✅ **Update Manager Not Found** - Returns 404
12. ✅ **Delete Manager as ADMIN** - Success
13. ✅ **Delete Manager as USER** - Returns 403 Forbidden
14. ✅ **Delete Manager Not Found** - Returns 404

### Example Test: Create Manager (Admin Only)

```java
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
```

### Example Test: Authorization Check (USER Cannot Access)

```java
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
```

---

## 🔧 Manual Testing with cURL

### Step 1: Start the Application

```bash
mvn spring-boot:run
```

Wait for: `Started SpringBootSecurityAuthenticationAndAuthorizationApplication`

### Step 2: Test Signup

```bash
curl -X POST http://localhost:8095/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "testuser@example.com",
    "password": "SecurePass123!",
    "role": "USER"
  }'
```

**Expected:** `201 Created` (empty response)

### Step 3: Test Login and Save Token

```bash
# Login
RESPONSE=$(curl -s -X POST http://localhost:8095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "testuser",
    "password": "SecurePass123!"
  }')

# Extract token (requires jq: brew install jq)
TOKEN=$(echo $RESPONSE | jq -r '.token')

# Or manually set
# TOKEN="your-token-here"

echo "Token: $TOKEN"
```

### Step 4: Test Protected Endpoint

```bash
# Get all employees
curl -X GET http://localhost:8095/api/employees \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

### Step 5: Test Create Employee

```bash
curl -X POST http://localhost:8095/api/employees \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "555-1234",
    "position": "Developer",
    "managerId": null
  }'
```

### Step 6: Test Error Cases

#### Test Unauthorized (No Token)

```bash
curl -X GET http://localhost:8095/api/employees \
  -H "Content-Type: application/json"
```

**Expected:** `401 Unauthorized`

#### Test Invalid Token

```bash
curl -X GET http://localhost:8095/api/employees \
  -H "Authorization: Bearer invalid-token-here" \
  -H "Content-Type: application/json"
```

**Expected:** `401 Unauthorized`

#### Test Wrong Role (USER trying ADMIN endpoint)

```bash
# Login as USER (not admin)
USER_TOKEN=$(curl -s -X POST http://localhost:8095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usernameOrEmail":"user","password":"user123"}' \
  | jq -r '.token')

# Try to access ADMIN-only endpoint
curl -X GET http://localhost:8095/api/managers \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json"
```

**Expected:** `403 Forbidden`

---

## 📮 Manual Testing with Postman

### Step 1: Setup Collection

1. Open Postman
2. Create new collection: **"Spring Boot Security API"**
3. Add variables:
   - `base_url`: `http://localhost:8095`
   - `token`: (leave empty)

### Step 2: Test Signup

1. Create request: **POST** `{{base_url}}/api/auth/signup`
2. Body → raw → JSON:
```json
{
  "username": "testuser",
  "email": "testuser@example.com",
  "password": "SecurePass123!",
  "role": "USER"
}
```
3. Click **"Send"**
4. **Expected:** `201 Created`

### Step 3: Test Login and Save Token

1. Create request: **POST** `{{base_url}}/api/auth/login`
2. Body → raw → JSON:
```json
{
  "usernameOrEmail": "testuser",
  "password": "SecurePass123!"
}
```
3. Go to **"Tests"** tab
4. Add script:
```javascript
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.collectionVariables.set("token", jsonData.token);
    console.log("Token saved: " + jsonData.token);
}
```
5. Click **"Send"**
6. Check console to see token saved

### Step 4: Test Protected Endpoint

1. Create request: **GET** `{{base_url}}/api/employees`
2. Authorization → Type: **"Bearer Token"**
3. Token: `{{token}}`
4. Click **"Send"**
5. **Expected:** `200 OK` with employee list

### Step 5: Test Error Cases

#### Test Unauthorized

1. Create request: **GET** `{{base_url}}/api/employees`
2. **Don't** add Authorization header
3. Click **"Send"**
4. **Expected:** `401 Unauthorized`

#### Test Wrong Role

1. Login as USER (not admin)
2. Try to access: **GET** `{{base_url}}/api/managers`
3. **Expected:** `403 Forbidden`

---

## 📝 Test Examples

### Complete Test Workflow

#### 1. Test Signup Flow

```bash
# Signup
curl -X POST http://localhost:8095/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "newuser@example.com",
    "password": "SecurePass123!",
    "role": "USER"
  }'

# Expected: 201 Created
```

#### 2. Test Login Flow

```bash
# Login
curl -X POST http://localhost:8095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "newuser",
    "password": "SecurePass123!"
  }'

# Expected: 200 OK with token
```

#### 3. Test Employee CRUD

```bash
# Save token
TOKEN="your-token-here"

# Create employee
curl -X POST http://localhost:8095/api/employees \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "555-1234",
    "position": "Developer"
  }'

# Get all employees
curl -X GET http://localhost:8095/api/employees \
  -H "Authorization: Bearer $TOKEN"

# Get employee by ID (replace 1 with actual ID)
curl -X GET http://localhost:8095/api/employees/1 \
  -H "Authorization: Bearer $TOKEN"

# Update employee
curl -X PUT http://localhost:8095/api/employees/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John Updated",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "555-5678",
    "position": "Senior Developer"
  }'

# Delete employee (ADMIN only)
curl -X DELETE http://localhost:8095/api/employees/1 \
  -H "Authorization: Bearer $TOKEN"
```

#### 4. Test Manager CRUD (Admin Only)

```bash
# Login as admin
ADMIN_TOKEN=$(curl -s -X POST http://localhost:8095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usernameOrEmail":"admin","password":"admin123"}' \
  | jq -r '.token')

# Create manager
curl -X POST http://localhost:8095/api/managers \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Bob Johnson",
    "designation": "Engineering Manager",
    "experience": 10,
    "city": "New York"
  }'

# Get all managers
curl -X GET http://localhost:8095/api/managers \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

## ✅ Testing Checklist

### Authentication Tests

- [ ] Signup with valid data - Success
- [ ] Signup with duplicate username - Error
- [ ] Signup with duplicate email - Error
- [ ] Signup with invalid password - Validation error
- [ ] Signup with invalid email - Validation error
- [ ] Login with valid credentials - Success
- [ ] Login with wrong password - Error
- [ ] Login with non-existent user - Error

### Employee Tests

- [ ] Get all employees (USER) - Success
- [ ] Get all employees (ADMIN) - Success
- [ ] Get all employees (No token) - Unauthorized
- [ ] Create employee (USER) - Success
- [ ] Create employee (ADMIN) - Success
- [ ] Create employee (No token) - Unauthorized
- [ ] Get employee by ID - Success
- [ ] Get employee by ID (Not found) - 404
- [ ] Update employee (USER) - Success
- [ ] Update employee (ADMIN) - Success
- [ ] Delete employee (ADMIN) - Success
- [ ] Delete employee (USER) - 403 Forbidden

### Manager Tests

- [ ] Get all managers (ADMIN) - Success
- [ ] Get all managers (USER) - 403 Forbidden
- [ ] Create manager (ADMIN) - Success
- [ ] Create manager (USER) - 403 Forbidden
- [ ] Get manager by ID (USER) - Success
- [ ] Get manager by ID (ADMIN) - Success
- [ ] Update manager (ADMIN) - Success
- [ ] Update manager (USER) - 403 Forbidden
- [ ] Delete manager (ADMIN) - Success
- [ ] Delete manager (USER) - 403 Forbidden

---

## 🐛 Debugging Tests

### View Test Output

Add `.andDo(print())` to see request/response details:

```java
mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andDo(print())  // This prints request/response
        .andExpect(status().isCreated());
```

### Check Database State

Tests use H2 in-memory database. To check data:

```java
// In test method
List<Employee> employees = employeeRepository.findAll();
System.out.println("Employees: " + employees.size());
```

### Common Test Issues

1. **Token Not Working**
   - Check token is generated correctly
   - Verify token format: `Bearer <token>`
   - Check token hasn't expired

2. **Database Issues**
   - Tests use `@Transactional` - data is rolled back after each test
   - Each test should clean up in `@BeforeEach`

3. **Authorization Failures**
   - Verify user has correct role
   - Check `@PreAuthorize` annotations match test expectations

---

## 📊 Test Coverage

To check test coverage:

```bash
# Add JaCoCo plugin to pom.xml, then:
mvn clean test jacoco:report
```

Coverage report will be in: `target/site/jacoco/index.html`

---

## 🎯 Summary

This project includes comprehensive tests for:

- ✅ Authentication (Signup/Login)
- ✅ Employee CRUD operations
- ✅ Manager CRUD operations
- ✅ Authorization (Role-based access)
- ✅ Validation errors
- ✅ Error handling (404, 401, 403)

**Test Files:**
- `AuthControllerTest.java` - 11 test cases
- `EmployeeControllerTest.java` - 15+ test cases
- `ManagerControllerTest.java` - 14+ test cases

**Total:** 40+ automated test cases

---

**Happy Testing! 🎉**

