# Complete Signup API Guide - Step by Step

## 🎯 Overview

The signup API allows you to create a new user account. **It does NOT require authentication** - it's a public endpoint.

**Endpoint:** `POST /api/auth/signup`  
**URL:** `http://localhost:8095/api/auth/signup`  
**Authentication Required:** ❌ NO

---

## 📋 Complete Step-by-Step Process

### **STEP 1: Ensure Application is Running**

1. **Start MySQL Database:**
   ```bash
   # Check if MySQL is running
   mysql -u root -p
   ```

2. **Start Spring Boot Application:**
   ```bash
   cd SpringBootSecurityAuthenticationAndAuthorization
   mvn spring-boot:run
   ```
   
   Or from your IDE:
   - Run `SpringBootSecurityAuthenticationAndAuthorizationApplication.java`
   - Wait for: "Started SpringBootSecurityAuthenticationAndAuthorizationApplication"

3. **Verify Application is Running:**
   ```bash
   curl http://localhost:8095/actuator/health
   ```
   Should return health status (not 401)

---

### **STEP 2: Prepare Your Request**

#### **Option A: Using Postman**

1. **Open Postman**
2. **Create New Request:**
   - Method: `POST`
   - URL: `http://localhost:8095/api/auth/signup`

3. **Headers Tab:**
   ```
   Content-Type: application/json
   ```
   ⚠️ **IMPORTANT:** Do NOT add `Authorization` header for signup!

4. **Body Tab:**
   - Select: `raw`
   - Select: `JSON` (from dropdown)
   - Enter JSON:
   ```json
   {
     "username": "john_doe",
     "email": "john.doe@example.com",
     "password": "SecurePass123!",
     "role": "USER"
   }
   ```

5. **Click Send**

#### **Option B: Using cURL**

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

---

### **STEP 3: Request Body Format**

#### **Required Fields:**
- `username` (String, required) - Must be unique
- `email` (String, required) - Must be valid email format and unique
- `password` (String, required) - Must meet password requirements

#### **Optional Fields:**
- `role` (String, optional) - Defaults to "USER" if not provided
  - `"USER"` - Regular user (anyone can create)
  - `"ADMIN"` - Admin user (only existing ADMIN can create)

