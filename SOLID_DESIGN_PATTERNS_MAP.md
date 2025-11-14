# 🎯 SOLID Principles & Design Patterns - Implementation Map

This document maps where each SOLID principle and design pattern is implemented in the codebase.

---

## ✅ SOLID Principles Implementation

### 1. Single Responsibility Principle (SRP)

Each class has ONE and only ONE reason to change.

| Class | Single Responsibility | What It Does NOT Do |
|-------|----------------------|---------------------|
| **EmployeeController** | Handle HTTP requests/responses | ❌ No business logic<br>❌ No database access |
| **EmployeeServiceImpl** | Business logic & validation | ❌ No HTTP handling<br>❌ No direct DB queries |
| **EmployeeRepository** | Database operations | ❌ No validation<br>❌ No HTTP responses |
| **GlobalExceptionHandler** | Exception handling | ❌ No business logic<br>❌ No data processing |
| **HighSalaryStrategy** | High salary calculation | ❌ Only one algorithm |
| **LowSalaryStrategy** | Low salary calculation | ❌ Only one algorithm |
| **EmployeeBuilder** | Object construction | ❌ Only builds objects |
| **ServiceFactory** | Service creation | ❌ Only creates objects |

**Interview Tip:** Point to any class and explain its single responsibility.

---

### 2. Open/Closed Principle (OCP)

Open for extension, closed for modification.

#### ✅ Example 1: Strategy Pattern

**Current Code:**
```java
public interface SalaryStrategy {
    double calculate(double baseSalary);
}
```

**Extension (NO modification needed):**
```java
// Just add a new class, don't modify existing code
public class PartTimeSalaryStrategy implements SalaryStrategy {
    public double calculate(double baseSalary) {
        return baseSalary * 0.5;
    }
}

public class ContractorSalaryStrategy implements SalaryStrategy {
    public double calculate(double baseSalary) {
        return baseSalary * 0.8;
    }
}
```

#### ✅ Example 2: Service Layer

**Can extend without modifying:**
```java
// Add caching without touching EmployeeServiceImpl
public class CachedEmployeeServiceImpl implements EmployeeService {
    private final EmployeeService delegate;
    // ... caching logic
}
```

**Interview Tip:** "I can add new features by creating new classes, not modifying existing ones."

---

### 3. Liskov Substitution Principle (LSP)

Subtypes must be substitutable for their base types.

#### ✅ Example: Service Interface

```java
// Base interface
public interface EmployeeService {
    Employee createEmployee(Employee employee);
    Employee getEmployeeById(Long id);
}

// Implementation 1
public class EmployeeServiceImpl implements EmployeeService {
    // Standard implementation
}

// Implementation 2 (can replace Implementation 1)
public class CachedEmployeeServiceImpl implements EmployeeService {
    // With caching
}
```

**Controller doesn't care which implementation:**
```java
@RestController
public class EmployeeController {
    private final EmployeeService service; // Works with ANY implementation
}
```

#### ✅ Example: Strategy Pattern

```java
SalaryStrategy strategy = new HighSalaryStrategy();
// Can be replaced with:
SalaryStrategy strategy = new LowSalaryStrategy();
// Code still works correctly
```

**Interview Tip:** "Any implementation can replace another without breaking the system."

---

### 4. Interface Segregation Principle (ISP)

No client should be forced to depend on methods it doesn't use.

#### ✅ Small, Focused Interfaces

**Good (Current):**
```java
// Small interface - only 2 methods
public interface SalaryStrategy {
    double calculate(double baseSalary);
    String getStrategyName();
}
```

**Bad (Avoided):**
```java
// BAD: Fat interface with unrelated methods
public interface EmployeeOperations {
    // Employee CRUD
    Employee create(Employee e);
    Employee read(Long id);
    
    // Salary calculation
    double calculateSalary(double base);
    
    // Reporting
    Report generateReport();
    
    // Email
    void sendEmail(String email);
}
// ❌ Classes implementing this must implement ALL methods!
```

**Our interfaces:**
- `EmployeeService` - Only employee operations
- `SalaryStrategy` - Only salary calculations
- `EmployeeRepository` - Only data access

