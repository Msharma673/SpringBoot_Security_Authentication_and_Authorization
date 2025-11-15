# 🌐 Complete API Documentation - cURL and Postman Guide

This guide provides complete API documentation with both cURL commands and Postman setup instructions.

## 📖 What is This Document?

**Definition**: This document is a complete reference guide for all APIs (Application Programming Interfaces) in the project. It shows you how to call each API endpoint.

**Purpose**: This document helps you:
- Understand what APIs are available (what you can do)
- Learn how to call each API (step-by-step instructions)
- Test APIs using cURL (command line) or Postman (graphical tool)
- Understand request and response formats (what to send and what to expect)
- Handle errors correctly (what to do when things go wrong)

**Why This Document is Important**:
- **For Testing**: You need to test APIs to verify they work
- **For Integration**: Other applications need to know how to call your APIs
- **For Documentation**: Serves as official API documentation
- **For Learning**: Shows real examples of how REST APIs work

**What is an API?**
- **Definition**: API stands for Application Programming Interface. It's a way for different applications to communicate with each other.
- **Purpose**: APIs allow clients (like web browsers, mobile apps, or other services) to request data or perform actions on the server.
- **Why We Need APIs**: Without APIs, there's no way for external systems to interact with our application. APIs are like a menu in a restaurant - they show what's available and how to order it.
- **How APIs Work**:
  1. Client sends HTTP request (GET, POST, PUT, DELETE) to a URL (endpoint)
  2. Server receives request and processes it
  3. Server performs the requested operation (create, read, update, delete)
  4. Server sends HTTP response back to client (with data or status)
- **REST API**: Our application uses REST (Representational State Transfer) - a standard way to design APIs
- **HTTP Methods**:
  - **GET**: Retrieve data (read)
  - **POST**: Create new data
  - **PUT**: Update existing data
  - **DELETE**: Remove data

**What You'll Learn**:
- How to authenticate (login and get token)
- How to call protected endpoints (with authentication)
- How to handle different user roles (USER vs ADMIN)
- How to test APIs manually

---

## 📋 Table of Contents

