# Troubleshooting Guide - 401 Unauthorized on Signup

## Problem: Getting 401 Unauthorized when calling `/api/auth/signup`

## Solutions:

### ✅ Solution 1: Restart Your Application (MOST IMPORTANT)

After making security configuration changes, **you MUST restart your Spring Boot application**:

```bash
# Stop the current application (Ctrl+C in terminal)
# Then restart:
mvn spring-boot:run
```

Or if running from IDE:
- Stop the application
- Clean and rebuild the project
- Start the application again

### ✅ Solution 2: Use Valid Password Format

The password `"password123"` **does NOT meet the requirements**. 

**Password Requirements:**
- ✅ Minimum 8 characters
- ✅ Maximum 128 characters  
- ✅ At least one **uppercase** letter (A-Z)
- ✅ At least one **lowercase** letter (a-z)
- ✅ At least one **digit** (0-9)
- ✅ At least one **special character** (!@#$%^&*()_+-=[]{}|;:,.<>?)

**❌ Invalid Password Examples:**
- `password123` - Missing uppercase and special character
- `PASSWORD123` - Missing lowercase and special character
- `Password` - Missing digit and special character
- `Pass123` - Missing special character

**✅ Valid Password Examples:**
- `SecurePass123!`
- `MyPassword@2024`
- `Test123!Pass`
- `Password123#`

### ✅ Solution 3: Correct Request Format

**In Postman:**

1. **Method:** `POST`
2. **URL:** `http://localhost:8095/api/auth/signup`
3. **Headers Tab:**
   ```
   Content-Type: application/json
   ```
4. **Body Tab:**
   - Select: `raw`
   - Select: `JSON` (from dropdown)
   - Enter:
   ```json
   {
     "username": "newuser",
     "email": "newuser@example.com",
     "password": "SecurePass123!"
   }
   ```

### ✅ Solution 4: Check Application Logs

Look at your application console/logs for errors. You should see:
- Application started successfully
- Database connection established
- No security-related errors

### ✅ Solution 5: Verify Database Connection

Make sure your MySQL database is running and accessible:
```bash
# Check if MySQL is running
mysql -u root -p

# Verify database exists
SHOW DATABASES;
# Should see: SpringSecurity
```

### ✅ Solution 6: Test with curl (Alternative to Postman)

```bash
curl -X POST http://localhost:8095/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "newuser@example.com",
    "password": "SecurePass123!"
  }'
```

Expected response: `201 Created` (empty body)

---

## Expected Responses:

### ✅ Success (201 Created):
- **Status:** `201 Created`
- **Body:** Empty

### ❌ Password Validation Error (400 Bad Request):
```json
{
  "error": "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
}
```

### ❌ Username/Email Already Exists (400 Bad Request):
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

### ❌ Invalid Email Format (400 Bad Request):
```json
{
  "validationErrors": {
    "email": "Email must be a valid email address"
  }
}
```

---

## Common Mistakes:

1. ❌ **Not restarting application** after code changes
2. ❌ **Using weak password** that doesn't meet requirements
3. ❌ **Wrong Content-Type header** (should be `application/json`)
4. ❌ **Wrong URL** (should be `http://localhost:8095/api/auth/signup`)
5. ❌ **Application not running** on port 8095
6. ❌ **Database not connected**

---

## Quick Test Sequence:

1. ✅ Restart application
2. ✅ Use valid password: `SecurePass123!`
3. ✅ Send POST request to `/api/auth/signup`
4. ✅ Should get `201 Created`
5. ✅ Then try login with same credentials

---

## Still Getting 401?

If you've tried all the above and still get 401:

1. **Check if application is actually running:**
   ```bash
   curl http://localhost:8095/actuator/health
   ```
   Should return health status.

2. **Check application logs** for any security filter errors

3. **Verify the SecurityConfig is being loaded:**
   - Look for log message: "JWT Utils initialized with expiration: 9000 seconds"

4. **Try accessing a different public endpoint:**
   ```bash
   curl http://localhost:8095/api/auth/login
   ```
   Should return 400 (bad request) not 401 (unauthorized)

5. **Clear browser/Postman cache** and try again

---

## Need More Help?

Check:
- Application console logs
- Database connection status
- Port 8095 is not blocked by firewall
- No other application using port 8095

