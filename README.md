# Storey API

Create an API that acts as a store management tool.

## ✅ Features

- [x] Implement some functions, for example: `add-product`, `find-product`, `change-price`, or others  
- [x] Implement a basic authentication mechanism and role-based endpoint access  
- [x] Design error mechanism and handling plus logging  
- [x] Write unit tests, at least for one class  
- [x] Nice to have: use Java 17+ features  
- [x] Add a small README to document the project  

---

## 💡 Bonus Points

- [x] Use any relational database (Oracle Database 23ai)  
- [x] Use JPA/Hibernate  
- [x] SwaggerUI  
- [x] One integration test for an endpoint
- [ ] Kafka — simulate sending `ProductCreatedEvent` to analytics when a product is created  
- [x] Create Postman collection  

# Project details

### Features

- **Product & Category Management**
  - CRUD operations for `Product` and `Category` entities
  - DTOs for clean API data handling (Java 17 records)

- **Authentication & Authorization**
  - JWT token generation and validation
  - Role-based access control for endpoints

- **Error Handling & Logging**
  - Centralized `GlobalExceptionHandler` for consistent error responses

- **API Documentation**
  - Swagger UI with JWT authentication support for secure endpoint testing

- **Testing**
  - Unit tests for `ProductService` and `ProductController` using JUnit & Mockito
  - Integration tests for key endpoints using a runtime H2 test database
  - Dedicated `test` profile for isolation

- **Extras**
  - Postman Collection for testing all endpoints (requires customizing some values)
  - Maven project structure
  - Usage of modern Java 17+ features (records)

### Requirements

- Java 17+
- Maven
- Spring Boot
- H2 (test) & relational DB support (Oracle database 23ai)
- JPA/Hibernate

### Getting Started

1. Clone the repository
2. In order to run on local, you need to configure an Oracle 23ai database (which can be found here: https://www.oracle.com/database/free/). In 'storey/src/main/resources' there are some sql scripts which can be used to create the tables with some data.
3. Run the project:

   ```bash
   mvn spring-boot:run
4. Access to SwaggerUI: `http://localhost:8080/swagger-ui.html`
   
