#!/bin/bash

# Load environment variables from .env file
if [ -f .env ]; then
    echo "📄 Loading .env file..."
    export $(cat .env | grep -v '^#' | xargs)
else
    echo "❌ Error: .env file not found!"
    echo "Please create a .env file with VAULT_URL and VAULT_TOKEN"
    exit 1
fi

# Validate required variables
if [ -z "$VAULT_URL" ] || [ -z "$VAULT_TOKEN" ]; then
    echo "❌ Error: VAULT_URL and VAULT_TOKEN must be set in .env file"
    exit 1
fi

echo "🚀 Starting Quarkus with Vault Configuration..."
echo "📍 VAULT_URL: $VAULT_URL"
echo "🔑 VAULT_TOKEN: ${VAULT_TOKEN:0:10}..."
echo ""

# Run Quarkus in dev mode
./mvnw clean quarkus:dev

