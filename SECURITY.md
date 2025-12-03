# Security Policy

## Supported Versions

We release patches for security vulnerabilities. The following versions are currently being supported with security updates:

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |
| < 1.0   | :x:                |

## Reporting a Vulnerability

The SafeCap team takes security bugs seriously. We appreciate your efforts to responsibly disclose your findings and will make every effort to acknowledge your contributions.

### How to Report

**Please do not report security vulnerabilities through public GitHub issues.**

Instead, please report them via email to the project maintainers. You should receive a response within 48 hours. If for some reason you do not, please follow up via email to ensure we received your original message.

### What to Include

To help us better understand the nature and scope of the possible issue, please include as much of the following information as possible:

- Type of issue (e.g., buffer overflow, SQL injection, cross-site scripting, etc.)
- Full paths of source file(s) related to the manifestation of the issue
- The location of the affected source code (tag/branch/commit or direct URL)
- Any special configuration required to reproduce the issue
- Step-by-step instructions to reproduce the issue
- Proof-of-concept or exploit code (if possible)
- Impact of the issue, including how an attacker might exploit it

This information will help us triage your report more quickly.

## Security Best Practices

When using SafeCap, we recommend following these security best practices:

### Authentication & Authorization

- Always use strong passwords (minimum 12 characters recommended)
- Rotate JWT secrets regularly in production
- Keep JWT expiration times short for sensitive operations
- Implement proper role-based access control

### Configuration

- Never commit sensitive data (passwords, API keys, secrets) to version control
- Use environment variables for all sensitive configuration
- Ensure `.env` files are in `.gitignore`
- Use different credentials for development, staging, and production

### Database

- Use prepared statements (JPA handles this automatically)
- Follow principle of least privilege for database users
- Regularly backup your database
- Encrypt sensitive data at rest

### Network Security

- Always use HTTPS in production
- Configure CORS properly for your domain
- Implement rate limiting to prevent abuse
- Use firewall rules to restrict database access

### Dependencies

- Regularly update dependencies to patch vulnerabilities
- Use Maven's dependency-check plugin (already configured)
- Monitor security advisories for dependencies
- Remove unused dependencies

### Deployment

- Run application with non-root user
- Keep JVM and OS updated
- Use container scanning tools for Docker images
- Implement proper logging and monitoring

## Security Features

SafeCap includes several built-in security features:

### Authentication
- JWT-based stateless authentication
- BCrypt password hashing with salt
- Token expiration and validation

### Input Validation
- Bean Validation (JSR 380) for all inputs
- XSS protection
- SQL injection prevention via JPA

### Security Headers
- CORS configuration
- Security headers configured via Spring Security

### Audit Logging
- Automatic timestamp tracking for all entities
- Request/response logging (configurable)

## Security Updates

Security updates will be released as patch versions (e.g., 1.0.1, 1.0.2) and will be documented in the [CHANGELOG.md](CHANGELOG.md).

When a security vulnerability is fixed:
1. A new patch version will be released
2. The vulnerability will be documented in CHANGELOG.md
3. A security advisory will be published on GitHub
4. Affected versions will be clearly identified

## Security Testing

We encourage security testing of SafeCap, but please follow these guidelines:

### Allowed
- Security research on your own installation
- Reporting vulnerabilities through proper channels
- Testing with explicit permission

### Not Allowed
- Testing on production systems without permission
- Denial of service testing
- Accessing or modifying data you don't own
- Social engineering attacks

## Security Hall of Fame

We recognize and thank security researchers who help improve SafeCap's security:

<!-- Contributors who report security issues will be listed here (with their permission) -->

- Thank you to all security researchers who help keep SafeCap secure!

## Disclosure Policy

When a security vulnerability is reported:

1. We will acknowledge receipt within 48 hours
2. We will provide an initial assessment within 5 business days
3. We will work on a fix and keep you informed of progress
4. We will release a patched version as soon as possible
5. We will publicly disclose the vulnerability after a patch is available

We ask that you:
- Give us reasonable time to fix the issue before public disclosure
- Make a good faith effort to avoid privacy violations and service disruption
- Do not exploit the vulnerability beyond what is necessary to demonstrate it

## Questions

If you have questions about this security policy, please open an issue in the GitHub repository (for general questions only, not for reporting vulnerabilities).

---

**Last Updated**: December 2025
