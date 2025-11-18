#!/bin/bash

set -e

IMAGE_NAME="sf-tech-sales-api"
CONTAINER_NAME="sf-tech-sales-api"

echo "Building Docker image..."
docker build -t $IMAGE_NAME:latest .

echo "Stopping existing container if running..."
docker stop $CONTAINER_NAME 2>/dev/null || true
docker rm $CONTAINER_NAME 2>/dev/null || true

echo "Starting container..."
docker run -d \
  --name $CONTAINER_NAME \
  -p 8080:8080 \
  -e DATABASE_URL="${DATABASE_URL}" \
  -e DATABASE_USERNAME="${DATABASE_USERNAME}" \
  -e DATABASE_PASSWORD="${DATABASE_PASSWORD}" \
  -e SPRING_PROFILES_ACTIVE="${SPRING_PROFILES_ACTIVE:-prod}" \
  --restart unless-stopped \
  $IMAGE_NAME:latest

echo "Container started! Check logs with: docker logs -f $CONTAINER_NAME"

