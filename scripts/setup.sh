#!/bin/bash

# SafeCap Development Environment Setup Script
# This script sets up the development environment for SafeCap

set -e

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}SafeCap Development Environment Setup${NC}"
echo -e "${BLUE}========================================${NC}\n"

# Check Java
echo -e "${YELLOW}Checking Java installation...${NC}"
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
    if [ "$JAVA_VERSION" -ge 17 ]; then
        echo -e "${GREEN}✓ Java $JAVA_VERSION found${NC}"
    else
        echo -e "${RED}✗ Java 17 or higher is required. Current version: $JAVA_VERSION${NC}"
        exit 1
    fi
else
    echo -e "${RED}✗ Java is not installed${NC}"
    echo -e "${YELLOW}Please install Java 17 or higher${NC}"
    exit 1
fi

# Check Maven
echo -e "${YELLOW}Checking Maven installation...${NC}"
if command -v mvn &> /dev/null; then
    MVN_VERSION=$(mvn -version | head -n 1 | awk '{print $3}')
    echo -e "${GREEN}✓ Maven $MVN_VERSION found${NC}"
else
    echo -e "${RED}✗ Maven is not installed${NC}"
    echo -e "${YELLOW}Please install Maven 3.8 or higher${NC}"
    exit 1
fi

# Check Git
echo -e "${YELLOW}Checking Git installation...${NC}"
if command -v git &> /dev/null; then
    GIT_VERSION=$(git --version | awk '{print $3}')
    echo -e "${GREEN}✓ Git $GIT_VERSION found${NC}"
else
    echo -e "${RED}✗ Git is not installed${NC}"
    exit 1
fi

# Create .env file if it doesn't exist
echo -e "\n${YELLOW}Setting up environment file...${NC}"
if [ ! -f ".env" ]; then
    if [ -f ".env.example" ]; then
        cp .env.example .env
        echo -e "${GREEN}✓ Created .env from .env.example${NC}"
        echo -e "${YELLOW}⚠ Please update .env with your configuration${NC}"
    else
        echo -e "${YELLOW}⚠ .env.example not found. Skipping .env creation${NC}"
    fi
else
    echo -e "${GREEN}✓ .env already exists${NC}"
fi

# Install dependencies
echo -e "\n${YELLOW}Installing project dependencies...${NC}"
mvn clean install -DskipTests
echo -e "${GREEN}✓ Dependencies installed${NC}"

# Create logs directory
echo -e "\n${YELLOW}Creating logs directory...${NC}"
mkdir -p logs
echo -e "${GREEN}✓ Logs directory created${NC}"

# Check Docker (optional)
echo -e "\n${YELLOW}Checking Docker installation (optional)...${NC}"
if command -v docker &> /dev/null; then
    DOCKER_VERSION=$(docker --version | awk '{print $3}' | sed 's/,//')
    echo -e "${GREEN}✓ Docker $DOCKER_VERSION found${NC}"
else
    echo -e "${YELLOW}⚠ Docker not found (optional for development)${NC}"
fi

# Summary
echo -e "\n${BLUE}========================================${NC}"
echo -e "${GREEN}Setup completed successfully!${NC}"
echo -e "${BLUE}========================================${NC}\n"

echo -e "${BLUE}Next steps:${NC}"
echo -e "1. Review and update ${YELLOW}.env${NC} file with your configuration"
echo -e "2. Run ${YELLOW}mvn spring-boot:run${NC} to start the application"
echo -e "3. Visit ${YELLOW}http://localhost:8080/swagger-ui.html${NC} for API documentation"
echo -e "4. Check ${YELLOW}README.md${NC} for more information\n"

echo -e "${GREEN}Happy coding! 🚀${NC}\n"
