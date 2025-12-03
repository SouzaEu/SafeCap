# SafeCap

[![Java Version](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()

An enterprise-grade IoT monitoring system for temperature and humidity control, built with Spring Boot and modern security practices.

## Overview

SafeCap is a RESTful API platform designed to monitor and alert on environmental conditions through IoT devices. The system provides real-time tracking, automated alerting, and historical data analysis for temperature-sensitive and humidity-sensitive environments.

**Key Features:**
- Secure JWT-based authentication with BCrypt password encryption
- Real-time IoT device monitoring and management
- Automated alert generation based on configurable thresholds
- RESTful API with OpenAPI 3.0 documentation
- Horizontal scalability with stateless architecture
- Comprehensive audit logging

## Technology Stack

### Core Framework
- **Java 17** - LTS version with modern language features
- **Spring Boot 3.1.0** - Application framework
- **Spring Security 6.1.0** - Authentication and authorization
- **Spring Data JPA** - Data persistence layer

### Security
- **JWT (jjwt 0.11.5)** - Token-based authentication
- **BCrypt** - Password hashing algorithm

### Database
- **Oracle Database** - Primary production database
- **H2 Database** - In-memory database for testing

### Documentation
- **SpringDoc OpenAPI 3** - API documentation and Swagger UI

### Build Tools
- **Maven 3.8+** - Dependency management and build automation

## Prerequisites

- JDK 17 or higher
- Maven 3.8+
- Oracle Database (or H2 for development/testing)

## Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/SouzaEu/SafeCap.git
cd SafeCap
```

### 2. Configure Database

Copy the example environment file and configure your database credentials:

```bash
cp .env.example .env
```

Edit `src/main/resources/application.properties` with your database settings.

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### 5. Access API Documentation

Navigate to `http://localhost:8080/swagger-ui.html` for interactive API documentation.

## Project Structure

```
SafeCap/
├── src/
│   ├── main/
│   │   ├── java/com/safecap/
│   │   │   ├── config/          # Security, CORS, JWT configuration
│   │   │   ├── controller/      # REST API endpoints
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── exception/       # Custom exception handling
│   │   │   ├── model/           # JPA entities
│   │   │   ├── repository/      # Data access layer
│   │   │   └── service/         # Business logic
│   │   └── resources/
│   │       └── application.properties
│   └── test/                    # Unit and integration tests
├── pom.xml                      # Maven dependencies
├── Dockerfile                   # Container configuration
└── docker-compose.yml           # Multi-container setup
```

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Authenticate and receive JWT token

### Devices
- `GET /api/dispositivos` - List all devices
- `POST /api/dispositivos` - Create new device
- `GET /api/dispositivos/{id}` - Get device details
- `PUT /api/dispositivos/{id}` - Update device
- `DELETE /api/dispositivos/{id}` - Remove device

### Alerts
- `GET /api/alertas` - List all alerts
- `POST /api/alertas` - Create alert
- `GET /api/alertas/{id}` - Get alert details
- `GET /api/alertas/dispositivo/{id}` - Get alerts by device

For complete API documentation, visit the Swagger UI endpoint when running the application.

## Configuration

### Environment Variables

```properties
# Database Configuration
DB_URL=jdbc:oracle:thin:@localhost:1521:xe
DB_USERNAME=your_username
DB_PASSWORD=your_password

# JWT Configuration
JWT_SECRET=your-secret-key-here
JWT_EXPIRATION=86400000

# Application Configuration
SERVER_PORT=8080
```

### Validation Rules

- **Temperature Range:** -10°C to 80°C
- **Humidity Range:** 0% to 100%
- **Password:** Minimum 6 characters
- **Email:** Valid RFC 5322 format

## Security

This application implements multiple security layers:

1. **Authentication:** JWT-based stateless authentication
2. **Authorization:** Role-based access control (RBAC)
3. **Password Security:** BCrypt hashing with salt
4. **SQL Injection Prevention:** Parameterized queries via JPA
5. **CORS Configuration:** Configurable cross-origin resource sharing
6. **Input Validation:** Bean Validation (JSR 380)

## Testing

Run the test suite:

```bash
mvn test
```

Run with coverage report:

```bash
mvn clean test jacoco:report
```

## Docker Support

### Build and Run with Docker

```bash
docker build -t safecap:latest .
docker run -p 8080:8080 safecap:latest
```

### Using Docker Compose

```bash
docker-compose up -d
```

## Development

### Code Style
This project follows standard Java conventions and Spring Boot best practices.

### Branching Strategy
- `main` - Production-ready code
- `develop` - Integration branch
- `feature/*` - New features
- `bugfix/*` - Bug fixes
- `hotfix/*` - Production hotfixes

### Commit Convention
Follow [Conventional Commits](https://www.conventionalcommits.org/):
```
feat: add device batch import
fix: resolve JWT expiration issue
docs: update API documentation
test: add integration tests for alerts
```

## Performance

- Stateless architecture enables horizontal scaling
- Database connection pooling with HikariCP
- Lazy loading and query optimization with JPA
- Caching strategies for frequently accessed data

## Monitoring and Logging

The application uses SLF4J with Logback for structured logging:
- Application logs: `logs/application.log`
- Error logs: `logs/error.log`

## Contributing

We welcome contributions! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Architecture

For detailed architecture documentation, see [ARCHITECTURE.md](ARCHITECTURE.md).

## Support

For questions and support, please open an issue in the GitHub repository.

---

**Built with enterprise standards for production environments**