#### **Password Requirements:**
✅ Minimum 8 characters  
✅ Maximum 128 characters  
✅ At least one **uppercase** letter (A-Z)  
✅ At least one **lowercase** letter (a-z)  
✅ At least one **digit** (0-9)  
✅ At least one **special character** (!@#$%^&*()_+-=[]{}|;:,.<>?)

**✅ Valid Password Examples:**
- `SecurePass123!`
- `MyPassword@2024`
- `Test123!Pass`
- `Password123#`

**❌ Invalid Password Examples:**
- `password123` - Missing uppercase and special character
- `PASSWORD123` - Missing lowercase and special character
- `Password` - Missing digit and special character
- `Pass123` - Missing special character

---

### **STEP 4: Expected Responses**

#### **✅ Success Response (201 Created):**
- **Status Code:** `201 Created`
- **Body:** Empty (no content)
- **Headers:** Standard response headers

#### **❌ Error Responses:**

**400 Bad Request - Validation Error:**
```json
{
  "error": "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
}
```

**400 Bad Request - Username/Email Exists:**
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

**400 Bad Request - Invalid Email:**
```json
{
  "validationErrors": {
    "email": "Email must be a valid email address"
  }
}
```

**403 Forbidden - Creating ADMIN without permission:**
```json
{
  "error": "Only ADMIN can create ADMIN user"
}
```

**401 Unauthorized - See troubleshooting below**

---

## 🔧 Troubleshooting 401 Unauthorized Error

### **Common Causes & Solutions:**

#### **1. ❌ Authorization Header Present**
**Problem:** You're sending an `Authorization` header with an invalid/expired token.

**Solution:**
- **In Postman:** Go to Authorization tab → Select "No Auth"
- **In Headers:** Remove any `Authorization` header
- **In cURL:** Don't include `-H "Authorization: Bearer ..."`

#### **2. ❌ Application Not Restarted**
**Problem:** Security configuration changes require restart.

**Solution:**
```bash
# Stop application (Ctrl+C)
# Then restart:
mvn spring-boot:run
```

#### **3. ❌ Wrong URL or Port**
**Problem:** Application not running on expected port.

**Solution:**
- Check application logs for: "Tomcat started on port(s): 8095"
- Verify URL: `http://localhost:8095/api/auth/signup`
- Check if port 8095 is blocked by firewall

#### **4. ❌ CORS Preflight Issue**
**Problem:** Browser sending OPTIONS request that fails.

**Solution:**
- Try using Postman or cURL instead of browser
- Check CORS configuration in `SecurityConfig.java`
- Verify allowed origins include your client

#### **5. ❌ Database Connection Issue**
**Problem:** Application can't connect to database.

**Solution:**
- Check MySQL is running: `mysql -u root -p`
- Verify database exists: `SHOW DATABASES;` (should see `SpringSecurity`)
- Check `application.properties` for correct credentials

---

## 🧪 Testing the Signup API

### **Test 1: Basic Signup (USER role)**
```json
POST http://localhost:8095/api/auth/signup
Content-Type: application/json

{
  "username": "testuser",
  "email": "testuser@example.com",
  "password": "SecurePass123!",
  "role": "USER"
}
```
**Expected:** `201 Created`

### **Test 2: Signup without role (defaults to USER)**
```json
POST http://localhost:8095/api/auth/signup
Content-Type: application/json

{
  "username": "testuser2",
  "email": "testuser2@example.com",
  "password": "SecurePass123!"
}
```
**Expected:** `201 Created` (role defaults to USER)

### **Test 3: Invalid Password**
```json
POST http://localhost:8095/api/auth/signup
Content-Type: application/json

{
  "username": "testuser3",
  "email": "testuser3@example.com",
  "password": "weakpass"
}
```
**Expected:** `400 Bad Request` with password validation error

### **Test 4: Duplicate Username**
```json
POST http://localhost:8095/api/auth/signup
Content-Type: application/json

{
  "username": "testuser",  // Already exists from Test 1
  "email": "different@example.com",
  "password": "SecurePass123!"
}
```
**Expected:** `400 Bad Request` - "Username already taken"

---

## 🔄 Complete Flow: Signup → Login → Use API

### **1. Signup (No Auth Required)**
```bash
POST /api/auth/signup
{
  "username": "john_doe",
  "email": "john.doe@example.com",
  "password": "SecurePass123!",
  "role": "USER"
}
```
**Response:** `201 Created`

### **2. Login (No Auth Required)**
```bash
POST /api/auth/login
{
  "usernameOrEmail": "john_doe",
  "password": "SecurePass123!"
}
```
**Response:** `200 OK` with JWT token
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresInSeconds": 9000
}
```

### **3. Use Protected Endpoints (Auth Required)**
```bash
GET /api/employees
Authorization: Bearer <your-token-here>
```
**Response:** `200 OK` with employee list

---

## ✅ Checklist Before Calling Signup API

- [ ] Application is running on port 8095
- [ ] MySQL database is running and accessible
- [ ] Database `SpringSecurity` exists
- [ ] No `Authorization` header in request
- [ ] `Content-Type: application/json` header is set
- [ ] Password meets all requirements (uppercase, lowercase, digit, special char, 8+ chars)
- [ ] Email is in valid format
- [ ] Username is unique (not already taken)
- [ ] Request body is valid JSON

---

## 🚨 Still Getting 401?

If you've tried everything above and still get 401:

1. **Check Application Logs:**
   - Look for security filter errors
   - Check for authentication entry point messages
   - Verify JWT filter is skipping `/api/auth/**` paths

2. **Test with Simple cURL:**
   ```bash
   curl -v -X POST http://localhost:8095/api/auth/signup \
     -H "Content-Type: application/json" \
     -d '{"username":"test","email":"test@test.com","password":"Test123!Pass"}'
   ```
   The `-v` flag shows detailed request/response headers

3. **Verify Security Configuration:**
   - Check `SecurityConfig.java` line 69: `auth.requestMatchers("/api/auth/**").permitAll();`
   - Check `JwtAuthenticationFilter.java` line 34: `return path.startsWith("/api/auth/")`

4. **Clear Browser/Postman Cache:**
   - Clear all cookies
   - Clear cache
   - Try in incognito/private mode

5. **Check for Proxy/Firewall:**
   - Disable VPN if using
   - Check firewall settings
   - Verify no proxy is intercepting requests

---

## 📝 Quick Reference

**Endpoint:** `POST /api/auth/signup`  
**URL:** `http://localhost:8095/api/auth/signup`  
**Auth Required:** ❌ NO  
**Headers:** `Content-Type: application/json`  
**Success:** `201 Created`  
**Common Errors:** `400` (validation), `401` (config issue), `403` (ADMIN creation)

---

**Happy Signing Up! 🚀**

