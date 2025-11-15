# 🚀 Quick Start: API Order & First API to Hit

## ✅ **FIRST API TO HIT: Signup**

**This is the FIRST API you must call!**

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

**Expected:** `201 Created` (empty body)

---

## 📋 Complete API Order

### **Step 1: Signup (FIRST!)**
```bash
POST http://localhost:8095/api/auth/signup
```
- ❌ **No Auth Required**
- Creates user account
- **This is the FIRST API to hit!**

---

### **Step 2: Login (Get Token)**
```bash
POST http://localhost:8095/api/auth/login
```
- ❌ **No Auth Required**
- Returns JWT token
- **Save the token for next steps!**

---

### **Step 3: Use Protected APIs**
All APIs below require the token from Step 2:

#### **Employee APIs:**
- `GET /api/employees` - Get all employees
- `POST /api/employees` - Create employee
- `GET /api/employees/{id}` - Get employee by ID
- `PUT /api/employees/{id}` - Update employee
- `DELETE /api/employees/{id}` - Delete employee (ADMIN only)

#### **Manager APIs:**
- `GET /api/managers` - Get all managers (ADMIN only)
- `POST /api/managers` - Create manager (ADMIN only)
- `GET /api/managers/{id}` - Get manager by ID
- `PUT /api/managers/{id}` - Update manager (ADMIN only)
- `DELETE /api/managers/{id}` - Delete manager (ADMIN only)

---

## 🎯 Quick Copy-Paste Commands

### **1. Signup (FIRST!)**
```bash
curl -X POST http://localhost:8095/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"SecurePass123!","role":"USER"}'
```

### **2. Login (Save Token)**
```bash
curl -X POST http://localhost:8095/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usernameOrEmail":"testuser","password":"SecurePass123!"}'
```

**Copy the `token` from response!**

### **3. Use Token (Replace YOUR_TOKEN)**
```bash
curl -X GET http://localhost:8095/api/employees \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json"
```

---

## 📖 Full Details

For complete curl commands for ALL APIs, see: **`COMPLETE_API_CURL_GUIDE.md`**

---

**Remember: Signup is the FIRST API! 🎯**

