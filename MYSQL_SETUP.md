# 🗄️ MySQL Database Setup Guide

This guide will help you set up MySQL for the Employee Management System.

---

## 📋 Prerequisites

- MySQL Server 8.0 or higher installed
- MySQL running on localhost:3306

---

## 🚀 Quick Setup

### Option 1: Using MySQL Command Line

```bash
# Login to MySQL
mysql -u root -p

# Create database
CREATE DATABASE employeedb;

# Create user (optional, if not using root)
CREATE USER 'empuser'@'localhost' IDENTIFIED BY 'emppass';
GRANT ALL PRIVILEGES ON employeedb.* TO 'empuser'@'localhost';
FLUSH PRIVILEGES;

# Exit
exit;
```

### Option 2: Using Docker (Recommended for Development)

```bash
# Run MySQL in Docker
docker run --name mysql-employee \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=employeedb \
  -p 3306:3306 \
  -d mysql:8.0

# Check if running
docker ps

# View logs
docker logs mysql-employee
```

---

## ⚙️ Configuration

The application is configured to use:

**Database:** `employeedb`  
**Host:** `localhost:3306`  
**Username:** `root`  
**Password:** `root`

### To Change Database Credentials:

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employeedb?createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password
```

---

## 🏃 Run the Application

```bash
# Build
mvn clean install

# Run
mvn spring-boot:run
```

The application will:
1. Connect to MySQL
2. Create tables automatically (JPA auto-creates schema)
3. Start on port 8080

---

## 🧪 Verify Database Connection

### Check Tables Created

```bash
mysql -u root -p

USE employeedb;
SHOW TABLES;
DESC employees;
```

**Expected Output:**
```
+----------------------+
| Tables_in_employeedb |
+----------------------+
| employees            |
+----------------------+
```

### Query Data

```sql
SELECT * FROM employees;
```

---

## 🔍 Troubleshooting

### Error: "Access denied for user 'root'@'localhost'"

**Solution:** Update password in `application.properties`

```properties
spring.datasource.password=your_actual_password
```

### Error: "Communications link failure"

**Solution:** Ensure MySQL is running

```bash
# Check MySQL status
sudo systemctl status mysql

# Start MySQL
sudo systemctl start mysql

# Or if using Docker
docker start mysql-employee
```

### Error: "Unknown database 'employeedb'"

**Solution:** The app will create it automatically if the URL has `createDatabaseIfNotExist=true`

Or create manually:
```sql
CREATE DATABASE employeedb;
```

---

## 📊 Database Schema

Spring JPA will automatically create this table:

```sql
CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    salary DOUBLE NOT NULL,
    department VARCHAR(255) NOT NULL
);
```

---

## 🐳 Docker Compose (Optional)

Create `docker-compose.yml`:

```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    container_name: mysql-employee
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: employeedb
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql

volumes:
  mysql-data:
```

Run:
```bash
docker-compose up -d
```

---

## 🎯 Production Configuration

For production, use environment variables:

```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/employeedb}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:root}
```

Then set environment variables:
```bash
export DB_URL=jdbc:mysql://prod-server:3306/employeedb
export DB_USERNAME=prod_user
export DB_PASSWORD=secure_password
```

---

## 📝 Important Notes

1. **Auto-create Database:** The URL includes `createDatabaseIfNotExist=true` so the database will be created automatically

2. **Schema Updates:** `spring.jpa.hibernate.ddl-auto=update` means:
   - Creates tables if they don't exist
   - Updates schema when entities change
   - **Does NOT drop existing data**

3. **For Production:** Change `ddl-auto` to `validate` or `none`

---

## ✅ Verification Checklist

- [ ] MySQL is installed and running
- [ ] Database `employeedb` exists (or will be auto-created)
- [ ] Username/password configured correctly
- [ ] Application starts without errors
- [ ] Can create employees via API
- [ ] Data persists across restarts

---

**You're ready to use MySQL! 🚀**
