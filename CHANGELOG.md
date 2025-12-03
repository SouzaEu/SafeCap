# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- GitHub Actions CI/CD workflows
  - Continuous Integration with automated testing
  - Code quality analysis (Checkstyle, SpotBugs)
  - Security scanning (CodeQL, OWASP Dependency Check)
  - Automated release process
- Comprehensive documentation
  - Professional README with badges
  - Architecture documentation (ARCHITECTURE.md)
  - Contributing guidelines (CONTRIBUTING.md)
  - GitHub setup guide
- Code quality tools
  - JaCoCo for test coverage
  - Checkstyle for code style enforcement
  - SpotBugs for bug detection
  - OWASP Dependency Check for security vulnerabilities
- Issue and PR templates
- EditorConfig for consistent code formatting

### Changed
- Updated README to professional format without emojis
- Enhanced API documentation descriptions
- Improved project structure and organization

### Removed
- Academic-specific files (INTEGRANTES.txt, LINKS.txt)
- Informal documentation elements

## [1.0.0] - Initial Release

### Added
- Core API functionality
  - User authentication and authorization with JWT
  - Device management (CRUD operations)
  - Alert management and monitoring
  - Temperature and humidity validation
- Security features
  - JWT-based authentication
  - BCrypt password encryption
  - Role-based access control
  - CORS configuration
- Database integration
  - JPA/Hibernate ORM
  - Oracle Database support
  - H2 in-memory database for testing
- API documentation
  - OpenAPI 3.0 specification
  - Swagger UI integration
- Exception handling
  - Global exception handler
  - Custom business exceptions
  - Standardized error responses
- Validation
  - Bean Validation (JSR 380)
  - Input sanitization
  - Business rule validation
- Docker support
  - Dockerfile for containerization
  - Docker Compose configuration
- Audit logging
  - Entity audit trails
  - Timestamp tracking

### Technical Stack
- Java 17
- Spring Boot 3.1.0
- Spring Security 6.1.0
- Spring Data JPA
- JWT (jjwt 0.11.5)
- Oracle Database / H2
- Maven 3.8+
- Lombok
- SpringDoc OpenAPI

---

## Version History Format

### [Version] - YYYY-MM-DD

#### Added
- New features

#### Changed
- Changes in existing functionality

#### Deprecated
- Soon-to-be removed features

#### Removed
- Now removed features

#### Fixed
- Bug fixes

#### Security
- Security fixes and improvements

---

[Unreleased]: https://github.com/SouzaEu/SafeCap/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/SouzaEu/SafeCap/releases/tag/v1.0.0
