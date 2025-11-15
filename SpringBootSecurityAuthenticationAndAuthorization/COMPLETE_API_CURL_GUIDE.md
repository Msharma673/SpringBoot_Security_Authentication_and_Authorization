# 🚀 Complete API cURL Guide - All Endpoints

## 📋 API Execution Order

**Follow this order:**
1. ✅ **Signup** (Create user account) - NO AUTH REQUIRED
2. ✅ **Login** (Get JWT token) - NO AUTH REQUIRED  
3. ✅ **Use Protected APIs** (With JWT token)

---

## 🔧 Base Configuration

**Base URL:** `http://localhost:8095`  
**Port:** `8095`  
**Content-Type:** `application/json`

---

## 📝 Step-by-Step: Complete API Flow

### **STEP 1: Signup (Create User Account)**

**This is the FIRST API you should hit!**

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

**Expected Response:**
- **Status:** `201 Created`
- **Body:** Empty

**Password Requirements:**
- Minimum 8 characters
- At least one uppercase letter
- At least one lowercase letter
- At least one digit
- At least one special character

**Valid Password Examples:**
- `SecurePass123!`
- `MyPassword@2024`
- `Test123!Pass`

---

### **STEP 2: Login (Get JWT Token)**

**Save the token from this response!**

```bash
curl -X POST http://localhost:8095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "john_doe",
    "password": "SecurePass123!"
  }'
```

**Expected Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresInSeconds": 9000
}
```

**⚠️ IMPORTANT:** Copy the `token` value! You'll need it for all protected endpoints.

**Save token to variable (for bash):**
```bash
TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."  # Replace with actual token
```

---

## 🔐 Protected APIs (Require JWT Token)

**All APIs below require the Authorization header:**
```
Authorization: Bearer <your-token-here>
```

---

## 👥 Employee APIs

### **1. Get All Employees**

```bash
curl -X GET http://localhost:8095/api/employees \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

**With token variable:**
```bash
curl -X GET http://localhost:8095/api/employees \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

**Required Role:** `USER` or `ADMIN`

**Expected Response:**
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
  }
]
```

---

### **2. Create Employee**

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

**With token variable:**
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

**Required Role:** `USER` or `ADMIN`

**Expected Response:**
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

### **3. Get Employee by ID**

```bash
curl -X GET http://localhost:8095/api/employees/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

**With token variable:**
```bash
curl -X GET http://localhost:8095/api/employees/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

**Required Role:** `USER` or `ADMIN`

**Expected Response:**
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

### **4. Update Employee**

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

**With token variable:**
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

**Required Role:** `USER` or `ADMIN`

**Expected Response:**
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

### **5. Delete Employee**

```bash
curl -X DELETE http://localhost:8095/api/employees/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

**With token variable:**
```bash
curl -X DELETE http://localhost:8095/api/employees/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

**Required Role:** `ADMIN` only

**Expected Response:**
- **Status:** `204 No Content`
- **Body:** Empty

---

## 👔 Manager APIs

### **1. Get All Managers**

```bash
curl -X GET http://localhost:8095/api/managers \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

**With token variable:**
```bash
curl -X GET http://localhost:8095/api/managers \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

**Required Role:** `ADMIN` only

**Expected Response:**
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

### **2. Create Manager**

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

**With token variable:**
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

**Required Role:** `ADMIN` only

**Expected Response:**
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

### **3. Get Manager by ID**

```bash
curl -X GET http://localhost:8095/api/managers/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

**With token variable:**
```bash
curl -X GET http://localhost:8095/api/managers/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

**Required Role:** `USER` or `ADMIN`

**Expected Response:**
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

### **4. Update Manager**

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

**With token variable:**
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

**Required Role:** `ADMIN` only

**Expected Response:**
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

### **5. Delete Manager**

```bash
curl -X DELETE http://localhost:8095/api/managers/1 \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

**With token variable:**
```bash
curl -X DELETE http://localhost:8095/api/managers/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

**Required Role:** `ADMIN` only

**Expected Response:**
- **Status:** `204 No Content`
- **Body:** Empty

---

## 🎯 Complete Example: Full Workflow

### **Step 1: Signup**
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

### **Step 2: Login and Save Token**
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

# Or manually copy token from response
# TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### **Step 3: Use Protected APIs**
```bash
# Get all employees
curl -X GET http://localhost:8095/api/employees \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"

# Create employee
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

## 📊 API Summary Table

| Endpoint | Method | Auth Required | Role Required | Description |
|----------|--------|---------------|---------------|-------------|
| `/api/auth/signup` | POST | ❌ No | - | Create user account |
| `/api/auth/login` | POST | ❌ No | - | Get JWT token |
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

---

## 🔑 Quick Reference: Token Management

### **Save Token (Bash/Zsh)**
```bash
# Method 1: Manual
TOKEN="your-token-here"

# Method 2: Extract from login response (requires jq)
TOKEN=$(curl -s -X POST http://localhost:8095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usernameOrEmail":"testuser","password":"SecurePass123!"}' \
  | jq -r '.token')

# Method 3: Extract without jq (basic)
RESPONSE=$(curl -s -X POST http://localhost:8095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usernameOrEmail":"testuser","password":"SecurePass123!"}')
TOKEN=$(echo $RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)
```

### **Use Token in Requests**
```bash
# Replace YOUR_TOKEN_HERE with actual token
curl -X GET http://localhost:8095/api/employees \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"

# Or use variable
curl -X GET http://localhost:8095/api/employees \
  -H "Authorization: Bearer $TOKEN"
```

---

## ⚠️ Common Errors & Solutions

### **401 Unauthorized**
- **Problem:** Missing or invalid token
- **Solution:** 
  - Login again to get new token
  - Check token hasn't expired (9000 seconds = 2.5 hours)
  - Verify header format: `Authorization: Bearer <token>`

### **403 Forbidden**
- **Problem:** Insufficient permissions (wrong role)
- **Solution:** 
  - Check your user role
  - Some endpoints require ADMIN role
  - Login with ADMIN account

### **400 Bad Request**
- **Problem:** Validation errors
- **Solution:**
  - Check password requirements
  - Verify email format
  - Ensure all required fields provided

### **404 Not Found**
- **Problem:** Resource doesn't exist
- **Solution:** Check the ID exists in database

---

## 🎯 Testing Checklist

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

## 🚀 Quick Start Script

Save this as `test-api.sh`:

```bash
#!/bin/bash

BASE_URL="http://localhost:8095"

echo "=== Step 1: Signup ==="
curl -X POST $BASE_URL/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "testuser@example.com",
    "password": "SecurePass123!",
    "role": "USER"
  }'

echo -e "\n\n=== Step 2: Login ==="
TOKEN=$(curl -s -X POST $BASE_URL/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "testuser",
    "password": "SecurePass123!"
  }' | grep -o '"token":"[^"]*' | cut -d'"' -f4)

echo "Token: $TOKEN"

echo -e "\n\n=== Step 3: Get All Employees ==="
curl -X GET $BASE_URL/api/employees \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"

echo -e "\n\n=== Step 4: Create Employee ==="
curl -X POST $BASE_URL/api/employees \
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

**Make it executable:**
```bash
chmod +x test-api.sh
./test-api.sh
```

---

**Happy Testing! 🎉**

