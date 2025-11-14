# 🧪 API Testing Guide

This guide provides ready-to-use curl commands for testing the Employee Management API.

## Quick Start

1. Start the application: `mvn spring-boot:run`
2. Wait for: "✅ Employee Management System Started Successfully!"
3. Use these commands to test

---

## 📋 Test Scenarios

### 1. Create Employee (POST)

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "salary": 60000,
    "department": "Engineering"
  }'
```

**Expected Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "salary": 60000.0,
  "department": "Engineering"
}
```

---

### 2. Create More Employees

```bash
# Employee 2
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "email": "jane@example.com",
    "salary": 45000,
    "department": "Marketing"
  }'

# Employee 3
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Bob Johnson",
    "email": "bob@example.com",
    "salary": 75000,
    "department": "Engineering"
  }'
```

---

### 3. Get All Employees (GET)

```bash
curl -X GET http://localhost:8080/api/employees
```

---

### 4. Get Employee by ID (GET)

```bash
curl -X GET http://localhost:8080/api/employees/1
```

---

### 5. Update Employee (PUT)

```bash
curl -X PUT http://localhost:8080/api/employees/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe Updated",
    "email": "john.doe@example.com",
    "salary": 70000,
    "department": "Senior Engineering"
  }'
```

---

### 6. Get Employees by Department (GET)

```bash
curl -X GET http://localhost:8080/api/employees/department/Engineering
```

---

### 7. Calculate Adjusted Salary (Strategy Pattern Demo)

**High Salary (> 50000) - Gets 20% bonus:**
```bash
curl -X POST http://localhost:8080/api/employees/calculate-salary \
  -H "Content-Type: application/json" \
  -d '{
    "baseSalary": 60000
  }'
```

**Expected Response:**
```json
{
  "baseSalary": 60000.0,
  "adjustedSalary": 72000.0
}
```

**Low Salary (<= 50000) - Gets 10% adjustment:**
```bash
curl -X POST http://localhost:8080/api/employees/calculate-salary \
  -H "Content-Type: application/json" \
  -d '{
    "baseSalary": 40000
  }'
```

**Expected Response:**
```json
{
  "baseSalary": 40000.0,
  "adjustedSalary": 36000.0
}
```

---

### 8. Delete Employee (DELETE)

```bash
curl -X DELETE http://localhost:8080/api/employees/1
```

**Expected Response:**
```json
{
  "message": "Employee deleted successfully"
}
```

---

## 🔍 Test Error Handling

### Employee Not Found (404)

```bash
curl -X GET http://localhost:8080/api/employees/999
```

**Expected Response:**
```json
{
  "timestamp": "2025-11-14T...",
  "status": 404,
  "error": "Not Found",
  "message": "Employee not found with id: 999",
  "path": "/api/employees/999"
}
```

---

### Invalid Data (400)

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "",
    "email": "invalid-email",
    "salary": -1000,
    "department": ""
  }'
```

**Expected Response:**
```json
{
  "timestamp": "2025-11-14T...",
  "status": 400,
  "error": "Bad Request",
  "message": "Employee name cannot be empty",
  "path": "/api/employees"
}
```

---

## 🎯 Interview Demo Flow

Use this sequence to demonstrate the project:

```bash
# 1. Create first employee
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","email":"alice@company.com","salary":55000,"department":"IT"}'

# 2. Create second employee
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{"name":"Bob","email":"bob@company.com","salary":48000,"department":"HR"}'

# 3. Show all employees
curl -X GET http://localhost:8080/api/employees

# 4. Demonstrate Strategy Pattern (High Salary)
curl -X POST http://localhost:8080/api/employees/calculate-salary \
  -H "Content-Type: application/json" \
  -d '{"baseSalary":55000}'

# 5. Demonstrate Strategy Pattern (Low Salary)
curl -X POST http://localhost:8080/api/employees/calculate-salary \
  -H "Content-Type: application/json" \
  -d '{"baseSalary":48000}'

# 6. Filter by department
curl -X GET http://localhost:8080/api/employees/department/IT

# 7. Update employee
curl -X PUT http://localhost:8080/api/employees/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice Updated","email":"alice@company.com","salary":60000,"department":"IT"}'

# 8. Delete employee
curl -X DELETE http://localhost:8080/api/employees/2
```

---

## 🌐 Postman Alternative

If you prefer Postman, import this collection:

1. Create new Collection: "Employee Management API"
2. Add requests with these details:
   - Base URL: `http://localhost:8080/api/employees`
   - Content-Type: `application/json`
   - Body: Use JSON examples above

---

## 🗄️ H2 Database Console

Access: `http://localhost:8080/h2-console`

**Settings:**
- Driver Class: `org.h2.Driver`
- JDBC URL: `jdbc:h2:mem:employeedb`
- User Name: `sa`
- Password: *(leave empty)*

**Sample Query:**
```sql
SELECT * FROM employees;
```

---

## 📊 Expected Behavior

| Endpoint | Status Code | Response Type |
|----------|-------------|---------------|
| POST /api/employees | 201 Created | Employee object |
| GET /api/employees | 200 OK | Array of employees |
| GET /api/employees/{id} | 200 OK | Employee object |
| PUT /api/employees/{id} | 200 OK | Updated employee |
| DELETE /api/employees/{id} | 200 OK | Success message |
| GET /api/employees/999 | 404 Not Found | Error object |
| POST (invalid data) | 400 Bad Request | Error object |

---

## 🎤 Interview Talking Points

While demonstrating:

1. **Builder Pattern:** "Notice how I create Employee objects cleanly using the Builder"
2. **Strategy Pattern:** "The salary calculation uses different strategies based on amount"
3. **Exception Handling:** "Global exception handler provides consistent error responses"
4. **SOLID:** "Each layer has one responsibility - Controller handles HTTP, Service handles logic"

---

**Happy Testing! 🚀**
