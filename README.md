# 🚀 LinkedIn Backend (Microservices)

## 📌 Overview
**linkedin-backend** is a **LinkedIn-like backend system** built using **Spring Boot** and **Spring Cloud**, following a **microservices architecture**.

The primary goal of this project is to **practice real-world backend system design**, focusing on:
- Scalability
- Service discovery
- Gateway-level security
- Clean service-to-service communication

> ⚠️ **Note:** This is a learning-focused project created to understand **enterprise-level backend architecture and patterns** used in real production systems.

---

## 🏗️ Architecture Overview

The system follows a **gateway-centric microservices architecture** where **all client traffic flows through the API Gateway**.

```
Client
  |
  v
API Gateway
(Authentication & Authorization)
  |
  +----------------------+
  |                      |
Discovery Server     Registered Services
(Eureka)                  |
                            |
        +-------------------+-------------------+
        |                   |                   |
  User Service        Posts Service       Connection Service
   (Postgres)          (Postgres)              (Neo4j)
```

---

## 🧩 Key Features
- Service discovery using **Eureka**
- Centralized routing via **Spring Cloud Gateway**
- JWT-based authentication & authorization at API Gateway
- Dynamic service registration (no hardcoded URLs)
- Database-per-service design
- Inter-service communication using **OpenFeign**
- Clean layered architecture inside each service
- Fully containerized using Docker & Docker Compose
- Feature-based Git workflow

---

## 🔐 Authentication & Authorization

**Implemented at:** `API Gateway`

### Why Gateway-level Security?
- Centralized access control
- No duplicate security logic across services
- Internal services remain focused on business logic
- Industry-standard approach

### Responsibilities:
- JWT-based authentication at API Gateway
- Token validation before request forwarding
- Injection of authenticated user context (`X-User-Id`) into downstream services
- Blocking unauthorized requests at gateway level

> Internal microservices trust the API Gateway and are **not directly exposed to clients**.

---

## 🔐 JWT Security Flow

1. User authenticates via `user-service` and receives a JWT token
2. Client sends the token in `Authorization: Bearer <token>` header
3. API Gateway validates the JWT signature and expiration
4. On successful validation, Gateway injects `X-User-Id` header
5. Downstream services trust the gateway and do not parse JWT
6. Each service extracts user context from request headers

---

## 🔁 Inter-Service Communication (OpenFeign)

The project uses **Spring Cloud OpenFeign** for service-to-service communication.

- Services communicate using **service names registered in Eureka**
- No hardcoded service URLs are used
- HTTP calls are abstracted as declarative Java interfaces
- Authenticated user context (`X-User-Id`) is automatically propagated via headers

This keeps inter-service communication **clean, readable, and scalable**.

---

## 🔔 Messaging (Apache Kafka)

The system uses **Apache Kafka** for **asynchronous, event-driven communication** between microservices.

Kafka is used for **side effects** like notifications, keeping core business flows **decoupled and non-blocking**.

---

## 🔁 Messaging Flow
```
Connection Service
      |
      | (SendConnectionRequestEvent)
      v
 Kafka Topic
      |
      v
Notification Service
      |
      v
Notification Saved
```
---


## 📌 Implemented Use Case

**Connection Request Notification**

- `connection-service` publishes an event when a connection request is sent
- `notification-service` consumes the event and stores a notification



## 🧩 Kafka Topic

| Topic                         | Producer           | Consumer             |
|-------------------------------|--------------------|----------------------|
| send-connection-request-topic | connection-service | notification-service |



## 🧠 Design Notes

- Messaging is asynchronous
- Services are loosely coupled
- Notification failures do not affect core flows

---

## 🛠️ Tech Stack
- **Java 17**
- **Spring Boot**
- **Spring Cloud (Eureka, Gateway, OpenFeign)**
- **Spring Data JPA**
- **Maven**
- **PostgreSQL**
- **Neo4j**
- **Docker & Docker Compose**
- **Apache Kafka (KRaft mode)**
- **Zipkin** (planned)

---

## 🧩 Microservices

### 🔹 Discovery Server
**Module:** `discovery-server`

- Central service registry
- Enables dynamic service discovery
- Required for gateway-based routing

**Port:** `8761`  
**Dashboard:** http://localhost:8761

---

### 🔹 API Gateway
**Module:** `api-gateway`

- Single entry point for all client requests
- Routes requests using service IDs via Eureka
- Handles authentication & authorization
- Registered as a Eureka client