**Interview Tip:** "Each interface has a focused purpose. No unnecessary methods."

---

### 5. Dependency Inversion Principle (DIP)

Depend on abstractions, not concretions.

#### ✅ Example 1: Controller → Service

**High-level module (Controller) depends on abstraction:**
```java
@RestController
public class EmployeeController {
    // Depends on interface (abstraction), not implementation
    private final EmployeeService employeeService;
    
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
```

**Spring injects the concrete implementation at runtime.**

#### ✅ Example 2: Service → Repository

**Service depends on repository interface:**
```java
@Service
public class EmployeeServiceImpl implements EmployeeService {
    // Depends on interface
    private final EmployeeRepository repository;
    
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }
}
```

**Benefits:**
- ✅ Easy to test (mock interfaces)
- ✅ Easy to swap implementations
- ✅ Loose coupling

**Interview Tip:** "High-level modules depend on abstractions. Spring injects implementations."

---

## 🎨 Design Patterns Implementation

### 1️⃣ Builder Pattern

**Purpose:** Construct complex objects step by step

**Location:** `model/EmployeeBuilder.java`

**Implementation:**
```java
public class EmployeeBuilder {
    private String name;
    private String email;
    private Double salary;
    private String department;

    public EmployeeBuilder name(String name) {
        this.name = name;
        return this;  // Return this for method chaining
    }

    public EmployeeBuilder email(String email) {
        this.email = email;
        return this;
    }

    public Employee build() {
        return new Employee(name, email, salary, department);
    }
}
```

**Usage in Controller:**
```java
Employee employee = new EmployeeBuilder()
    .name("John Doe")
    .email("john@example.com")
    .salary(60000.0)
    .department("Engineering")
    .build();
```

**Benefits:**
- ✅ Readable code
- ✅ Flexible construction
- ✅ Immutable objects
- ✅ Better than constructors with many parameters

**When to use:**
- Objects with many fields (4+)
- Optional parameters
- Step-by-step construction

**Interview Tip:** "Builder makes object creation clean and readable, especially for complex objects."

---

### 2️⃣ Strategy Pattern

**Purpose:** Define a family of algorithms, encapsulate each one, and make them interchangeable

**Location:** `strategy/` package

**Components:**

1. **Strategy Interface:**
```java
public interface SalaryStrategy {
    double calculate(double baseSalary);
}
```

2. **Concrete Strategies:**
```java
public class HighSalaryStrategy implements SalaryStrategy {
    public double calculate(double baseSalary) {
        return baseSalary * 1.2; // 20% bonus
    }
}

public class LowSalaryStrategy implements SalaryStrategy {
    public double calculate(double baseSalary) {
        return baseSalary * 0.9; // 10% adjustment
    }
}
```

3. **Context (Service):**
```java
public double applySalaryStrategy(double baseSalary) {
    // Select strategy at runtime
    SalaryStrategy strategy = baseSalary > 50000
        ? new HighSalaryStrategy()
        : new LowSalaryStrategy();
    
    return strategy.calculate(baseSalary);
}
```

**Benefits:**
- ✅ Follows Open/Closed Principle
- ✅ Easy to add new strategies
- ✅ Eliminates conditional statements
- ✅ Runtime algorithm selection

**When to use:**
- Multiple algorithms for same operation
- Need to switch algorithms at runtime
- Want to avoid if-else chains

**Interview Tip:** "Strategy pattern makes the system extensible. Adding new salary rules is just creating a new class."

---

### 3️⃣ Factory Pattern

**Purpose:** Create objects without specifying exact class

**Location:** `factory/ServiceFactory.java`

**Implementation:**
```java
public class ServiceFactory {
    public static EmployeeService getService(String type, EmployeeRepository repo) {
        if (type.equalsIgnoreCase("standard")) {
            return new EmployeeServiceImpl(repo);
        }
        // Future: add more types
        // if (type.equalsIgnoreCase("premium")) {
        //     return new PremiumEmployeeServiceImpl(repo);
        // }
        throw new IllegalArgumentException("Invalid service type");
    }
}
```

