# Postman Testing Guide - Step by Step

## 📋 Prerequisites

1. **Start the Spring Boot Application**
   - Make sure your MySQL database is running
   - Run the application: `mvn spring-boot:run` or run from your IDE
   - Application will start on: `http://localhost:8095`

2. **Open Postman** (Desktop app or web version)

---

## 🚀 Step-by-Step Testing Process

### **STEP 1: Create a New User (Signup)**

#### **API Endpoint:** `POST /api/auth/signup`

**Details:**
- **URL:** `http://localhost:8095/api/auth/signup`
- **Method:** `POST`
- **Headers:**
  ```
  Content-Type: application/json
  ```
- **Body (raw JSON):**
  ```json
  {
    "username": "john_doe",
    "email": "john.doe@example.com",
    "password": "SecurePass123!",
    "role": "USER"
  }
  ```

**Password Requirements:**
- Minimum 8 characters
- Maximum 128 characters
- Must contain: uppercase, lowercase, digit, and special character

**Example Request Body (USER role):**
```json
{
  "username": "john_doe",
  "email": "john.doe@example.com",
  "password": "SecurePass123!",
  "role": "USER"
}
```

**Example Request Body (ADMIN role - only if you're already logged in as ADMIN):**
```json
{
  "username": "admin_user",
  "email": "admin@example.com",
  "password": "AdminPass123!",
  "role": "ADMIN"
}
```

**Expected Response:**
- **Status Code:** `201 Created`
- **Body:** Empty (no content)

**If Error:**
- `400 Bad Request` - Validation errors (check password requirements, email format)
- `403 Forbidden` - Trying to create ADMIN without being ADMIN yourself

---

### **STEP 2: Login and Get JWT Token**

#### **API Endpoint:** `POST /api/auth/login`

**Details:**
- **URL:** `http://localhost:8095/api/auth/login`
- **Method:** `POST`
- **Headers:**
  ```
  Content-Type: application/json
  ```
- **Body (raw JSON):**
  ```json
  {
    "usernameOrEmail": "john_doe",
    "password": "SecurePass123!"
  }
  ```

**Note:** You can use either `username` or `email` in the `usernameOrEmail` field.

**Expected Response:**
- **Status Code:** `200 OK`
- **Body:**
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "expiresInSeconds": 9000
  }
  ```

**⚠️ IMPORTANT:** Copy the `token` value from the response. You'll need it for all protected endpoints!

---

### **STEP 3: Test Protected Endpoints**

Now you can use the JWT token to access protected APIs. Add the token to the Authorization header.

#### **Setting Authorization in Postman:**

1. Go to the **Authorization** tab in Postman
2. Select **Type:** `Bearer Token`
3. Paste your JWT token in the **Token** field
   - OR manually add in Headers:
     ```
     Authorization: Bearer <your-token-here>
     ```

---

### **STEP 4: Test Employee APIs**

#### **4.1 Create Employee**
- **URL:** `http://localhost:8095/api/employees`
- **Method:** `POST`
- **Authorization:** `Bearer <your-token>`
- **Headers:**
  ```
  Content-Type: application/json
  Authorization: Bearer <your-jwt-token>
  ```
- **Body:**
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
- **Required Role:** `USER` or `ADMIN`
- **Expected Response:** `200 OK` with employee data

#### **4.2 Get All Employees**
- **URL:** `http://localhost:8095/api/employees`
- **Method:** `GET`
- **Authorization:** `Bearer <your-token>`
- **Required Role:** `USER` or `ADMIN`
- **Expected Response:** `200 OK` with list of employees

#### **4.3 Get Employee by ID**
- **URL:** `http://localhost:8095/api/employees/{id}`
- **Method:** `GET`
- **Example:** `http://localhost:8095/api/employees/1`
- **Authorization:** `Bearer <your-token>`
- **Required Role:** `USER` or `ADMIN`
- **Expected Response:** `200 OK` with employee data

#### **4.4 Update Employee**
- **URL:** `http://localhost:8095/api/employees/{id}`
- **Method:** `PUT`
- **Example:** `http://localhost:8095/api/employees/1`
- **Authorization:** `Bearer <your-token>`
- **Headers:**
  ```
  Content-Type: application/json
  Authorization: Bearer <your-jwt-token>
  ```
- **Body:**
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
- **Required Role:** `USER` or `ADMIN`
- **Expected Response:** `200 OK` with updated employee data

#### **4.5 Delete Employee**
- **URL:** `http://localhost:8095/api/employees/{id}`
- **Method:** `DELETE`
- **Example:** `http://localhost:8095/api/employees/1`
- **Authorization:** `Bearer <your-token>`
- **Required Role:** `ADMIN` only
- **Expected Response:** `204 No Content`

---

### **STEP 5: Test Manager APIs**

#### **5.1 Create Manager**
- **URL:** `http://localhost:8095/api/managers`
- **Method:** `POST`
- **Authorization:** `Bearer <your-token>`
- **Headers:**
  ```
  Content-Type: application/json
  Authorization: Bearer <your-jwt-token>
  ```
- **Body:**
  ```json
  {
    "firstName": "Bob",
    "lastName": "Johnson",
    "email": "bob.johnson@example.com",
    "department": "Engineering"
  }
  ```
- **Required Role:** `ADMIN` only
- **Expected Response:** `200 OK` with manager data

#### **5.2 Get All Managers**
- **URL:** `http://localhost:8095/api/managers`
- **Method:** `GET`
- **Authorization:** `Bearer <your-token>`
- **Required Role:** `ADMIN` only
- **Expected Response:** `200 OK` with list of managers

