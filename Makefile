.PHONY: help build clean test run docker-build docker-run docker-stop install coverage lint format check-style security-check

# Colors for output
BLUE := \033[0;34m
GREEN := \033[0;32m
RED := \033[0;31m
NC := \033[0m # No Color

help: ## Show this help message
	@echo '$(BLUE)SafeCap - Available Commands:$(NC)'
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "  $(GREEN)%-20s$(NC) %s\n", $$1, $$2}'

install: ## Install dependencies
	@echo '$(BLUE)Installing dependencies...$(NC)'
	mvn clean install -DskipTests

build: ## Build the project
	@echo '$(BLUE)Building project...$(NC)'
	mvn clean package -DskipTests

clean: ## Clean build artifacts
	@echo '$(BLUE)Cleaning build artifacts...$(NC)'
	mvn clean
	rm -rf target/

test: ## Run all tests
	@echo '$(BLUE)Running tests...$(NC)'
	mvn test

test-integration: ## Run integration tests
	@echo '$(BLUE)Running integration tests...$(NC)'
	mvn verify -P integration-tests

coverage: ## Generate test coverage report
	@echo '$(BLUE)Generating coverage report...$(NC)'
	mvn clean test jacoco:report
	@echo '$(GREEN)Coverage report: target/site/jacoco/index.html$(NC)'

run: ## Run the application
	@echo '$(BLUE)Starting application...$(NC)'
	mvn spring-boot:run

run-dev: ## Run with dev profile
	@echo '$(BLUE)Starting application in dev mode...$(NC)'
	mvn spring-boot:run -Dspring-boot.run.profiles=dev

run-prod: ## Run with prod profile
	@echo '$(BLUE)Starting application in prod mode...$(NC)'
	java -jar target/safecap-1.0.0.jar --spring.profiles.active=prod

lint: check-style ## Run linting (alias for check-style)

check-style: ## Check code style with Checkstyle
	@echo '$(BLUE)Checking code style...$(NC)'
	mvn checkstyle:check

format: ## Format code
	@echo '$(BLUE)Formatting code...$(NC)'
	mvn spotless:apply

check-bugs: ## Check for bugs with SpotBugs
	@echo '$(BLUE)Running SpotBugs analysis...$(NC)'
	mvn spotbugs:check

security-check: ## Run security vulnerability check
	@echo '$(BLUE)Running security check...$(NC)'
	mvn dependency-check:check

verify: ## Run all checks (tests, style, bugs, security)
	@echo '$(BLUE)Running all verifications...$(NC)'
	mvn clean verify

docker-build: ## Build Docker image
	@echo '$(BLUE)Building Docker image...$(NC)'
	docker build -t safecap:latest .

docker-run: ## Run Docker container
	@echo '$(BLUE)Starting Docker container...$(NC)'
	docker run -d -p 8080:8080 --name safecap safecap:latest

docker-stop: ## Stop Docker container
	@echo '$(BLUE)Stopping Docker container...$(NC)'
	docker stop safecap || true
	docker rm safecap || true

docker-compose-up: ## Start with docker-compose
	@echo '$(BLUE)Starting with docker-compose...$(NC)'
	docker-compose up -d

docker-compose-down: ## Stop docker-compose
	@echo '$(BLUE)Stopping docker-compose...$(NC)'
	docker-compose down

docker-compose-logs: ## View docker-compose logs
	docker-compose logs -f

docker-clean: ## Clean Docker resources
	@echo '$(BLUE)Cleaning Docker resources...$(NC)'
	docker-compose down -v
	docker rmi safecap:latest || true

quick-check: ## Quick check (fast tests + style check)
	@echo '$(BLUE)Running quick checks...$(NC)'
	mvn test checkstyle:check -DskipSlowTests=true

full-check: verify coverage security-check ## Run full CI pipeline locally

release: ## Create a release build
	@echo '$(BLUE)Creating release build...$(NC)'
	mvn clean package -DskipTests -P release

deploy-local: build docker-build docker-run ## Build and deploy locally

logs: ## Show application logs
	@echo '$(BLUE)Showing logs...$(NC)'
	tail -f logs/application.log

db-console: ## Open H2 database console
	@echo '$(GREEN)H2 Console: http://localhost:8080/h2-console$(NC)'
	@echo '$(GREEN)JDBC URL: jdbc:h2:mem:safecapdb$(NC)'
	@echo '$(GREEN)Username: sa$(NC)'
	@echo '$(GREEN)Password: (empty)$(NC)'

swagger: ## Open Swagger UI
	@echo '$(GREEN)Swagger UI: http://localhost:8080/swagger-ui.html$(NC)'
	open http://localhost:8080/swagger-ui.html || xdg-open http://localhost:8080/swagger-ui.html || echo "Please open http://localhost:8080/swagger-ui.html in your browser"

update-deps: ## Update dependencies to latest versions
	@echo '$(BLUE)Checking for dependency updates...$(NC)'
	mvn versions:display-dependency-updates

dependency-tree: ## Show dependency tree
	@echo '$(BLUE)Showing dependency tree...$(NC)'
	mvn dependency:tree

version: ## Show project version
	@mvn help:evaluate -Dexpression=project.version -q -DforceStdout

info: ## Show project information
	@echo '$(BLUE)Project: SafeCap$(NC)'
	@echo '$(BLUE)Version: $(NC)'$(shell mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
	@echo '$(BLUE)Java Version: $(NC)'$(shell java -version 2>&1 | head -n 1)
	@echo '$(BLUE)Maven Version: $(NC)'$(shell mvn -version | head -n 1)
