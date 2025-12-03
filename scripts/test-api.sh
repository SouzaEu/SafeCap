#!/bin/bash

# SafeCap API Test Script
# Quick smoke test for the API

set -e

BASE_URL=${1:-http://localhost:8080}

echo "Testing SafeCap API at $BASE_URL"
echo "=================================="

# Test 1: Health Check
echo -e "\n1. Testing Health Endpoint..."
HEALTH=$(curl -s "$BASE_URL/actuator/health")
if echo "$HEALTH" | grep -q "UP"; then
    echo "✓ Health check passed"
else
    echo "✗ Health check failed"
    exit 1
fi

# Test 2: Register User
echo -e "\n2. Testing User Registration..."
REGISTER_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/register" \
    -H "Content-Type: application/json" \
    -d '{
        "nome": "Test User",
        "email": "test'$(date +%s)'@example.com",
        "senha": "Test123456"
    }')

if echo "$REGISTER_RESPONSE" | grep -q "id"; then
    echo "✓ User registration passed"
    USER_EMAIL=$(echo "$REGISTER_RESPONSE" | grep -o '"email":"[^"]*"' | cut -d'"' -f4)
else
    echo "✗ User registration failed"
    echo "Response: $REGISTER_RESPONSE"
    exit 1
fi

# Test 3: Login
echo -e "\n3. Testing User Login..."
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/login" \
    -H "Content-Type: application/json" \
    -d '{
        "email": "'$USER_EMAIL'",
        "senha": "Test123456"
    }')

if echo "$LOGIN_RESPONSE" | grep -q "token"; then
    echo "✓ User login passed"
    TOKEN=$(echo "$LOGIN_RESPONSE" | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
else
    echo "✗ User login failed"
    echo "Response: $LOGIN_RESPONSE"
    exit 1
fi

# Test 4: Create Device
echo -e "\n4. Testing Device Creation..."
DEVICE_RESPONSE=$(curl -s -X POST "$BASE_URL/api/dispositivos" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $TOKEN" \
    -d '{
        "nome": "Test Device",
        "localizacao": "Test Location",
        "tipo": "TEMPERATURE",
        "ativo": true
    }')

if echo "$DEVICE_RESPONSE" | grep -q "id"; then
    echo "✓ Device creation passed"
    DEVICE_ID=$(echo "$DEVICE_RESPONSE" | grep -o '"id":[0-9]*' | cut -d':' -f2)
else
    echo "✗ Device creation failed"
    echo "Response: $DEVICE_RESPONSE"
    exit 1
fi

# Test 5: List Devices
echo -e "\n5. Testing Device Listing..."
DEVICES_RESPONSE=$(curl -s -X GET "$BASE_URL/api/dispositivos" \
    -H "Authorization: Bearer $TOKEN")

if echo "$DEVICES_RESPONSE" | grep -q "Test Device"; then
    echo "✓ Device listing passed"
else
    echo "✗ Device listing failed"
    echo "Response: $DEVICES_RESPONSE"
    exit 1
fi

# Test 6: Swagger Documentation
echo -e "\n6. Testing Swagger Documentation..."
SWAGGER_RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/swagger-ui.html")

if [ "$SWAGGER_RESPONSE" = "200" ]; then
    echo "✓ Swagger documentation accessible"
else
    echo "✗ Swagger documentation not accessible (HTTP $SWAGGER_RESPONSE)"
fi

echo -e "\n=================================="
echo "✓ All API tests passed!"
echo "=================================="