---

### 🔹 User Service
**Module:** `user-service`  
**Database:** PostgreSQL

**Responsibilities:**
- User management
- Password handling utilities
- Business logic related to users

**Structure:**
```
config
controller
dto
entity
repository
service
globalException
utils (hashPassword, checkPassword)
```

---

### 🔹 Posts Service
**Module:** `posts-service`  
**Database:** PostgreSQL

**Responsibilities:**
- Post creation and management
- DTO-based API design
- Clean layered architecture
- User context propagation using request interceptors

**Structure:**
```
config
controller
dto
entity
repository
service
globalException
```

---

### 🔹 Connection Service
**Module:** `connection-service`  
**Database:** Neo4j (Graph Database)

**Responsibilities:**
- Manage user-to-user connections
- Handle relationships such as connections / followers
- Graph-based modeling for fast relationship traversal

---

## 🗄️ Databases

| Service            | Database   | Purpose |
|--------------------|------------|---------|
| user-service       | PostgreSQL | User data |
| posts-service      | PostgreSQL | Posts data |
| connection-service | Neo4j      | Relationships |

---

## 🔄 Service Discovery Flow
1. Discovery Server starts
2. API Gateway and all services start
3. Each service registers itself with Eureka
4. API Gateway routes requests dynamically using service names
5. Authentication & authorization happen at the gateway layer

---

## ▶️ How to Run (Local)

### 1️⃣ Start Discovery Server
```bash
  cd discovery-server
  ./mvnw spring-boot:run
```

### 2️⃣ Start API Gateway
```bash
  cd api-gateway
  ./mvnw spring-boot:run
```

### 3️⃣ Start User Service
```bash
  cd user-service
  ./mvnw spring-boot:run
```

### 4️⃣ Start Posts Service
```bash
  cd posts-service
  ./mvnw spring-boot:run
```

### 5️⃣ Start Connection Service
```bash
  cd connection-service
  ./mvnw spring-boot:run
```

### 6️⃣ Verify
Open Eureka Dashboard:  
👉 http://localhost:8761

---

## 🐳 Docker & Docker Compose Setup

The entire system is fully containerized using **Docker** and **Docker Compose**.

All services, databases, and Kafka are started together using a single command.

### 📦 Containers Included
- discovery-server
- api-gateway
- user-service (PostgreSQL)
- posts-service (PostgreSQL)
- connection-service (Neo4j)
- notification-service (PostgreSQL)
- Apache Kafka (KRaft mode, no Zookeeper)
- Kafka UI

---

### ▶️ Run Entire System with Docker

From project root:
```bash
  docker-compose up -d
```

🔍 Verify Running Containers

    docker ps

🛑 Stop All Containers

    docker-compose down

---

🌐 Important URLs
Service	URL

     API Gateway	        http://localhost:8080

    Eureka Dashboard	http://localhost:8761

    Kafka UI	        http://localhost:8090

    Neo4j Browser	        http://localhost:7474

---

## 🧠 Design Decisions

- Authentication is centralized at the API Gateway to avoid duplication
- JWT parsing is intentionally avoided in downstream services
- User identity is propagated using request headers (`X-User-Id`)
- Each service owns its database to ensure loose coupling
- Neo4j is used for connection-service to efficiently model graph relationships

---

## 🔮 Feature Status & Future Enhancements

### ✅ Completed Features
- [x] Microservices architecture using Spring Boot
- [x] Service discovery with Eureka
- [x] API Gateway as a single entry point
- [x] Centralized JWT authentication at API Gateway
- [x] User context propagation using request headers
- [x] Inter-service communication using OpenFeign
- [x] Database-per-service architecture
- [x] PostgreSQL for relational data
- [x] Neo4j for connection graph modeling
- [x] Apache Kafka for asynchronous messaging
- [x] Event-driven notification system
- [x] Notification Service consuming Kafka events

---

### ⏳ Future Enhancements
- [ ] Docker & Docker Compose setup
- [ ] Centralized Config Server (Spring Cloud Config)
- [ ] Distributed tracing with Zipkin
- [ ] Kubernetes deployment
- [ ] Rate limiting at API Gateway
- [ ] Monitoring & metrics (Prometheus / Grafana)

---

## 👨‍💻 Author
**Vishal Patidar**  
Backend Developer | Java | Spring Boot | Microservices