#### **5.3 Get Manager by ID**
- **URL:** `http://localhost:8095/api/managers/{id}`
- **Method:** `GET`
- **Example:** `http://localhost:8095/api/managers/1`
- **Authorization:** `Bearer <your-token>`
- **Required Role:** `USER` or `ADMIN`
- **Expected Response:** `200 OK` with manager data

#### **5.4 Update Manager**
- **URL:** `http://localhost:8095/api/managers/{id}`
- **Method:** `PUT`
- **Example:** `http://localhost:8095/api/managers/1`
- **Authorization:** `Bearer <your-token>`
- **Required Role:** `ADMIN` only
- **Expected Response:** `200 OK` with updated manager data

#### **5.5 Delete Manager**
- **URL:** `http://localhost:8095/api/managers/{id}`
- **Method:** `DELETE`
- **Example:** `http://localhost:8095/api/managers/1`
- **Authorization:** `Bearer <your-token>`
- **Required Role:** `ADMIN` only
- **Expected Response:** `204 No Content`

---

## 🔐 Testing Different Roles

### **Test as USER Role:**

1. **Signup as USER:**
   ```json
   {
     "username": "testuser",
     "email": "testuser@example.com",
     "password": "TestPass123!",
     "role": "USER"
   }
   ```

2. **Login and get token**

3. **Try accessing:**
   - ✅ `/api/employees` - GET, POST, PUT (should work)
   - ✅ `/api/employees/{id}` - GET (should work)
   - ❌ `/api/employees/{id}` - DELETE (should fail with 403)
   - ❌ `/api/managers` - GET, POST, PUT, DELETE (should fail with 403)
   - ✅ `/api/managers/{id}` - GET (should work)

### **Test as ADMIN Role:**

1. **First, create an ADMIN user (you need to be logged in as ADMIN already, or use database seed)**
   - If you have seed data, use those credentials
   - Or create ADMIN through database directly

2. **Login as ADMIN and get token**

3. **Try accessing:**
   - ✅ All `/api/employees` endpoints (should work)
   - ✅ All `/api/managers` endpoints (should work)

---

## 📝 Quick Reference - All Endpoints

### **Public Endpoints (No Auth Required):**
- `POST /api/auth/signup` - Create new user
- `POST /api/auth/login` - Login and get JWT token

### **Protected Endpoints (Require JWT Token):**

#### **Employee APIs:**
- `GET /api/employees` - Get all employees (USER, ADMIN)
- `GET /api/employees/{id}` - Get employee by ID (USER, ADMIN)
- `POST /api/employees` - Create employee (USER, ADMIN)
- `PUT /api/employees/{id}` - Update employee (USER, ADMIN)
- `DELETE /api/employees/{id}` - Delete employee (ADMIN only)

#### **Manager APIs:**
- `GET /api/managers` - Get all managers (ADMIN only)
- `GET /api/managers/{id}` - Get manager by ID (USER, ADMIN)
- `POST /api/managers` - Create manager (ADMIN only)
- `PUT /api/managers/{id}` - Update manager (ADMIN only)
- `DELETE /api/managers/{id}` - Delete manager (ADMIN only)

---

## ⚠️ Common Errors and Solutions

### **401 Unauthorized:**
- **Problem:** Missing or invalid JWT token
- **Solution:** 
  - Make sure you logged in and copied the token correctly
  - Check that token hasn't expired (9000 seconds = 2.5 hours)
  - Verify Authorization header format: `Bearer <token>`

### **403 Forbidden:**
- **Problem:** Insufficient permissions (wrong role)
- **Solution:** 
  - Check your user role
  - Some endpoints require ADMIN role
  - Try logging in with an ADMIN account

### **400 Bad Request:**
- **Problem:** Validation errors
- **Solution:**
  - Check password requirements (8+ chars, uppercase, lowercase, digit, special char)
  - Verify email format
  - Ensure all required fields are provided

### **404 Not Found:**
- **Problem:** Resource doesn't exist
- **Solution:** Check the ID you're using exists in the database

---

## 🎯 Postman Collection Setup Tips

1. **Create Environment Variables:**
   - `base_url`: `http://localhost:8095`
   - `jwt_token`: (set this after login)

2. **Use Variables in Requests:**
   - URL: `{{base_url}}/api/auth/login`
   - Authorization: `Bearer {{jwt_token}}`

3. **Save Token Automatically:**
   - In login request, add a test script:
   ```javascript
   if (pm.response.code === 200) {
       var jsonData = pm.response.json();
       pm.environment.set("jwt_token", jsonData.token);
   }
   ```

---

## 📋 Complete Testing Flow Example

```
1. POST /api/auth/signup
   → Create user "john_doe" with role "USER"
   → Response: 201 Created

2. POST /api/auth/login
   → Login with "john_doe" / "SecurePass123!"
   → Response: 200 OK with token
   → Copy token: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

3. GET /api/employees
   → Headers: Authorization: Bearer <token>
   → Response: 200 OK with employee list

4. POST /api/employees
   → Headers: Authorization: Bearer <token>
   → Body: { employee data }
   → Response: 200 OK with created employee

5. DELETE /api/employees/1
   → Headers: Authorization: Bearer <token>
   → Response: 403 Forbidden (USER can't delete)
   → OR 204 No Content (if ADMIN)
```

---

## ✅ Success Checklist

- [ ] Application is running on port 8095
- [ ] Database is connected
- [ ] Successfully created a user via signup
- [ ] Successfully logged in and received JWT token
- [ ] Successfully accessed protected endpoints with token
- [ ] Tested USER role permissions
- [ ] Tested ADMIN role permissions
- [ ] Verified error handling (401, 403, 400, 404)

---

**Happy Testing! 🚀**

