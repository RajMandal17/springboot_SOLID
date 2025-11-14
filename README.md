# 🚀 Employee Management System - Interview-Ready Project

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![SOLID](https://img.shields.io/badge/Principles-SOLID-blue.svg)](https://en.wikipedia.org/wiki/SOLID)
[![Design Patterns](https://img.shields.io/badge/Design-Patterns-purple.svg)](https://refactoring.guru/design-patterns)

> **A complete Spring Boot REST API demonstrating SOLID principles and Design Patterns**  
> Perfect for **3-year Java Developer** interviews

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [Architecture](#-architecture)
- [SOLID Principles Applied](#-solid-principles-applied)
- [Design Patterns Used](#-design-patterns-used)
- [Project Structure](#-project-structure)
- [Setup & Run](#-setup--run)
- [API Endpoints](#-api-endpoints)
- [Interview Talking Points](#-interview-talking-points)

---

## 🎯 Project Overview

This is a **production-ready Employee Management System** built with:

✅ **Spring Boot 3.2.0**  
✅ **RESTful API** with complete CRUD operations  
✅ **Layered Architecture** (Controller → Service → Repository)  
✅ **SOLID Principles** at every layer  
✅ **4 Design Patterns** (Builder, Strategy, Factory, Repository)  
✅ **Global Exception Handling**  
✅ **H2 In-Memory Database** (easy to demo)

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────┐
│          REST API (JSON)                        │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│     CONTROLLER LAYER                            │
│  EmployeeController.java                        │
│  → Handles HTTP requests only                   │
│  → Uses Builder Pattern                         │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│     SERVICE LAYER                               │
│  EmployeeService (Interface)                    │
│  EmployeeServiceImpl (Implementation)           │
│  → Business logic                               │
│  → Uses Strategy Pattern for salary             │
│  → Validation                                   │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│     REPOSITORY LAYER                            │
│  EmployeeRepository (Spring Data JPA)           │
│  → Database operations only                     │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│     DATABASE (H2)                               │
│  employees table                                │
└─────────────────────────────────────────────────┘
```

---

## ⭐ SOLID Principles Applied

### 🔹 S - Single Responsibility Principle

**Each class has ONE job:**

| Class | Responsibility |
|-------|---------------|
| `EmployeeController` | Handle HTTP requests/responses only |
| `EmployeeServiceImpl` | Business logic only |
| `EmployeeRepository` | Database operations only |
| `GlobalExceptionHandler` | Exception handling only |
| `SalaryStrategy` | Salary calculation only |

**Interview Answer:**  
*"Every class has a single, well-defined purpose. Controller doesn't do business logic, Service doesn't handle HTTP, Repository doesn't validate."*

---

### 🔹 O - Open/Closed Principle

**Code is open for extension, closed for modification**

Example: **Strategy Pattern for Salary Calculation**

```java
// Adding new salary strategy doesn't modify existing code
public class PremiumSalaryStrategy implements SalaryStrategy {
    public double calculate(double baseSalary) {
        return baseSalary * 1.5; // 50% bonus
    }
}
```

**Interview Answer:**  
*"I can add new salary strategies without changing existing service code. Just implement the SalaryStrategy interface."*

---

### 🔹 L - Liskov Substitution Principle

**Any implementation of EmployeeService can replace another**

```java
EmployeeService service = new EmployeeServiceImpl(repository);
// Can be replaced with:
EmployeeService service = new CachedEmployeeServiceImpl(repository);
// No breaking changes
```

**Interview Answer:**  
*"Controller depends on EmployeeService interface. I can swap implementations without breaking the application."*

---

### 🔹 I - Interface Segregation Principle

**Small, focused interfaces**

```java
public interface SalaryStrategy {
    double calculate(double baseSalary);
    String getStrategyName();
}
```

**Interview Answer:**  
*"Interfaces are small and focused. SalaryStrategy only has methods related to salary calculation."*

---

### 🔹 D - Dependency Inversion Principle

**Depend on abstractions, not implementations**

```java
@RestController
public class EmployeeController {
    private final EmployeeService employeeService; // Interface, not implementation
    
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
```

**Interview Answer:**  
*"Controller depends on EmployeeService interface. Spring injects the implementation at runtime. Easy to test and swap."*

---

## 🎨 Design Patterns Used

### 1️⃣ Builder Pattern

**Purpose:** Clean, readable object creation

**Location:** `EmployeeBuilder.java`

```java
Employee employee = new EmployeeBuilder()
    .name("John Doe")
    .email("john@example.com")
    .salary(60000.0)
    .department("Engineering")
    .build();
```

**Why?** Makes code readable, especially for objects with many fields.

---

### 2️⃣ Strategy Pattern

**Purpose:** Different algorithms for salary calculation

**Location:** `strategy/` package

```java
public interface SalaryStrategy {
    double calculate(double baseSalary);
}

// High salary gets 20% bonus
public class HighSalaryStrategy implements SalaryStrategy {
    public double calculate(double baseSalary) {
        return baseSalary * 1.2;
    }
}

// Low salary gets 10% adjustment
public class LowSalaryStrategy implements SalaryStrategy {
    public double calculate(double baseSalary) {
        return baseSalary * 0.9;
    }
}
```

**Why?** Extensible, follows Open/Closed Principle.

---

### 3️⃣ Factory Pattern

**Purpose:** Choose which service to create

**Location:** `ServiceFactory.java`

```java
public class ServiceFactory {
    public static EmployeeService getService(String type, EmployeeRepository repo) {
        if(type.equals("standard")) return new EmployeeServiceImpl(repo);
        if(type.equals("premium")) return new PremiumEmployeeServiceImpl(repo);
        throw new IllegalArgumentException("Invalid type");
    }
}
```

**Why?** Centralizes object creation logic.

---

### 4️⃣ Repository Pattern

**Purpose:** Abstract database operations

**Location:** `EmployeeRepository.java`

```java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    List<Employee> findByDepartment(String department);
}
```

**Why?** Spring Data JPA implements this automatically. Clean separation of concerns.

---

## 📁 Project Structure

```
src/main/java/com/interview/employeemanagement/
│
├── EmployeeManagementApplication.java    # Main class
│
├── controller/
│   └── EmployeeController.java           # REST endpoints
│
├── service/
│   ├── EmployeeService.java              # Service interface
│   └── EmployeeServiceImpl.java          # Service implementation
│
├── repository/
│   └── EmployeeRepository.java           # Spring Data JPA
│
├── model/
│   ├── Employee.java                     # Entity
│   └── EmployeeBuilder.java              # Builder Pattern
│
├── strategy/
│   ├── SalaryStrategy.java               # Strategy interface
│   ├── HighSalaryStrategy.java           # High salary logic
│   └── LowSalaryStrategy.java            # Low salary logic
│
├── factory/
│   └── ServiceFactory.java               # Factory Pattern
│
└── exception/
    ├── ResourceNotFoundException.java    # Custom exception
    └── GlobalExceptionHandler.java       # Global error handling
```

---

## 🚀 Setup & Run

### Prerequisites

- Java 17+
- Maven 3.6+

### Steps

1. **Clone the repository**
   ```bash
   cd /workspaces/springboot_SOLID
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API: `http://localhost:8080/api/employees`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:employeedb`
     - Username: `sa`
     - Password: *(leave blank)*

---

## 📡 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/employees` | Create new employee |
| `GET` | `/api/employees` | Get all employees |
| `GET` | `/api/employees/{id}` | Get employee by ID |
| `PUT` | `/api/employees/{id}` | Update employee |
| `DELETE` | `/api/employees/{id}` | Delete employee |
| `GET` | `/api/employees/department/{dept}` | Get by department |
| `POST` | `/api/employees/calculate-salary` | Calculate adjusted salary |

### Example Requests

**Create Employee (POST /api/employees)**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "salary": 60000,
  "department": "Engineering"
}
```

**Calculate Salary (POST /api/employees/calculate-salary)**
```json
{
  "baseSalary": 60000
}
```

**Response:**
```json
{
  "baseSalary": 60000.0,
  "adjustedSalary": 72000.0
}
```

---

## 💼 Interview Talking Points

### When interviewer asks: **"How did you apply SOLID principles?"**

**Answer:**

✅ **SRP:** Each layer has one responsibility  
✅ **OCP:** Strategy Pattern allows adding new salary logic without modifying existing code  
✅ **LSP:** Service implementations are interchangeable  
✅ **ISP:** Small, focused interfaces like `SalaryStrategy`  
✅ **DIP:** Controller depends on `EmployeeService` interface, not implementation

---

### When interviewer asks: **"Why did you use these design patterns?"**

**Answer:**

✅ **Builder:** Clean object creation with many fields  
✅ **Strategy:** Extensible business logic for salary calculation  
✅ **Factory:** Centralized service selection logic  
✅ **Repository:** Spring Data JPA abstracts database operations

---

### When interviewer asks: **"How would you handle a new requirement?"**

**Example:** *"Add part-time employees with different salary calculation"*

**Answer:**

1. Create `PartTimeSalaryStrategy` implementing `SalaryStrategy`
2. No changes to existing code (Open/Closed Principle)
3. Add new strategy in `ServiceFactory` if needed

```java
public class PartTimeSalaryStrategy implements SalaryStrategy {
    public double calculate(double baseSalary) {
        return baseSalary * 0.5; // Part-time gets 50%
    }
}
```

---

## 🎓 Key Takeaways

| Concept | Implementation |
|---------|---------------|
| **Layered Architecture** | Controller → Service → Repository |
| **Dependency Injection** | Spring @Autowired |
| **Exception Handling** | @RestControllerAdvice |
| **Data Validation** | Service layer validation |
| **Clean Code** | Interfaces, separation of concerns |
| **Testability** | Each layer can be tested independently |

---

## 📚 Technologies Used

- **Spring Boot 3.2.0** - Framework
- **Spring Data JPA** - ORM
- **H2 Database** - In-memory database
- **Lombok** - Reduce boilerplate
- **Maven** - Build tool
- **Java 17** - Language

---

## 🏆 Why This Project is Interview-Ready

✅ Demonstrates **3 years of experience** level understanding  
✅ Shows **SOLID principles** in real code  
✅ Uses **4 industry-standard design patterns**  
✅ **Clean architecture** that's maintainable  
✅ **Production-like** exception handling  
✅ Easy to explain and defend in interviews  
✅ Can run and demo in real-time  

---

## 📞 Interview Tips

1. **Be ready to explain SOLID** for each class
2. **Know why you chose each pattern** (not just what it is)
3. **Show how to extend** the system without breaking existing code
4. **Discuss testability** - each layer can be unit tested
5. **Explain trade-offs** - when NOT to use patterns

---

## 🌟 Next Steps (Optional Enhancements)

- Add unit tests with JUnit & Mockito
- Add DTOs (Data Transfer Objects)
- Add pagination for large datasets
- Add authentication (Spring Security)
- Add Swagger/OpenAPI documentation
- Deploy to cloud (AWS/Azure)

---

## 📝 License

This is an educational project for interview preparation.

---

## 👨‍💻 Author

Built for **Java Developer Interview Preparation**

**Good luck with your interviews! 🚀**