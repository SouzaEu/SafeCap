#!/bin/bash

# SafeCap Health Check Script
# Checks if the application is running and healthy

set -e

HOST=${1:-localhost}
PORT=${2:-8080}
TIMEOUT=30

echo "Checking SafeCap health at $HOST:$PORT..."

# Wait for application to be ready
echo "Waiting for application to start (timeout: ${TIMEOUT}s)..."
for i in $(seq 1 $TIMEOUT); do
    if curl -f -s "http://$HOST:$PORT/actuator/health" > /dev/null 2>&1; then
        echo "✓ Application is running!"
        
        # Get detailed health info
        HEALTH=$(curl -s "http://$HOST:$PORT/actuator/health")
        echo -e "\nHealth Status:"
        echo "$HEALTH" | python3 -m json.tool 2>/dev/null || echo "$HEALTH"
        
        # Check if status is UP
        STATUS=$(echo "$HEALTH" | grep -o '"status":"[^"]*"' | cut -d'"' -f4)
        if [ "$STATUS" = "UP" ]; then
            echo -e "\n✓ Application is healthy!"
            exit 0
        else
            echo -e "\n✗ Application is not healthy. Status: $STATUS"
            exit 1
        fi
    fi
    
    echo -n "."
    sleep 1
done

echo -e "\n✗ Application failed to start within ${TIMEOUT} seconds"
exit 1
