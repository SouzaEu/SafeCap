# Architecture Documentation

## Table of Contents
- [Overview](#overview)
- [System Architecture](#system-architecture)
- [Design Patterns](#design-patterns)
- [Data Model](#data-model)
- [Security Architecture](#security-architecture)
- [API Design](#api-design)
- [Database Schema](#database-schema)
- [Deployment Architecture](#deployment-architecture)

## Overview

SafeCap follows a layered architecture pattern with clear separation of concerns, implementing Domain-Driven Design (DDD) principles and RESTful API standards.

### Architecture Principles

1. **Separation of Concerns**: Each layer has a single, well-defined responsibility
2. **Dependency Inversion**: High-level modules don't depend on low-level modules
3. **Single Responsibility**: Each class has one reason to change
4. **Open/Closed Principle**: Open for extension, closed for modification
5. **DRY (Don't Repeat Yourself)**: Code reusability and maintainability

## System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        Client Layer                          │
│              (Web, Mobile, IoT Devices)                      │
└────────────────────┬────────────────────────────────────────┘
                     │ HTTPS/REST
┌────────────────────▼────────────────────────────────────────┐
│                   Presentation Layer                         │
│   ┌─────────────────────────────────────────────────────┐   │
│   │          Controllers (REST Endpoints)               │   │
│   │  - AuthController                                   │   │
│   │  - DispositivoController                            │   │
│   │  - AlertaController                                 │   │
│   │  - UsuarioController                                │   │
│   └─────────────────────────────────────────────────────┘   │
└────────────────────┬────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────┐
│                   Security Layer                             │
│   ┌─────────────────────────────────────────────────────┐   │
│   │  - JWT Authentication Filter                        │   │
│   │  - Security Configuration                           │   │
│   │  - CORS Configuration                               │   │
│   └─────────────────────────────────────────────────────┘   │
└────────────────────┬────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────┐
│                   Service Layer                              │
│   ┌─────────────────────────────────────────────────────┐   │
│   │          Business Logic & Orchestration             │   │
│   │  - UsuarioService                                   │   │
│   │  - DispositivoService                               │   │
│   │  - AlertaService                                    │   │
│   │  - JwtService                                       │   │
│   └─────────────────────────────────────────────────────┘   │
└────────────────────┬────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────┐
│                   Repository Layer                           │
│   ┌─────────────────────────────────────────────────────┐   │
│   │          Data Access Layer (JPA)                    │   │
│   │  - UsuarioRepository                                │   │
│   │  - DispositivoRepository                            │   │
│   │  - AlertaRepository                                 │   │
│   └─────────────────────────────────────────────────────┘   │
└────────────────────┬────────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────────┐
│                   Database Layer                             │
│              Oracle Database / H2 (Testing)                  │
└─────────────────────────────────────────────────────────────┘
```

## Design Patterns

### 1. Layered Architecture
The application is organized into distinct layers:
- **Controller Layer**: HTTP request handling and response formatting
- **Service Layer**: Business logic and transaction management
- **Repository Layer**: Data persistence abstraction
- **Model Layer**: Domain entities and value objects

### 2. Data Transfer Object (DTO)
- Separate DTOs for request and response
- Prevents exposure of internal domain model
- Enables versioning and backward compatibility

Examples:
- `UsuarioDTO` - User creation request
- `UsuarioResponseDTO` - User data response
- `LoginDTO` - Authentication request
- `TokenResponseDTO` - JWT token response

### 3. Repository Pattern
- Abstraction over data access
- Enables switching between different data sources
- Supports unit testing with mock repositories

### 4. Dependency Injection
- Constructor-based injection (preferred)
- Field injection for configuration properties
- Promotes testability and loose coupling

### 5. Builder Pattern
- Used in entity creation (via Lombok)
- Immutable object construction
- Fluent API for object creation

### 6. Strategy Pattern
- Custom exception handling strategies
- Multiple authentication strategies support

## Data Model

### Core Entities

#### Usuario (User)
```java
Usuario {
  Long id
  String nome
  String email (unique)
  String senha (encrypted)
  Role role
  LocalDateTime createdAt
  LocalDateTime updatedAt
}
```

#### Dispositivo (Device)
```java
Dispositivo {
  Long id
  String nome
  String localizacao
  String tipo
  Boolean ativo
  Usuario usuario (FK)
  LocalDateTime createdAt
  LocalDateTime updatedAt
}
```

#### Alerta (Alert)
```java
Alerta {
  Long id
  Dispositivo dispositivo (FK)
  Double temperatura
  Double umidade
  String mensagem
  String severidade
  Boolean resolvido
  LocalDateTime dataHora
  LocalDateTime createdAt
}
```

### Entity Relationships

```
Usuario (1) ──────< (N) Dispositivo
                         │
                         │
Dispositivo (1) ─────< (N) Alerta
```

## Security Architecture

### Authentication Flow

```
1. Client sends credentials (email/password)
   │
   ▼
2. AuthController receives request
   │
   ▼
3. UsuarioService validates credentials
   │
   ▼
4. BCrypt verifies password hash
   │
   ▼
5. JwtService generates token
   │
   ▼
6. Token returned to client
   │
   ▼
7. Client includes token in Authorization header
   │
   ▼
8. JwtAuthFilter validates token
   │
   ▼
9. Request proceeds to controller
```

### Security Components

#### 1. JWT Token Structure
```
Header:
{
  "alg": "HS256",
  "typ": "JWT"
}

Payload:
{
  "sub": "user@example.com",
  "iat": 1516239022,
  "exp": 1516325422
}

Signature: HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)
```

#### 2. Password Security
- BCrypt with salt (strength: 10)
- Passwords never stored in plain text
- Password validation on input

#### 3. CORS Configuration
- Configurable allowed origins
- Credential support enabled
- Methods: GET, POST, PUT, DELETE, OPTIONS
- Headers: Authorization, Content-Type

## API Design

### RESTful Principles

1. **Resource-Based URLs**
   - `/api/usuarios` - Users collection
   - `/api/usuarios/{id}` - Specific user
   - `/api/dispositivos` - Devices collection
   - `/api/alertas` - Alerts collection

2. **HTTP Methods**
   - GET - Retrieve resources
   - POST - Create resources
   - PUT - Update resources
   - DELETE - Remove resources

3. **Status Codes**
   - 200 OK - Successful GET/PUT
   - 201 Created - Successful POST
   - 204 No Content - Successful DELETE
   - 400 Bad Request - Invalid input
   - 401 Unauthorized - Missing/invalid token
   - 403 Forbidden - Insufficient permissions
   - 404 Not Found - Resource doesn't exist
   - 500 Internal Server Error - Server error

4. **Response Format**
```json
{
  "id": 1,
  "nome": "Device Name",
  "status": "active",
  "createdAt": "2025-01-01T10:00:00Z"
}
```

5. **Error Format**
```json
{
  "timestamp": "2025-01-01T10:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid input data",
  "path": "/api/dispositivos"
}
```

### Validation

- Bean Validation (JSR 380)
- Custom validators for business rules
- Input sanitization
- Type safety through DTOs

## Database Schema

### Tables

#### USUARIOS
```sql
CREATE TABLE usuarios (
    id NUMBER PRIMARY KEY,
    nome VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    senha VARCHAR2(255) NOT NULL,
    role VARCHAR2(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### DISPOSITIVOS
```sql
CREATE TABLE dispositivos (
    id NUMBER PRIMARY KEY,
    nome VARCHAR2(100) NOT NULL,
    localizacao VARCHAR2(200),
    tipo VARCHAR2(50),
    ativo NUMBER(1) DEFAULT 1,
    usuario_id NUMBER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
```

#### ALERTAS
```sql
CREATE TABLE alertas (
    id NUMBER PRIMARY KEY,
    dispositivo_id NUMBER NOT NULL,
    temperatura NUMBER(5,2),
    umidade NUMBER(5,2),
    mensagem VARCHAR2(500),
    severidade VARCHAR2(20),
    resolvido NUMBER(1) DEFAULT 0,
    data_hora TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (dispositivo_id) REFERENCES dispositivos(id)
);
```

### Indexes

```sql
CREATE INDEX idx_dispositivos_usuario ON dispositivos(usuario_id);
CREATE INDEX idx_alertas_dispositivo ON alertas(dispositivo_id);
CREATE INDEX idx_alertas_data_hora ON alertas(data_hora);
CREATE INDEX idx_usuarios_email ON usuarios(email);
```

## Deployment Architecture

### Development Environment
```
Developer Machine
  ├── Java 17 JDK
  ├── Maven
  ├── IDE (IntelliJ IDEA / Eclipse / VS Code)
  └── H2 Database (embedded)
```

### Production Environment
```
Application Server
  ├── JVM (Java 17)
  ├── Spring Boot Application (JAR)
  └── Application Properties
       │
       ├── Database Connection Pool
       │   └── Oracle Database
       │
       └── External Services
           └── Monitoring/Logging
```

### Docker Deployment
```
Docker Container
  ├── Base Image: openjdk:17-slim
  ├── Application JAR
  ├── Configuration Files
  └── Exposed Ports: 8080
```

### Scalability Considerations

1. **Horizontal Scaling**
   - Stateless application design
   - JWT tokens (no session state)
   - Load balancer compatible

2. **Database Optimization**
   - Connection pooling (HikariCP)
   - Query optimization
   - Proper indexing

3. **Caching Strategy**
   - Spring Cache abstraction
   - In-memory caching for frequently accessed data
   - Cache invalidation policies

## Performance Considerations

### Database Performance
- Lazy loading for associations
- Batch processing for bulk operations
- Query optimization with JPA Criteria API
- Connection pooling configuration

### Application Performance
- Stateless architecture
- Efficient DTO mapping
- Pagination for large datasets
- Async processing for non-blocking operations

## Monitoring and Observability

### Logging Strategy
- Structured logging with SLF4J
- Log levels: ERROR, WARN, INFO, DEBUG
- Correlation IDs for request tracking
- Sensitive data masking

### Metrics
- JVM metrics (heap, threads, GC)
- Application metrics (request rate, latency)
- Business metrics (devices, alerts)
- Database connection pool metrics

## Error Handling

### Exception Hierarchy
```
RuntimeException
  ├── BusinessRuleException
  ├── ResourceNotFoundException
  └── JwtExpiredException
```

### Global Exception Handler
- Centralized exception handling
- Consistent error response format
- Logging of exceptions
- Client-friendly error messages

## Future Enhancements

1. **Caching Layer**: Redis integration for performance
2. **Message Queue**: Kafka/RabbitMQ for async processing
3. **API Versioning**: URL-based versioning strategy
4. **Rate Limiting**: Protection against API abuse
5. **GraphQL Support**: Alternative to REST API
6. **Microservices**: Service decomposition for scalability
7. **Event Sourcing**: Audit trail and state reconstruction

---

**Document Version**: 1.0  
**Last Updated**: December 2025  
**Maintained By**: Development Team
