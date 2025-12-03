# GitHub Setup Guide

This document explains how to configure GitHub repository settings and secrets for SafeCap.

## Required Secrets

To enable all CI/CD workflows, you need to configure the following secrets in your GitHub repository.

### Accessing Secrets Settings

1. Go to your GitHub repository
2. Click on **Settings**
3. In the left sidebar, click on **Secrets and variables** → **Actions**
4. Click **New repository secret**

### Required Secrets

#### DOCKER_USERNAME
- **Description**: Your Docker Hub username
- **Required for**: Release workflow (Docker image publishing)
- **How to get**: Your Docker Hub account username
- **Example**: `yourusername`

#### DOCKER_PASSWORD
- **Description**: Docker Hub access token
- **Required for**: Release workflow (Docker image publishing)
- **How to get**: 
  1. Log in to [Docker Hub](https://hub.docker.com/)
  2. Go to Account Settings → Security
  3. Click "New Access Token"
  4. Give it a descriptive name (e.g., "GitHub Actions SafeCap")
  5. Copy the generated token
- **Note**: Use an access token instead of your password for better security

## Optional Configuration

### Codecov Integration

For code coverage reporting, you can optionally set up Codecov:

1. Sign up at [Codecov.io](https://codecov.io/)
2. Connect your GitHub repository
3. The CI workflow will automatically upload coverage reports

### Branch Protection Rules

To maintain code quality, consider setting up branch protection:

1. Go to **Settings** → **Branches**
2. Add rule for `main` branch
3. Recommended settings:
   - ✅ Require pull request reviews before merging
   - ✅ Require status checks to pass before merging
     - Select: `build`, `code-quality`, `dependency-check`
   - ✅ Require branches to be up to date before merging
   - ✅ Include administrators

### GitHub Actions Permissions

Ensure GitHub Actions has proper permissions:

1. Go to **Settings** → **Actions** → **General**
2. Under "Workflow permissions":
   - Select "Read and write permissions"
   - ✅ Allow GitHub Actions to create and approve pull requests

## Workflows Overview

### CI Workflow (ci.yml)
**Trigger**: Push or PR to `main` or `develop` branches

**Jobs**:
- **build**: Compiles project, runs tests, generates coverage
- **code-quality**: Runs Checkstyle and SpotBugs
- **dependency-check**: Scans for vulnerable dependencies

**Required secrets**: None

### Release Workflow (release.yml)
**Trigger**: Push of version tags (e.g., `v1.0.0`)

**Jobs**:
- Builds project
- Creates GitHub release
- Uploads JAR artifact
- Builds and pushes Docker image

**Required secrets**: `DOCKER_USERNAME`, `DOCKER_PASSWORD`

### CodeQL Workflow (codeql.yml)
**Trigger**: 
- Push or PR to `main` or `develop`
- Weekly schedule (Mondays at midnight UTC)

**Jobs**:
- Security analysis using GitHub CodeQL

**Required secrets**: None

## Creating a Release

To create a new release:

1. **Update version** (if not using semantic versioning automation):
   ```bash
   mvn versions:set -DnewVersion=1.0.0
   git commit -am "chore: bump version to 1.0.0"
   ```

2. **Create and push tag**:
   ```bash
   git tag -a v1.0.0 -m "Release version 1.0.0"
   git push origin v1.0.0
   ```

3. **Workflow automatically**:
   - Builds the project
   - Creates GitHub release
   - Uploads JAR file
   - Builds Docker image
   - Pushes to Docker Hub

## Troubleshooting

### Workflow Fails with "Secret not found"

**Problem**: Release workflow fails because Docker secrets are not configured.

**Solution**: 
- Add `DOCKER_USERNAME` and `DOCKER_PASSWORD` secrets
- Or comment out the Docker push steps in `.github/workflows/release.yml`

### Tests Fail in CI but Pass Locally

**Problem**: Database or environment differences.

**Solution**:
- Ensure tests use H2 in-memory database
- Check for hardcoded paths or environment-specific code
- Review test logs in GitHub Actions

### CodeQL Analysis Times Out

**Problem**: Large codebase causes timeout.

**Solution**:
- Adjust timeout in workflow file
- Exclude test directories if needed

## Monitoring

### Build Status Badge

Add this to your README.md:
```markdown
[![Build Status](https://github.com/SouzaEu/SafeCap/workflows/CI%20-%20Build%20and%20Test/badge.svg)](https://github.com/SouzaEu/SafeCap/actions)
```

### Coverage Badge

After setting up Codecov:
```markdown
[![codecov](https://codecov.io/gh/SouzaEu/SafeCap/branch/main/graph/badge.svg)](https://codecov.io/gh/SouzaEu/SafeCap)
```

## Security Best Practices

1. **Never commit secrets** to the repository
2. **Use access tokens** instead of passwords
3. **Rotate tokens** periodically
4. **Limit token scope** to only what's needed
5. **Review workflow logs** regularly for security issues

## Support

For issues with GitHub Actions or workflows:
- Check [GitHub Actions Documentation](https://docs.github.com/en/actions)
- Review workflow run logs in the Actions tab
- Open an issue in this repository

---

**Last Updated**: December 2025