**Usage:**
```java
EmployeeService service = ServiceFactory.getService("standard", repository);
```

**Benefits:**
- ✅ Centralizes object creation
- ✅ Hides implementation details
- ✅ Easy to add new types
- ✅ Runtime type selection

**When to use:**
- Object creation logic is complex
- Need to create different types at runtime
- Want to encapsulate creation logic

**Spring Alternative:**
In Spring, we use `@Service` and dependency injection instead, but Factory is still important for:
- Non-Spring beans
- Testing scenarios
- Complex creation logic

**Interview Tip:** "Factory centralizes object creation. In Spring, we often use DI, but Factory is useful for complex scenarios."

---

### 4️⃣ Repository Pattern

**Purpose:** Abstract data access logic

**Location:** `repository/EmployeeRepository.java`

**Implementation:**
```java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Spring Data JPA provides implementation automatically
    
    // Custom query methods
    Optional<Employee> findByEmail(String email);
    List<Employee> findByDepartment(String department);
}
```

**Benefits:**
- ✅ Separates business logic from data access
- ✅ Spring Data JPA auto-implements
- ✅ Easy to test (mock repository)
- ✅ Database-agnostic

**What Spring provides automatically:**
- `save()`, `findAll()`, `findById()`, `delete()`, etc.
- Transaction management
- Exception translation

**When to use:**
- All database operations
- Need abstraction over data source
- Want testable data access

**Interview Tip:** "Repository pattern abstracts data access. Spring Data JPA implements it automatically, saving a lot of boilerplate code."

---

## 📊 Pattern vs SOLID Principle Matrix

| Design Pattern | SOLID Principles Applied |
|----------------|--------------------------|
| **Builder** | SRP (only builds objects) |
| **Strategy** | OCP (extensible), SRP (each strategy has one job), ISP (small interface) |
| **Factory** | SRP (only creates objects), OCP (can add new types) |
| **Repository** | SRP (only data access), DIP (service depends on interface) |

---

## 🎯 Quick Reference: Where Is Each Principle?

### Find SRP:
- Look at ANY class - it has one responsibility
- `EmployeeController` - HTTP only
- `EmployeeServiceImpl` - Business logic only

### Find OCP:
- `strategy/` package - can add strategies without modifying code
- Service interface - can add implementations

### Find LSP:
- `EmployeeService` interface - any implementation can replace another
- `SalaryStrategy` interface - strategies are interchangeable

### Find ISP:
- `SalaryStrategy` interface - only 2 methods, focused
- `EmployeeService` interface - only employee operations

### Find DIP:
- `EmployeeController` - depends on `EmployeeService` (interface)
- `EmployeeServiceImpl` - depends on `EmployeeRepository` (interface)

---

## 💡 Interview Scenario: Adding New Feature

**Interviewer asks:** "How would you add overtime pay calculation?"

**Answer (demonstrating SOLID + Patterns):**

1. **Create new Strategy (OCP, SRP):**
```java
public class OvertimePayStrategy implements SalaryStrategy {
    public double calculate(double baseSalary) {
        return baseSalary * 1.3; // 30% overtime bonus
    }
}
```

2. **No modification to existing code (OCP):**
- No changes to `EmployeeServiceImpl`
- No changes to other strategies
- Just add new class

3. **Usage:**
```java
SalaryStrategy strategy = new OvertimePayStrategy();
double overtimePay = strategy.calculate(50000);
```

**This demonstrates:**
- ✅ Open/Closed Principle
- ✅ Single Responsibility
- ✅ Strategy Pattern
- ✅ No breaking changes

---

## 🏆 Summary: Why This Architecture Rocks

| Benefit | How We Achieved It |
|---------|-------------------|
| **Maintainable** | Each class has one job (SRP) |
| **Extensible** | Can add features without modifying code (OCP) |
| **Testable** | Depend on interfaces (DIP) |
| **Flexible** | Implementations are interchangeable (LSP) |
| **Clean** | Small, focused interfaces (ISP) |
| **Readable** | Builder pattern, clear naming |
| **Scalable** | Layered architecture |

---

**This is exactly what interviewers want to see! 🚀**
