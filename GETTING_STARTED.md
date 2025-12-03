# Getting Started with SafeCap

This guide will help you get SafeCap up and running on your local machine for development and testing purposes.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Development Workflow](#development-workflow)
- [Troubleshooting](#troubleshooting)

## Prerequisites

Before you begin, ensure you have the following installed:

### Required
- **Java Development Kit (JDK) 17 or higher**
  ```bash
  java -version
  # Should show: java version "17" or higher
  ```

- **Maven 3.8 or higher**
  ```bash
  mvn -version
  # Should show: Apache Maven 3.8.x or higher
  ```

- **Git**
  ```bash
  git --version
  ```

### Optional
- **Docker** (for containerized deployment)
  ```bash
  docker --version
  ```

- **Docker Compose** (for multi-container setup)
  ```bash
  docker-compose --version
  ```

- **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/SouzaEu/SafeCap.git
cd SafeCap
```

### 2. Verify Project Structure

```bash
ls -la
# Should show: pom.xml, src/, .github/, etc.
```

### 3. Install Dependencies

```bash
mvn clean install
```

This will:
- Download all required dependencies
- Compile the source code
- Run tests
- Package the application

## Configuration

### 1. Database Configuration

#### Option A: Using H2 (In-Memory Database) - For Development

The project is configured to use H2 by default for development. No additional setup required.

#### Option B: Using Oracle Database - For Production

1. Copy the environment example file:
   ```bash
   cp .env.example .env
   ```

2. Edit `.env` and add your database credentials:
   ```properties
   DB_URL=jdbc:oracle:thin:@localhost:1521:xe
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   ```

3. Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=${DB_URL}
   spring.datasource.username=${DB_USERNAME}
   spring.datasource.password=${DB_PASSWORD}
   spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
   spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
   ```

### 2. JWT Configuration

Configure JWT secret in `application.properties`:

```properties
jwt.secret=your-very-secure-secret-key-minimum-256-bits
jwt.expiration=86400000
```

**Important**: Use a strong, random secret for production!

### 3. Application Port (Optional)

Default port is 8080. To change:

```properties
server.port=8080
```

## Running the Application

### Method 1: Using Maven (Recommended for Development)

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Method 2: Using Packaged JAR

```bash
# Build the JAR
mvn clean package -DskipTests

# Run the JAR
java -jar target/safecap-1.0.0.jar
```

### Method 3: Using Docker

```bash
# Build the Docker image
docker build -t safecap:latest .

# Run the container
docker run -p 8080:8080 safecap:latest
```

### Method 4: Using Docker Compose

```bash
docker-compose up -d
```

## Accessing the Application

Once running, you can access:

### Swagger UI (API Documentation)
```
http://localhost:8080/swagger-ui.html
```

### H2 Console (if using H2)
```
http://localhost:8080/h2-console
```
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

## Testing

### Run All Tests

```bash
mvn test
```

### Run Specific Test

```bash
mvn test -Dtest=DispositivoServiceTest
```

### Run with Coverage Report

```bash
mvn clean test jacoco:report
```

View coverage report at: `target/site/jacoco/index.html`

### Run Code Quality Checks

```bash
# Checkstyle
mvn checkstyle:check

# SpotBugs
mvn spotbugs:check

# All quality checks
mvn clean verify
```

## Development Workflow

### 1. Create a Feature Branch

```bash
git checkout -b feature/your-feature-name
```

### 2. Make Your Changes

Edit the code using your preferred IDE.

### 3. Run Tests

```bash
mvn test
```

### 4. Check Code Quality

```bash
mvn checkstyle:check spotbugs:check
```

### 5. Commit Your Changes

```bash
git add .
git commit -m "feat: add your feature description"
```

### 6. Push and Create PR

```bash
git push origin feature/your-feature-name
```

Then create a Pull Request on GitHub.

## API Testing

### Using Swagger UI

1. Navigate to `http://localhost:8080/swagger-ui.html`
2. Click "Try it out" on any endpoint
3. Fill in parameters
4. Click "Execute"

### Using cURL

#### Register a User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Test User",
    "email": "test@example.com",
    "senha": "password123"
  }'
```

#### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "senha": "password123"
  }'
```

#### Create Device (with JWT token)
```bash
curl -X POST http://localhost:8080/api/dispositivos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "nome": "Temperature Sensor 1",
    "localizacao": "Warehouse A",
    "tipo": "TEMPERATURE",
    "ativo": true
  }'
```

## Troubleshooting

### Port Already in Use

**Problem**: Error saying port 8080 is already in use.

**Solution**:
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process (replace PID with actual process ID)
kill -9 PID

# Or change the port in application.properties
```

### Database Connection Failed

**Problem**: Cannot connect to database.

**Solution**:
- Verify database credentials in `.env`
- Ensure database service is running
- Check firewall settings
- For H2, ensure no file locks exist

### Tests Failing

**Problem**: Tests fail during build.

**Solution**:
```bash
# Clean and rebuild
mvn clean install

# Skip tests temporarily (not recommended)
mvn clean install -DskipTests

# Run specific test with verbose output
mvn test -Dtest=YourTest -X
```

### Out of Memory Error

**Problem**: Java heap space error.

**Solution**:
```bash
# Increase heap size
export MAVEN_OPTS="-Xmx2048m"
mvn clean install

# Or for running the app
java -Xmx2048m -jar target/safecap-1.0.0.jar
```

### JWT Token Expired

**Problem**: API returns 401 Unauthorized.

**Solution**:
- Login again to get a new token
- Increase `jwt.expiration` in `application.properties` for development
- Ensure system clock is synchronized

## IDE Setup

### IntelliJ IDEA

1. **Import Project**
   - File → Open → Select `pom.xml`
   - Click "Open as Project"

2. **Enable Annotation Processing**
   - Settings → Build, Execution, Deployment → Compiler → Annotation Processors
   - Check "Enable annotation processing"

3. **Install Lombok Plugin**
   - Settings → Plugins → Search "Lombok" → Install

4. **Configure Code Style**
   - Settings → Editor → Code Style → Import `.editorconfig`

### VS Code

1. **Install Extensions**
   - Extension Pack for Java
   - Spring Boot Extension Pack
   - Lombok Annotations Support

2. **Open Folder**
   - File → Open Folder → Select SafeCap directory

3. **Configure Maven**
   - VS Code will auto-detect `pom.xml`

## Next Steps

1. **Explore the API** - Use Swagger UI to test endpoints
2. **Read Documentation** - Check [ARCHITECTURE.md](ARCHITECTURE.md) for design details
3. **Review Code** - Browse the source code to understand the structure
4. **Make Changes** - Follow [CONTRIBUTING.md](CONTRIBUTING.md) to contribute
5. **Deploy** - See deployment options in [README.md](README.md)

## Additional Resources

- [README.md](README.md) - Project overview
- [ARCHITECTURE.md](ARCHITECTURE.md) - Architecture documentation
- [CONTRIBUTING.md](CONTRIBUTING.md) - Contribution guidelines
- [API Documentation](http://localhost:8080/swagger-ui.html) - Interactive API docs
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)

## Getting Help

If you encounter issues:

1. Check this guide first
2. Review [Troubleshooting](#troubleshooting) section
3. Search existing [GitHub Issues](https://github.com/SouzaEu/SafeCap/issues)
4. Create a new issue with details about your problem

---

**Welcome to SafeCap!** Happy coding! 🚀
