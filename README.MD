# Microservices-Based E-Commerce Backend

A scalable e-commerce backend built using Spring Boot Microservices, implementing service discovery, centralized authentication, API gateway routing, inter-service communication, and fault tolerance patterns.

---

## Architecture Overview

```text
                    +------------------+
                    |      Client      |
                    +--------+---------+
                             |
                             v
                    +------------------+
                    |   API Gateway    |
                    | JWT Validation   |
                    +--------+---------+
                             |
                             |
          +------------------+------------------+
          |                  |                  |
          v                  v                  v

 +----------------+  +----------------+  +----------------+
 | Auth Service   |  | Product Service|  | Order Service  |
 +----------------+  +----------------+  +----------------+
          |                  ^                  |
          |                  |                  |
          |                  +------------------+
          |                      OpenFeign
          |
          v

 +----------------------+
 | Eureka Server        |
 | Service Discovery    |
 +----------------------+
```

---

## Features

### Authentication Service

* User Registration
* User Login
* JWT Token Generation
* BCrypt Password Encryption
* Spring Security Integration

### Product Service

* Category Management
* Product Management
* Product Search
* Inventory Tracking
* Stock Management

### Order Service

* Order Creation
* Product Validation
* Stock Validation
* Inventory Deduction
* Inter-Service Communication using OpenFeign

### API Gateway

* Single Entry Point
* JWT Validation
* Request Routing
* User Context Propagation

### Service Discovery

* Eureka Server Registration
* Dynamic Service Discovery
* Load-Balancer Friendly Architecture

### Resilience

* Resilience4j Circuit Breaker
* Graceful Failure Handling
* Global Exception Handling

### Documentation

* Swagger/OpenAPI Integration
* Interactive API Testing

---

## Tech Stack

### Backend

* Java 24
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate

### Microservices

* Spring Cloud Gateway
* Netflix Eureka
* OpenFeign
* Resilience4j

### Database

* PostgreSQL

### Documentation

* Swagger / OpenAPI

### Build Tools

* Maven

---

## Microservices

| Service         | Port |
| --------------- | ---- |
| Eureka Server   | 8761 |
| API Gateway     | 8080 |
| Auth Service    | 8081 |
| Product Service | 8082 |
| Order Service   | 8083 |

---

## API Flow

### User Login

```text
Client
   ↓
API Gateway
   ↓
Auth Service
   ↓
JWT Token
```

### Product Retrieval

```text
Client
   ↓
Gateway
   ↓
Product Service
```

### Order Creation

```text
Client
   ↓
Gateway
   ↓
JWT Validation
   ↓
Order Service
   ↓
OpenFeign
   ↓
Product Service
   ↓
Stock Validation
   ↓
Inventory Deduction
   ↓
Order Creation
```

---

## Key Concepts Implemented

* Microservices Architecture
* API Gateway Pattern
* Service Discovery Pattern
* JWT Authentication
* Stateless Security
* Inter-Service Communication
* Circuit Breaker Pattern
* Database per Service Pattern
* DTO-Based Architecture
* Global Exception Handling
* RESTful API Design

---

## Getting Started

### Clone Repository

```bash
git clone https://github.com/n200534/microservices-ecommerce-backend.git
```

### Start Services

1. Start Eureka Server
2. Start Auth Service
3. Start Product Service
4. Start Order Service
5. Start API Gateway

### Access Services

#### Eureka Dashboard

```
http://localhost:8761
```

#### Swagger Documentation

Auth Service

```
http://localhost:8081/swagger-ui/index.html
```

Product Service

```
http://localhost:8082/swagger-ui/index.html
```

Order Service

```
http://localhost:8083/swagger-ui/index.html
```

---

## Future Enhancements

* Docker Containerization
* Kafka Event-Driven Communication
* Redis Caching
* Distributed Tracing
* Centralized Logging
* Kubernetes Deployment
* Payment Service Integration

---

## Author

Akshay Kumar Amavarapu

GitHub:
https://github.com/n200534
