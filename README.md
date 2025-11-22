# 🎓 EduConnect - Multi-Tenant SaaS Platform

EduConnect is a robust, **Schema-based Multi-Tenant SaaS platform** designed for educational centers, academies, and bootcamps. It provides complete isolation of data between tenants while maintaining a shared codebase and deployment.

Built with **Spring Boot 3**, **PostgreSQL**, and secured with **JWT**, this project demonstrates a production-ready architecture focusing on scalability, security, and rigorous testing.

---

## 🚀 Tech Stack

* **Core:** Java 21, Spring Boot 3.5.6
* **Database:** PostgreSQL 16
* **Architecture:** Multi-Tenancy (Database-per-tenant / Schema-based)
* **Security:** Spring Security, JWT (Access & Refresh Tokens)
* **Data Access:** Spring Data JPA, Hibernate
* **Migration:** Flyway (Programmatic schema migration for new tenants)
* **Mapping:** MapStruct
* **Testing:** JUnit 5, Mockito, **Testcontainers**, AssertJ
* **Tools:** Maven, Lombok, Docker

---

## 🏗️ Architecture Highlights

### 1. Multi-Tenancy Strategy (Schema-based)
EduConnect uses a **Schema-per-Tenant** approach to ensure maximum data isolation and security.
* **Public Schema:** Stores global data (Tenants, Users, Refresh Tokens).
* **Tenant Schemas:** Each educational center (Tenant) gets its own dedicated schema (e.g., `tenant_academy1`, `tenant_bootcamp2`) containing their specific data (Students, Courses, Payments).
* **Dynamic Resolution:** The application automatically resolves the current tenant based on:
    * `X-Tenant-ID` Header
    * Subdomain (e.g., `academy.educonnect.com`)
    * JWT Claims

### 2. Security & Auth
* Stateless authentication using **JWT (JSON Web Tokens)**.
* Role-Based Access Control (RBAC) with roles: `ADMIN`, `TEACHER`, `STUDENT`.
* **Method Level Security:** Custom `@PreAuthorize` logic using `AuthorizationService` to check resource ownership.

### 3. Database Migrations
* **Public Schema:** Migrated automatically on startup.
* **Tenant Schemas:** Migrated **programmatically** at runtime when a new tenant is registered via `TenantManagementService`.

---

## 🧪 Testing Strategy

The project follows the **Testing Pyramid** principle with a strong focus on reliability.

### ✅ Integration Tests (`@DataJpaTest` + Testcontainers)
Real PostgreSQL instances are spun up in Docker containers to test the Data Persistence layer.
* Ensures repositories work correctly with actual SQL dialects.
* Verifies Flyway migrations in a test environment.

### ✅ Slice Tests (`@WebMvcTest`)
Isolated tests for the Web Layer using `MockMvc`.
* Tests HTTP endpoints, request validation, and JSON serialization.
* Verifies Security filters and authorization rules (`@WithMockUser`).

### ✅ Unit Tests (Mockito)
Pure Java tests for the Service Layer.
* Verifies business logic in isolation using `Mockito` and `AssertJ`.

---

## 🛠️ Key Features

* **Tenant Management:** Create and manage educational centers dynamically.
* **User Management:** Secure registration and login with password encryption (BCrypt).
* **Course Management:** CRUD operations with search capabilities (`JPQL` with dynamic filtering).
* **Enrollment & Payments:** Track student enrollments and payments status.
* **Statistics:** Aggregated data views for tenant admins.

---

## 🏁 Getting Started

### Prerequisites
* Java 17 or higher
* Docker (required for Testcontainers)
* PostgreSQL (for local development)

### Installation

1.  **Clone the repository**
    bash
    git clone [https://github.com/your-username/EduConnect.git](https://github.com/your-username/EduConnect.git)
    cd EduConnect

2.  **Configure Database**
    Update `src/main/resources/application.properties` with your local PostgreSQL credentials.

3.  **Run Tests**
    bash
    ./mvnw test
    

4.  **Run Application**
    bash
    ./mvnw spring-boot:run
