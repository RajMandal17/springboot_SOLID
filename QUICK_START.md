# 🚀 Quick Start Guide

Get your Employee Management System running in 3 minutes!

---

## ⚡ Fast Setup

### Step 1: Verify Java & Maven
```bash
java -version    # Should be Java 17+
mvn -version     # Should be Maven 3.6+
```

### Step 2: Build the Project
```bash
cd /workspaces/springboot_SOLID
mvn clean install
```

### Step 3: Run the Application
```bash
mvn spring-boot:run
```

**Look for this message:**
```
✅ Employee Management System Started Successfully!
📍 Server running on: http://localhost:8080
🗄️ H2 Console: http://localhost:8080/h2-console
```

---

## 🧪 Test It Works

Open a new terminal and run:

```bash
# Create an employee
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "salary": 55000,
    "department": "Engineering"
  }'
```

**Success Response:**
```json
{
  "id": 1,
  "name": "Test User",
  "email": "test@example.com",
  "salary": 55000.0,
  "department": "Engineering"
}
```

---

## 📂 Project Structure (Visual)

```
springboot_SOLID/
│
├── 📄 pom.xml                          ← Maven dependencies
├── 📄 README.md                        ← Main documentation
├── 📄 API_TESTING.md                   ← API test commands
├── 📄 INTERVIEW_QA.md                  ← Interview Q&A
├── 📄 QUICK_START.md                   ← This file
│
└── src/main/
    ├── java/com/interview/employeemanagement/
    │   │
    │   ├── 📄 EmployeeManagementApplication.java    ← Main class
    │   │
    │   ├── 📁 controller/
    │   │   └── EmployeeController.java              ← REST API endpoints
    │   │
    │   ├── 📁 service/
    │   │   ├── EmployeeService.java                 ← Service interface
    │   │   └── EmployeeServiceImpl.java             ← Business logic
    │   │
    │   ├── 📁 repository/
    │   │   └── EmployeeRepository.java              ← Database access
    │   │
    │   ├── 📁 model/
    │   │   ├── Employee.java                        ← Entity class
    │   │   └── EmployeeBuilder.java                 ← Builder Pattern
    │   │
    │   ├── 📁 strategy/
    │   │   ├── SalaryStrategy.java                  ← Strategy interface
    │   │   ├── HighSalaryStrategy.java              ← High salary logic
    │   │   └── LowSalaryStrategy.java               ← Low salary logic
    │   │
    │   ├── 📁 factory/
    │   │   └── ServiceFactory.java                  ← Factory Pattern
    │   │
    │   └── 📁 exception/
    │       ├── ResourceNotFoundException.java       ← Custom exception
    │       └── GlobalExceptionHandler.java          ← Error handling
    │
    └── resources/
        └── application.properties                   ← Configuration
```

---

## 🎯 Key Files Explained

| File | Purpose | SOLID Principle |
|------|---------|-----------------|
| `EmployeeController.java` | HTTP endpoints | SRP - Only handles HTTP |
| `EmployeeServiceImpl.java` | Business logic | SRP - Only business rules |
| `EmployeeRepository.java` | Database ops | SRP - Only data access |
| `SalaryStrategy.java` | Salary calculation | OCP - Extensible |
| `ServiceFactory.java` | Object creation | SRP - Only creates objects |
| `GlobalExceptionHandler.java` | Error handling | SRP - Only handles errors |

---

## 🎨 Design Patterns Quick Reference

### 1️⃣ Builder Pattern
**File:** `EmployeeBuilder.java`
```java
Employee emp = new EmployeeBuilder()
    .name("John")
    .email("john@example.com")
    .salary(60000.0)
    .build();
```

### 2️⃣ Strategy Pattern
**Files:** `strategy/` package
```java
SalaryStrategy strategy = salary > 50000 
    ? new HighSalaryStrategy() 
    : new LowSalaryStrategy();
```

### 3️⃣ Factory Pattern
**File:** `ServiceFactory.java`
```java
EmployeeService service = ServiceFactory.getService(type, repo);
```

### 4️⃣ Repository Pattern
**File:** `EmployeeRepository.java`
```java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Spring Data JPA implements this automatically
}
```

---

## 📡 All API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/employees` | Create employee |
| GET | `/api/employees` | Get all employees |
| GET | `/api/employees/{id}` | Get by ID |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |
| GET | `/api/employees/department/{dept}` | Get by department |
| POST | `/api/employees/calculate-salary` | Calculate salary (Strategy Pattern) |

**Full examples:** See `API_TESTING.md`

---

## 🗄️ Access H2 Database Console

1. Go to: `http://localhost:8080/h2-console`
2. Use these settings:
   - **JDBC URL:** `jdbc:h2:mem:employeedb`
   - **Username:** `sa`
   - **Password:** *(leave empty)*
3. Click **Connect**
4. Run SQL: `SELECT * FROM employees;`

---

## 🎤 Quick Interview Demo

Run these commands in sequence:

```bash
# 1. Create employee
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","email":"alice@company.com","salary":55000,"department":"IT"}'

# 2. Show Strategy Pattern (High Salary = 20% bonus)
curl -X POST http://localhost:8080/api/employees/calculate-salary \
  -H "Content-Type: application/json" \
  -d '{"baseSalary":55000}'

# Output: {"baseSalary":55000.0,"adjustedSalary":66000.0}

# 3. Get all employees
curl -X GET http://localhost:8080/api/employees
```

---

## 🔍 Verify Everything Works

### ✅ Checklist

- [ ] Application starts without errors
- [ ] Can create employee (POST)
- [ ] Can retrieve employee (GET)
- [ ] Can calculate salary (Strategy Pattern works)
- [ ] Can access H2 console
- [ ] Global exception handler works (try GET /api/employees/999)

---

## 🎯 Interview Preparation

1. **Read:** `README.md` - Understand architecture
2. **Practice:** `API_TESTING.md` - Know all endpoints
3. **Study:** `INTERVIEW_QA.md` - Prepare for questions
4. **Explain:** SOLID principles for each class
5. **Demonstrate:** Run the application and explain code

---

## 🆘 Troubleshooting

### Port 8080 Already in Use
```bash
# Find and kill process
lsof -ti:8080 | xargs kill -9

# Or change port in application.properties
server.port=8081
```

### Maven Build Fails
```bash
# Clean Maven cache
mvn clean

# Skip tests
mvn clean install -DskipTests
```

### Java Version Issues
```bash
# Check Java version
java -version

# Should be Java 17 or higher
```

---

## 📚 Next Steps

1. ✅ Run the application
2. ✅ Test all APIs
3. ✅ Explore the code
4. ✅ Understand SOLID principles
5. ✅ Practice explaining design patterns
6. 🚀 Ace your interview!

---

## 🎓 Key Interview Points

When the interviewer asks about your project:

1. **"I built an Employee Management System using Spring Boot"**
2. **"Applied all 5 SOLID principles"**
3. **"Implemented 4 design patterns: Builder, Strategy, Factory, Repository"**
4. **"Used layered architecture for clean separation"**
5. **"Includes global exception handling and validation"**

---

**You're ready! 🚀 Good luck with your interview!**