1. [Base Configuration](#base-configuration)
2. [API Execution Order](#api-execution-order)
3. [Authentication APIs](#authentication-apis)
4. [Employee APIs](#employee-apis)
5. [Manager APIs](#manager-apis)
6. [Postman Setup](#postman-setup)
7. [Common Errors](#common-errors)

---

## 🔧 Base Configuration

**Base URL:** `http://localhost:8095`  
**Port:** `8095`  
**Content-Type:** `application/json`  
**Authentication:** JWT Bearer Token

---

## 📝 API Execution Order

**IMPORTANT:** Follow this order when testing APIs:

1. ✅ **Signup** - Create a new user account (No auth required)
2. ✅ **Login** - Get JWT token (No auth required)
3. ✅ **Use Protected APIs** - Use the token from step 2

---

## 🔐 Authentication APIs

**Definition**: Authentication APIs are endpoints that handle user registration (signup) and login. They are the entry point for users to access the system.

**Purpose**: These APIs allow:
- New users to create accounts (signup)
- Existing users to login and get access tokens (login)
- The system to verify user identity

**Why They're Important**: Without authentication, anyone could access your APIs. Authentication ensures only registered users can use the system. It's like having a door with a lock - only people with keys (valid credentials) can enter.

**How Authentication Works**:
1. User signs up (creates account with username, email, password)
2. User logs in (provides username/email and password)
3. System validates credentials (checks if password is correct)
4. System generates JWT token (a secure token that proves user is authenticated)
5. User uses this token for all future requests (sends token in Authorization header)

### 1. Signup (Create User Account)

**Definition**: Signup is the process of creating a new user account in the system. It's like registering for a new account on any website.

**Endpoint:** `POST /api/auth/signup`  
**Authentication:** ❌ Not Required  
**Description:** Creates a new user account

**Purpose**: This endpoint allows new users to:
- Create an account with username, email, and password
- Choose their role (USER or ADMIN - anyone can create any role)
- Start using the application

**Why Signup is Public**: Signup must be public (no authentication required) because new users don't have accounts yet. If we required authentication for signup, it would be impossible for new users to join - a chicken-and-egg problem.

**Role Selection**: Anyone can create an account with any role (USER or ADMIN) without any restrictions. Simply specify the desired role in the request body.

**What Happens When You Signup**:
1. System validates the request (checks username/email not already taken, password meets requirements)
2. System hashes the password (converts plain password to secure hash using BCrypt)
3. System creates user record in database
4. System assigns the selected role to the user
5. System returns success response (201 Created)

**Why Password Requirements**: Strong passwords prevent hackers from guessing or breaking into accounts. Requirements ensure passwords are hard to crack.

#### cURL Command

```bash
curl -X POST http://localhost:8095/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john.doe@example.com",
    "password": "SecurePass123!",
    "role": "USER"
  }'
```

#### Request Body

```json
{
  "username": "john_doe",
  "email": "john.doe@example.com",
  "password": "SecurePass123!",
  "role": "USER"
}
```

#### Request Fields

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| username | String | Yes | Unique username (min 3 characters) |
| email | String | Yes | Valid email address |
| password | String | Yes | Password (min 8 chars, uppercase, lowercase, digit, special char) |
| role | String | No | Role name (USER or ADMIN). Default: USER |

#### Password Requirements

- Minimum 8 characters
- At least one uppercase letter (A-Z)
- At least one lowercase letter (a-z)
- At least one digit (0-9)
- At least one special character (@$!%*?&)

**Valid Password Examples:**
- `SecurePass123!`
- `MyPassword@2024`
- `Test123!Pass`

#### Response

**Success (201 Created):**
- Status: `201 Created`
- Body: Empty

**Error (400 Bad Request):**
```json
{
  "error": "Username already exists"
}
```

or

```json
{
  "username": "Username is required",
  "email": "Email should be valid",
  "password": "Password must be at least 8 characters..."
}
```

#### Creating ADMIN User

**Definition**: Creating an ADMIN user means registering a new user account with ADMIN role, which gives full access to all system features.

**Purpose**: ADMIN users have special privileges:
- Can access all endpoints (including admin-only endpoints)
- Can delete employees and managers
- Have full system access

**Important Note**: Anyone can create an ADMIN user account. There are no restrictions - you don't need to be authenticated or have any special permissions. Simply specify `"role": "ADMIN"` in the signup request.

**Simple Process to Create ADMIN User**:

You can create an ADMIN user directly using the signup endpoint without any authentication:

```bash
curl -X POST http://localhost:8095/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "new_admin",
    "email": "newadmin@example.com",
    "password": "AdminPass123!",
    "role": "ADMIN"
  }'
```

**Response:**

**Success (201 Created):**
- Status: `201 Created`
- Body: Empty

**Error (400 Bad Request) - If Username/Email Exists:**
```json
{
  "error": "Username already taken"
}
```

or

```json
{
  "error": "Email already in use"
}
```

**Complete Example - Create and Verify ADMIN User:**

```bash
# Step 1: Create new admin user (no authentication required)
curl -X POST http://localhost:8095/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "super_admin",
    "email": "superadmin@example.com",
    "password": "SuperAdmin123!",
    "role": "ADMIN"
  }'

# Step 2: Verify by logging in with new admin
curl -X POST http://localhost:8095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "super_admin",
    "password": "SuperAdmin123!"
  }'
```

**What Happens When You Create ADMIN**:
1. System validates the new user data (username, email, password)
2. System creates the new admin user with ADMIN role
3. System returns success response (201 Created)

**Security Notes**:
- Always use strong passwords for admin accounts
- Don't share admin credentials
- Change default admin password in production
- Note: Since anyone can create admin accounts, ensure proper security measures are in place for production environments

---

### 2. Login (Get JWT Token)

**Endpoint:** `POST /api/auth/login`  
**Authentication:** ❌ Not Required  
**Description:** Authenticates user and returns JWT token

#### cURL Command

```bash
curl -X POST http://localhost:8095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "john_doe",
    "password": "SecurePass123!"
  }'
```

#### Request Body

```json
{
  "usernameOrEmail": "john_doe",
  "password": "SecurePass123!"
}
```

#### Request Fields

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| usernameOrEmail | String | Yes | Username or email address |
| password | String | Yes | User password |

#### Response

**Success (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE3MDAwMDAwMDAsImV4cCI6MTcwMDAwOTAwMH0.xxxxx",
  "tokenType": "Bearer",
  "expiresInSeconds": 9000
}
```

**Error (401 Unauthorized):**
```json
{
  "error": "Invalid credentials"
}
```

#### Save Token for Later Use

**Bash/Zsh:**
```bash
# Extract token from response (requires jq)
TOKEN=$(curl -s -X POST http://localhost:8095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usernameOrEmail":"john_doe","password":"SecurePass123!"}' \
  | jq -r '.token')

# Or manually set
TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**Windows PowerShell:**
```powershell
$response = Invoke-RestMethod -Uri "http://localhost:8095/api/auth/login" -Method Post -ContentType "application/json" -Body '{"usernameOrEmail":"john_doe","password":"SecurePass123!"}'
$TOKEN = $response.token
```

---

## 👥 Employee APIs

**All Employee APIs require JWT authentication.**

**Authorization Header Format:**
```
Authorization: Bearer <your-token-here>
```

---

### 1. Get All Employees

**Endpoint:** `GET /api/employees`  
**Authentication:** ✅ Required  
**Roles:** USER, ADMIN  
**Description:** Returns list of all employees

#### cURL Command

```bash
curl -X GET http://localhost:8095/api/employees \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

**With Token Variable:**
```bash
curl -X GET http://localhost:8095/api/employees \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

#### Response

**Success (200 OK):**
```json
[
  {
    "id": 1,
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@example.com",
    "phone": "123-456-7890",
    "position": "Software Engineer",
    "managerId": null
  },
  {
    "id": 2,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "555-1234",
    "position": "Developer",
    "managerId": 1
  }
]
```

---

### 2. Create Employee

**Endpoint:** `POST /api/employees`  
**Authentication:** ✅ Required  
**Roles:** USER, ADMIN  
**Description:** Creates a new employee

#### cURL Command

```bash
curl -X POST http://localhost:8095/api/employees \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@example.com",
    "phone": "123-456-7890",
    "position": "Software Engineer",
    "managerId": null
  }'
```

**With Token Variable:**
```bash
curl -X POST http://localhost:8095/api/employees \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@example.com",
    "phone": "123-456-7890",
    "position": "Software Engineer",
    "managerId": null
  }'
```

#### Request Body

```json
{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@example.com",
  "phone": "123-456-7890",
  "position": "Software Engineer",
  "managerId": null
}
```

#### Request Fields

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| firstName | String | No | Employee first name |
| lastName | String | No | Employee last name |
| email | String | Yes | Valid email address (unique) |
| phone | String | No | Phone number |
| position | String | No | Job position |
| managerId | Long | No | ID of assigned manager (null if none) |

#### Response

**Success (200 OK):**
```json
{
  "id": 1,
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@example.com",
  "phone": "123-456-7890",
  "position": "Software Engineer",
  "managerId": null
}
```

---

### 3. Get Employee by ID

**Endpoint:** `GET /api/employees/{id}`  
**Authentication:** ✅ Required  
**Roles:** USER, ADMIN  
**Description:** Returns employee by ID

#### cURL Command

```bash
curl -X GET http://localhost:8095/api/employees/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

**With Token Variable:**
```bash
curl -X GET http://localhost:8095/api/employees/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

#### Response

**Success (200 OK):**
```json
{
  "id": 1,
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@example.com",
  "phone": "123-456-7890",
  "position": "Software Engineer",
  "managerId": null
}
```

**Error (404 Not Found):**
```json
{
  "error": "Employee not found with id : '1'"
}
```

---

### 4. Update Employee

**Endpoint:** `PUT /api/employees/{id}`  
**Authentication:** ✅ Required  
**Roles:** USER, ADMIN  
**Description:** Updates existing employee

#### cURL Command

```bash
curl -X PUT http://localhost:8095/api/employees/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith Updated",
    "email": "jane.smith@example.com",
    "phone": "123-456-7890",
    "position": "Senior Software Engineer",
    "managerId": null
  }'
```

**With Token Variable:**
```bash
curl -X PUT http://localhost:8095/api/employees/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith Updated",
    "email": "jane.smith@example.com",
    "phone": "123-456-7890",
    "position": "Senior Software Engineer",
    "managerId": null
  }'
```

#### Request Body

Same as Create Employee

#### Response

**Success (200 OK):**
```json
{
  "id": 1,
  "firstName": "Jane",
  "lastName": "Smith Updated",
  "email": "jane.smith@example.com",
  "phone": "123-456-7890",
  "position": "Senior Software Engineer",
  "managerId": null
}
```

---

### 5. Delete Employee

**Endpoint:** `DELETE /api/employees/{id}`  
**Authentication:** ✅ Required  
**Roles:** ADMIN only  
**Description:** Deletes employee by ID

#### cURL Command

```bash
curl -X DELETE http://localhost:8095/api/employees/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

**With Token Variable:**
```bash
curl -X DELETE http://localhost:8095/api/employees/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

#### Response

**Success (204 No Content):**
- Status: `204 No Content`
- Body: Empty

**Error (403 Forbidden) - If USER role tries to delete:**
```json
{
  "error": "Forbidden: Insufficient permissions"
}
```

---

## 👔 Manager APIs

**Most Manager APIs require ADMIN role.**

---

### 1. Get All Managers

**Endpoint:** `GET /api/managers`  
**Authentication:** ✅ Required  
**Roles:** ADMIN only  
**Description:** Returns list of all managers

#### cURL Command

```bash
curl -X GET http://localhost:8095/api/managers \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

**With Token Variable:**
```bash
curl -X GET http://localhost:8095/api/managers \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

#### Response

**Success (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Bob Johnson",
    "designation": "Engineering Manager",
    "experience": 10,
    "city": "New York"
  }
]
```

---

### 2. Create Manager

**Endpoint:** `POST /api/managers`  
**Authentication:** ✅ Required  
**Roles:** ADMIN only  
**Description:** Creates a new manager

#### cURL Command

```bash
curl -X POST http://localhost:8095/api/managers \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Bob Johnson",
    "designation": "Engineering Manager",
    "experience": 10,
    "city": "New York"
  }'
```

**With Token Variable:**
```bash
curl -X POST http://localhost:8095/api/managers \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Bob Johnson",
    "designation": "Engineering Manager",
    "experience": 10,
    "city": "New York"
  }'
```

#### Request Body

```json
{
  "name": "Bob Johnson",
  "designation": "Engineering Manager",
  "experience": 10,
  "city": "New York"
}
```

#### Request Fields

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| name | String | Yes | Manager name |
| designation | String | No | Job designation |
| experience | Integer | No | Years of experience |
| city | String | No | City location |

#### Response

**Success (200 OK):**
```json
{
  "id": 1,
  "name": "Bob Johnson",
  "designation": "Engineering Manager",
  "experience": 10,
  "city": "New York"
}
```

---

### 3. Get Manager by ID

**Endpoint:** `GET /api/managers/{id}`  
**Authentication:** ✅ Required  
**Roles:** USER, ADMIN  
**Description:** Returns manager by ID

#### cURL Command

```bash
curl -X GET http://localhost:8095/api/managers/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

**With Token Variable:**
```bash
curl -X GET http://localhost:8095/api/managers/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

#### Response

**Success (200 OK):**
```json
{
  "id": 1,
  "name": "Bob Johnson",
  "designation": "Engineering Manager",
  "experience": 10,
  "city": "New York"
}
```

---

### 4. Update Manager

**Endpoint:** `PUT /api/managers/{id}`  
**Authentication:** ✅ Required  
**Roles:** ADMIN only  
**Description:** Updates existing manager

#### cURL Command

```bash
curl -X PUT http://localhost:8095/api/managers/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Bob Johnson Updated",
    "designation": "Senior Engineering Manager",
    "experience": 12,
    "city": "San Francisco"
  }'
```

**With Token Variable:**
```bash
curl -X PUT http://localhost:8095/api/managers/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Bob Johnson Updated",
    "designation": "Senior Engineering Manager",
    "experience": 12,
    "city": "San Francisco"
  }'
```

#### Response

**Success (200 OK):**
```json
{
  "id": 1,
  "name": "Bob Johnson Updated",
  "designation": "Senior Engineering Manager",
  "experience": 12,
  "city": "San Francisco"
}
```

---

### 5. Delete Manager

**Endpoint:** `DELETE /api/managers/{id}`  
**Authentication:** ✅ Required  
**Roles:** ADMIN only  
**Description:** Deletes manager by ID

#### cURL Command

```bash
curl -X DELETE http://localhost:8095/api/managers/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

**With Token Variable:**
```bash
curl -X DELETE http://localhost:8095/api/managers/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

#### Response

**Success (204 No Content):**
- Status: `204 No Content`
- Body: Empty

---

## 📮 Postman Setup Guide

### Step 1: Install Postman

1. Download from: https://www.postman.com/downloads/
2. Install and open Postman

---

### Step 2: Create New Collection

1. Click **"New"** → **"Collection"**
2. Name it: **"Spring Boot Security API"**
3. Click **"Create"**

---

### Step 3: Set Collection Variables

1. Click on your collection
2. Go to **"Variables"** tab
3. Add these variables:

| Variable | Initial Value | Current Value |
|----------|---------------|---------------|
| base_url | http://localhost:8095 | http://localhost:8095 |
| token | (leave empty) | (leave empty) |

4. Click **"Save"**

---

### Step 4: Create Authentication Folder

1. Right-click collection → **"Add Folder"**
2. Name: **"Authentication"**

---

### Step 5: Create Signup Request

1. Right-click "Authentication" folder → **"Add Request"**
2. Name: **"Signup"**
3. Method: **POST**
4. URL: `{{base_url}}/api/auth/signup`
5. Go to **"Body"** tab → Select **"raw"** → Select **"JSON"**
6. Paste this:
```json
{
  "username": "testuser",
  "email": "testuser@example.com",
  "password": "SecurePass123!",
  "role": "USER"
}
```
7. Click **"Send"**
8. Should get **201 Created**

---

### Step 6: Create Login Request

1. Right-click "Authentication" folder → **"Add Request"**
2. Name: **"Login"**
3. Method: **POST**
4. URL: `{{base_url}}/api/auth/login`
5. Go to **"Body"** tab → Select **"raw"** → Select **"JSON"**
6. Paste this:
```json
{
  "usernameOrEmail": "testuser",
  "password": "SecurePass123!"
}
```
7. Go to **"Tests"** tab
8. Add this script to save token automatically:
```javascript
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.collectionVariables.set("token", jsonData.token);
    console.log("Token saved: " + jsonData.token);
}
```
9. Click **"Send"**
10. Check **"Console"** (bottom) to see token saved

---

### Step 7: Create Employee Folder

1. Right-click collection → **"Add Folder"**
2. Name: **"Employees"**

---

### Step 8: Create Employee Requests

#### 8.1 Get All Employees

1. Right-click "Employees" folder → **"Add Request"**
2. Name: **"Get All Employees"**
3. Method: **GET**
4. URL: `{{base_url}}/api/employees`
5. Go to **"Authorization"** tab
6. Type: **"Bearer Token"**
7. Token: `{{token}}`
8. Click **"Send"**

#### 8.2 Create Employee

1. Right-click "Employees" folder → **"Add Request"**
2. Name: **"Create Employee"**
3. Method: **POST**
4. URL: `{{base_url}}/api/employees`
5. **Authorization:** Bearer Token → `{{token}}`
6. **Body** → raw → JSON:
```json
{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@example.com",
  "phone": "123-456-7890",
  "position": "Software Engineer",
  "managerId": null
}
```
7. Click **"Send"**

#### 8.3 Get Employee by ID

1. Right-click "Employees" folder → **"Add Request"**
2. Name: **"Get Employee by ID"**
3. Method: **GET**
4. URL: `{{base_url}}/api/employees/1`
5. **Authorization:** Bearer Token → `{{token}}`
6. Click **"Send"**

#### 8.4 Update Employee

1. Right-click "Employees" folder → **"Add Request"**
2. Name: **"Update Employee"**
3. Method: **PUT**
4. URL: `{{base_url}}/api/employees/1`
5. **Authorization:** Bearer Token → `{{token}}`
6. **Body** → raw → JSON:
```json
{
  "firstName": "Jane",
  "lastName": "Smith Updated",
  "email": "jane.smith@example.com",
  "phone": "123-456-7890",
  "position": "Senior Software Engineer",
  "managerId": null
}
```
7. Click **"Send"`

#### 8.5 Delete Employee

1. Right-click "Employees" folder → **"Add Request"**
2. Name: **"Delete Employee"**
3. Method: **DELETE**
4. URL: `{{base_url}}/api/employees/1`
5. **Authorization:** Bearer Token → `{{token}}`
6. Click **"Send"`

---

### Step 9: Create Manager Folder

1. Right-click collection → **"Add Folder"**
2. Name: **"Managers"`

---

### Step 10: Create Manager Requests

Follow same pattern as Employees:

1. **Get All Managers** - GET `{{base_url}}/api/managers` (ADMIN only)
2. **Create Manager** - POST `{{base_url}}/api/managers` (ADMIN only)
3. **Get Manager by ID** - GET `{{base_url}}/api/managers/1`
4. **Update Manager** - PUT `{{base_url}}/api/managers/1` (ADMIN only)
5. **Delete Manager** - DELETE `{{base_url}}/api/managers/1` (ADMIN only)

**Remember:** Set Authorization to Bearer Token with `{{token}}`

---

### Step 11: Create Admin Login Request

For testing ADMIN-only endpoints:

1. Right-click "Authentication" folder → **"Add Request"**
2. Name: **"Login as Admin"**
3. Method: **POST**
4. URL: `{{base_url}}/api/auth/login`
5. **Body** → raw → JSON:
```json
{
  "usernameOrEmail": "admin",
  "password": "admin123"
}
```
6. **Tests** tab → Add same token saving script
7. Click **"Send"**

**Note:** Default admin credentials:
- Username: `admin`
- Password: `admin123`

---

## 📊 Complete API Summary Table

| Endpoint | Method | Auth Required | Role Required | Description |
|----------|--------|---------------|---------------|-------------|
| `/api/auth/signup` | POST | ❌ No | - | Create user account (anyone can create any role) |
| `/api/auth/login` | POST | ❌ No | - | Get JWT token |
| `/api/auth/forgot-password` | POST | ❌ No | - | Request password reset token |
| `/api/auth/reset-password` | POST | ❌ No | - | Reset password using token |
| `/api/auth/logout` | POST | ❌ No | - | Logout (client should discard token) |
| `/api/employees` | GET | ✅ Yes | USER, ADMIN | Get all employees |
| `/api/employees` | POST | ✅ Yes | USER, ADMIN | Create employee |
| `/api/employees/{id}` | GET | ✅ Yes | USER, ADMIN | Get employee by ID |
| `/api/employees/{id}` | PUT | ✅ Yes | USER, ADMIN | Update employee |
| `/api/employees/{id}` | DELETE | ✅ Yes | ADMIN | Delete employee |
| `/api/managers` | GET | ✅ Yes | ADMIN | Get all managers |
| `/api/managers` | POST | ✅ Yes | ADMIN | Create manager |
| `/api/managers/{id}` | GET | ✅ Yes | USER, ADMIN | Get manager by ID |
| `/api/managers/{id}` | PUT | ✅ Yes | ADMIN | Update manager |
| `/api/managers/{id}` | DELETE | ✅ Yes | ADMIN | Delete manager |

**Note**: Signup endpoint is public and anyone can create any role (USER or ADMIN) without authentication.

---

## ⚠️ Common Errors and Solutions

### 401 Unauthorized

**Problem:** Missing or invalid JWT token

**Solutions:**
1. Login again to get new token
2. Check token hasn't expired (9000 seconds = 2.5 hours)
3. Verify header format: `Authorization: Bearer <token>`
4. Make sure there's a space between "Bearer" and token

---

### 403 Forbidden

**Problem:** Insufficient permissions (wrong role)

**Solutions:**
1. Check your user role (USER vs ADMIN)
2. Some endpoints require ADMIN role
3. Login with ADMIN account:
   - Username: `admin`
   - Password: `admin123`

---

### 400 Bad Request

**Problem:** Validation errors

**Solutions:**
1. Check password requirements (min 8 chars, uppercase, lowercase, digit, special char)
2. Verify email format is valid
3. Ensure all required fields are provided
4. Check for duplicate username/email

---

### 404 Not Found

**Problem:** Resource doesn't exist

**Solutions:**
1. Check the ID exists in database
2. Verify endpoint URL is correct
3. Check if resource was deleted

---

### 500 Internal Server Error

**Problem:** Server-side error

**Solutions:**
1. Check application logs
2. Verify database connection
3. Check if database tables exist
4. Verify JWT secret is configured correctly

---

## 🎯 Quick Test Workflow

### Complete Example Workflow

```bash
# Step 1: Signup
curl -X POST http://localhost:8095/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "testuser@example.com",
    "password": "SecurePass123!",
    "role": "USER"
  }'

# Step 2: Login and save token
TOKEN=$(curl -s -X POST http://localhost:8095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "testuser",
    "password": "SecurePass123!"
  }' | jq -r '.token')

echo "Token: $TOKEN"

# Step 3: Get all employees
curl -X GET http://localhost:8095/api/employees \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"

# Step 4: Create employee
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

---

## 👑 Quick Reference: Create ADMIN User

**Definition**: This is a quick reference guide for creating a new ADMIN user account.

**Purpose**: ADMIN users have full system access and can perform all operations.

**Why This Section**: Quick reference for creating admin users - no authentication required!

### Complete Admin Creation Workflow

```bash
# Step 1: Create new admin user (no authentication required)
curl -X POST http://localhost:8095/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "new_admin",
    "email": "newadmin@example.com",
    "password": "AdminPass123!",
    "role": "ADMIN"
  }'

