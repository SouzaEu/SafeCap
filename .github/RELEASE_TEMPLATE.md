# Release Notes Template

Use this template when creating new releases.

## Version X.Y.Z - Release Title

**Release Date**: YYYY-MM-DD

### Highlights

Brief summary of the most important changes in this release.

### Breaking Changes

List any breaking changes that require user action:
- Change 1
- Change 2

### New Features

- Feature 1 (#PR_NUMBER)
- Feature 2 (#PR_NUMBER)

### Enhancements

- Enhancement 1 (#PR_NUMBER)
- Enhancement 2 (#PR_NUMBER)

### Bug Fixes

- Fix 1 (#PR_NUMBER)
- Fix 2 (#PR_NUMBER)

### Security

- Security improvement 1 (#PR_NUMBER)
- Security fix 1 (#PR_NUMBER)

### Dependencies

- Updated dependency X from version A to version B
- Added new dependency Y version Z

### Documentation

- Documentation update 1
- Documentation update 2

### Performance

- Performance improvement 1
- Performance improvement 2

### Testing

- New tests added for feature X
- Improved test coverage from X% to Y%

## Installation

### Maven
```xml
<dependency>
    <groupId>com.souzau</groupId>
    <artifactId>safecap</artifactId>
    <version>X.Y.Z</version>
</dependency>
```

### Docker
```bash
docker pull safecap:X.Y.Z
docker run -p 8080:8080 safecap:X.Y.Z
```

### JAR
Download the JAR file from the release assets and run:
```bash
java -jar safecap-X.Y.Z.jar
```

## Upgrade Guide

If upgrading from a previous version:

1. Step 1
2. Step 2
3. Step 3

## Known Issues

- Issue 1 (#ISSUE_NUMBER)
- Issue 2 (#ISSUE_NUMBER)

## Contributors

Thank you to all contributors who made this release possible:
- @username1
- @username2

## Full Changelog

See [CHANGELOG.md](../CHANGELOG.md) for complete details.

---

**Note**: Replace X.Y.Z with the actual version number and fill in all sections appropriately.
