# Contributing to SafeCap

Thank you for your interest in contributing to SafeCap! This document provides guidelines and instructions for contributing to this project.

## Table of Contents
- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Workflow](#development-workflow)
- [Coding Standards](#coding-standards)
- [Testing Guidelines](#testing-guidelines)
- [Commit Guidelines](#commit-guidelines)
- [Pull Request Process](#pull-request-process)
- [Reporting Bugs](#reporting-bugs)
- [Suggesting Features](#suggesting-features)

## Code of Conduct

### Our Standards

- Be respectful and inclusive
- Welcome newcomers and help them learn
- Focus on constructive criticism
- Accept responsibility and apologize for mistakes
- Prioritize what's best for the community

### Unacceptable Behavior

- Harassment, discrimination, or offensive comments
- Personal attacks or trolling
- Publishing others' private information
- Any conduct inappropriate in a professional setting

## Getting Started

### Prerequisites

- JDK 17 or higher
- Maven 3.8+
- Git
- IDE (IntelliJ IDEA recommended)
- Basic understanding of Spring Boot and REST APIs

### Setting Up Development Environment

1. **Fork the repository**
   ```bash
   # Click the "Fork" button on GitHub
   ```

2. **Clone your fork**
   ```bash
   git clone https://github.com/YOUR-USERNAME/SafeCap.git
   cd SafeCap
   ```

3. **Add upstream remote**
   ```bash
   git remote add upstream https://github.com/SouzaEu/SafeCap.git
   ```

4. **Create a new branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

5. **Install dependencies**
   ```bash
   mvn clean install
   ```

6. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

7. **Run tests**
   ```bash
   mvn test
   ```

## Development Workflow

### Branch Naming Convention

Use descriptive branch names that follow this pattern:

- `feature/add-device-search` - New features
- `bugfix/fix-jwt-expiration` - Bug fixes
- `hotfix/security-patch` - Critical fixes for production
- `docs/update-api-docs` - Documentation updates
- `refactor/improve-service-layer` - Code refactoring
- `test/add-integration-tests` - Test additions

### Keeping Your Fork Updated

```bash
# Fetch upstream changes
git fetch upstream

# Merge upstream changes into your main branch
git checkout main
git merge upstream/main

# Update your fork on GitHub
git push origin main
```

### Making Changes

1. **Create a feature branch** from `main`
   ```bash
   git checkout -b feature/your-feature
   ```

2. **Make your changes** following coding standards

3. **Write or update tests** for your changes

4. **Run all tests** to ensure nothing broke
   ```bash
   mvn clean test
   ```

5. **Commit your changes** following commit guidelines

6. **Push to your fork**
   ```bash
   git push origin feature/your-feature
   ```

7. **Create a Pull Request** on GitHub

## Coding Standards

### Java Code Style

Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) with these highlights:

#### Formatting

- **Indentation**: 4 spaces (no tabs)
- **Line length**: Maximum 120 characters
- **Braces**: Always use braces, even for single-line blocks
- **Imports**: No wildcard imports, organize alphabetically

#### Naming Conventions

```java
// Classes: PascalCase
public class DispositivoService { }

// Methods and variables: camelCase
public void createDispositivo() { }
private String deviceName;

// Constants: UPPER_SNAKE_CASE
private static final int MAX_TEMPERATURE = 80;

// Packages: lowercase
package com.safecap.service;
```

#### Documentation

```java
/**
 * Creates a new device with the given parameters.
 *
 * @param dispositivoDTO the device data transfer object
 * @return the created device response
 * @throws BusinessRuleException if validation fails
 */
public DispositivoResponseDTO createDispositivo(DispositivoDTO dispositivoDTO) {
    // Implementation
}
```

### Spring Boot Best Practices

1. **Dependency Injection**
   ```java
   // Prefer constructor injection
   @Service
   public class DispositivoService {
       private final DispositivoRepository repository;
       
       public DispositivoService(DispositivoRepository repository) {
           this.repository = repository;
       }
   }
   ```

2. **Exception Handling**
   ```java
   // Use custom exceptions
   throw new ResourceNotFoundException("Device not found with id: " + id);
   ```

3. **Validation**
   ```java
   // Use Bean Validation annotations
   public class DispositivoDTO {
       @NotBlank(message = "Name is required")
       private String nome;
       
       @Min(value = -10, message = "Temperature must be >= -10")
       @Max(value = 80, message = "Temperature must be <= 80")
       private Double temperatura;
   }
   ```

4. **Logging**
   ```java
   // Use SLF4J
   private static final Logger logger = LoggerFactory.getLogger(DispositivoService.class);
   
   logger.info("Creating device: {}", dispositivoDTO.getNome());
   logger.error("Error creating device", exception);
   ```

### Database Guidelines

- Use meaningful column and table names
- Always add indexes for foreign keys
- Use appropriate data types
- Document complex queries
- Use JPA naming conventions

### Security Guidelines

- Never commit sensitive data (passwords, API keys)
- Validate all user inputs
- Use parameterized queries (JPA does this)
- Implement proper authorization checks
- Keep dependencies updated

## Testing Guidelines

### Test Structure

```
src/test/java/
└── com/safecap/
    ├── controller/
    │   └── DispositivoControllerTest.java
    ├── service/
    │   └── DispositivoServiceTest.java
    └── repository/
        └── DispositivoRepositoryTest.java
```

### Unit Tests

```java
@ExtendWith(MockitoExtension.class)
class DispositivoServiceTest {
    
    @Mock
    private DispositivoRepository repository;
    
    @InjectMocks
    private DispositivoService service;
    
    @Test
    void shouldCreateDispositivoSuccessfully() {
        // Given
        DispositivoDTO dto = new DispositivoDTO();
        dto.setNome("Test Device");
        
        // When
        DispositivoResponseDTO result = service.createDispositivo(dto);
        
        // Then
        assertNotNull(result);
        assertEquals("Test Device", result.getNome());
        verify(repository, times(1)).save(any(Dispositivo.class));
    }
}
```

### Integration Tests

```java
@SpringBootTest
@AutoConfigureMockMvc
class DispositivoControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldCreateDeviceSuccessfully() throws Exception {
        mockMvc.perform(post("/api/dispositivos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Test Device\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Test Device"));
    }
}
```

### Test Coverage

- Aim for minimum 80% code coverage
- Focus on critical business logic
- Test edge cases and error scenarios
- Run coverage report:
  ```bash
  mvn clean test jacoco:report
  ```

## Commit Guidelines

### Commit Message Format

Follow [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, no logic change)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks
- `perf`: Performance improvements
- `ci`: CI/CD changes

### Examples

```
feat(device): add batch device import functionality

Implement batch import feature allowing users to upload CSV files
with multiple devices. Includes validation and error reporting.

Closes #123
```

```
fix(auth): resolve JWT token expiration issue

Fixed bug where tokens were expiring immediately after creation.
Updated JwtService to use correct time units.

Fixes #456
```

```
docs(readme): update installation instructions

Added Docker installation steps and environment variable configuration.
```

### Commit Best Practices

- Keep commits atomic (one logical change per commit)
- Write clear, descriptive commit messages
- Reference issue numbers when applicable
- Use imperative mood ("add" not "added")
- Don't commit commented-out code
- Don't commit temporary files

## Pull Request Process

### Before Submitting

1. **Update your branch** with latest main
   ```bash
   git checkout main
   git pull upstream main
   git checkout feature/your-feature
   git rebase main
   ```

2. **Run all tests**
   ```bash
   mvn clean test
   ```

3. **Check code style**
   ```bash
   mvn checkstyle:check
   ```

4. **Update documentation** if needed

### PR Title and Description

**Title**: Follow commit message format
```
feat(device): add batch device import
```

**Description Template**:
```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Changes Made
- List specific changes
- Include technical details
- Mention any dependencies

## Testing
- Describe testing performed
- List test scenarios covered

## Screenshots (if applicable)

## Checklist
- [ ] Code follows project style guidelines
- [ ] Self-review completed
- [ ] Comments added for complex code
- [ ] Documentation updated
- [ ] Tests added/updated
- [ ] All tests passing
- [ ] No new warnings
```

### Review Process

1. Automated checks must pass (CI/CD)
2. At least one maintainer approval required
3. Address all review comments
4. Keep PR focused and reasonably sized
5. Be responsive to feedback

### After Approval

- Maintainers will merge your PR
- Your branch will be deleted
- Celebrate your contribution!

## Reporting Bugs

### Before Reporting

1. Check existing issues
2. Ensure you're using the latest version
3. Verify it's reproducible

### Bug Report Template

```markdown
**Describe the Bug**
Clear description of the issue

**To Reproduce**
Steps to reproduce:
1. Go to '...'
2. Click on '...'
3. See error

**Expected Behavior**
What you expected to happen

**Actual Behavior**
What actually happened

**Screenshots**
If applicable

**Environment**
- OS: [e.g., macOS 13.0]
- Java Version: [e.g., 17.0.2]
- Spring Boot Version: [e.g., 3.1.0]

**Additional Context**
Any other relevant information
```

## Suggesting Features

### Feature Request Template

```markdown
**Feature Description**
Clear description of the feature

**Problem it Solves**
What problem does this address?

**Proposed Solution**
How should it work?

**Alternatives Considered**
Other approaches you've thought about

**Additional Context**
Mockups, examples, references
```

## Development Tools

### Recommended IDE Setup

**IntelliJ IDEA**
- Install Lombok plugin
- Enable annotation processing
- Import Maven project
- Configure code style from `.editorconfig`

**VS Code**
- Extension Pack for Java
- Spring Boot Extension Pack
- Lombok Annotations Support

### Useful Maven Commands

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Run specific test
mvn test -Dtest=DispositivoServiceTest

# Skip tests
mvn clean install -DskipTests

# Generate coverage report
mvn clean test jacoco:report

# Check for dependency updates
mvn versions:display-dependency-updates

# Format code
mvn spotless:apply
```

## Questions?

- Open a discussion on GitHub
- Check existing documentation
- Review closed issues/PRs for similar questions

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

---

**Thank you for contributing to SafeCap!**