# Step 2: Verify by logging in with new admin
curl -X POST http://localhost:8095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "new_admin",
    "password": "AdminPass123!"
  }'
```

### Default Admin Credentials

**Username**: `admin`  
**Password**: `admin123`  
**Email**: `admin@example.com`  
**Role**: `ADMIN`

**Note**: These credentials are automatically created when the application starts (via DataInitializer). Change the default password in production!

---

## 📝 Postman Collection Export

To share your Postman collection:

1. Click on collection
2. Click **"..."** (three dots)
3. Select **"Export"**
4. Choose **"Collection v2.1"**
5. Save file
6. Share with team

To import:
1. Click **"Import"**
2. Select exported file
3. Collection will be added

---

## ✅ Testing Checklist

- [ ] Application running on port 8095
- [ ] Successfully signed up a user
- [ ] Successfully logged in and got token
- [ ] Tested GET /api/employees
- [ ] Tested POST /api/employees
- [ ] Tested GET /api/employees/{id}
- [ ] Tested PUT /api/employees/{id}
- [ ] Tested DELETE /api/employees/{id} (as ADMIN)
- [ ] Tested GET /api/managers (as ADMIN)
- [ ] Tested POST /api/managers (as ADMIN)
- [ ] Tested GET /api/managers/{id}
- [ ] Tested PUT /api/managers/{id} (as ADMIN)
- [ ] Tested DELETE /api/managers/{id} (as ADMIN)

---

**Happy Testing! 🎉**

