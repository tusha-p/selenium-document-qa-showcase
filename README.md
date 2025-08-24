# Selenium Document QA Showcase

A conceptual project demonstrating QA strategies for Document Management Systems (DMS) using Selenium WebDriver and Java.

## Objective

To showcase testing methodologies for core DMS challenges:
- Document Workflow Validation
- Permission & Security (RBAC) Testing
- Content Integrity Checks

## Tech Stack
- Selenium WebDriver (Java)
- TestNG
- Maven
- A simple mock web application for tests to run against

## Project Structure
```text
selenium-document-qa-showcase/
├── src/test/java/
│   ├── base/
│   │   └── BaseTest.java
│   ├── pages/
│   │   └── LoginPage.java
│   └── tests/
│       └── PermissionSecurityTest.java
├── src/test/resources/
├── testng.xml
├── pom.xml
└── README.md

Status

✅ Operational - Test framework built and functional
✅ Security Tests Implemented - RBAC validation logic complete
✅ CI/CD Ready - Configured for GitHub Codespaces execution
🔧 Mock Application - Planned for future test scenarios

## Test Execution
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=SimpleFrameworkTest
