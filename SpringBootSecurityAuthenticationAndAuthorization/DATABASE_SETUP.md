# Database Setup Instructions

## Issue: Tables Not Created in Database

If tables are not being created when you run the application, follow these steps:

### Step 1: Create the Database

The database `SpringSecurity` must exist before the application can create tables.

**Option A: Using MySQL Command Line**
```bash
mysql -u root -p
```

Then run:
```sql
CREATE DATABASE IF NOT EXISTS SpringSecurity CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

**Option B: Using the provided SQL script**
```bash
mysql -u root -p < create_database.sql
```

### Step 2: Verify Database Connection

Check your `application.properties` file:
- Database URL: `jdbc:mysql://localhost:3306/SpringSecurity`
- Username: `root`
- Password: `Macbook@1630` (update if different)

### Step 3: Verify Hibernate Configuration

In `application.properties`, ensure:
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.generate-ddl=true
```

### Step 4: Run the Application

```bash
./mvnw spring-boot:run
```

### Step 5: Check Application Logs

Look for these log messages:
- `Hibernate: create table roles`
- `Hibernate: create table users`
- `Initializing roles...`
- `Created ADMIN role`
- `Created USER role`

### Troubleshooting

**If tables still don't appear:**

1. **Check if database exists:**
   ```sql
   SHOW DATABASES;
   USE SpringSecurity;
   SHOW TABLES;
   ```

2. **Temporarily change ddl-auto to 'create':**
   ```properties
   spring.jpa.hibernate.ddl-auto=create
   ```
   ⚠️ **Warning:** This will DROP all existing tables and recreate them!

3. **Check application logs for errors:**
   - Connection errors
   - Authentication errors
   - SQL syntax errors

4. **Verify MySQL is running:**
   ```bash
   mysqladmin -u root -p status
   ```

### Expected Tables After Startup

After successful startup, you should see these tables:
- `roles`
- `users`
- `user_roles`
- `managers`
- `employees`

### Default Users Created

- **Admin:** username=`admin`, password=`admin123`
- **User:** username=`user`, password=`user123`

