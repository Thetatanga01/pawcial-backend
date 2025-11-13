#!/bin/bash

# Pawcial Backend Deployment Script
# This script helps you deploy the application via ArgoCD

set -e

NAMESPACE="pawcial"
ARGOCD_NAMESPACE="argocd"

echo "🚀 Pawcial Backend Deployment Script"
echo "===================================="
echo ""

# Function to check if command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check prerequisites
echo "📋 Checking prerequisites..."

if ! command_exists kubectl; then
    echo "❌ kubectl is not installed"
    exit 1
fi

if ! command_exists argocd; then
    echo "⚠️  argocd CLI is not installed (optional but recommended)"
    echo "   You can install it from: https://argo-cd.readthedocs.io/en/stable/cli_installation/"
fi

# Check if namespace exists
if ! kubectl get namespace $NAMESPACE >/dev/null 2>&1; then
    echo "📦 Creating namespace: $NAMESPACE"
    kubectl create namespace $NAMESPACE
else
    echo "✅ Namespace $NAMESPACE already exists"
fi

# Check if Vault secret exists
if ! kubectl get secret pawcial-backend-vault -n $NAMESPACE >/dev/null 2>&1; then
    echo ""
    echo "⚠️  Vault secret not found!"
    echo ""
    read -p "Enter your Vault token: " VAULT_TOKEN
    read -p "Enter your Vault URL [https://vault.guven.uk]: " VAULT_URL
    VAULT_URL=${VAULT_URL:-https://vault.guven.uk}

    echo "🔐 Creating Vault secret..."
    kubectl create secret generic pawcial-backend-vault \
        --from-literal=VAULT_TOKEN="$VAULT_TOKEN" \
        --from-literal=VAULT_URL="$VAULT_URL" \
        -n $NAMESPACE
    echo "✅ Vault secret created"
else
    echo "✅ Vault secret already exists"
fi

echo ""
echo "📝 Select deployment environment:"
echo "1) Development (dev)"
echo "2) Production (prod)"
echo "3) Both"
read -p "Enter choice [1-3]: " CHOICE

case $CHOICE in
    1)
        echo "🚀 Deploying Development environment..."
        kubectl apply -f argocd/application-dev.yaml
        echo "✅ Development application created in ArgoCD"
        ;;
    2)
        echo "🚀 Deploying Production environment..."
        kubectl apply -f argocd/application-prod.yaml
        echo "✅ Production application created in ArgoCD"
        ;;
    3)
        echo "🚀 Deploying both environments..."
        kubectl apply -f argocd/application-dev.yaml
        kubectl apply -f argocd/application-prod.yaml
        echo "✅ Both applications created in ArgoCD"
        ;;
    *)
        echo "❌ Invalid choice"
        exit 1
        ;;
esac

echo ""
echo "✨ Deployment initiated!"
echo ""
echo "📊 Check status with:"
echo "   kubectl get applications -n $ARGOCD_NAMESPACE"
if command_exists argocd; then
    echo "   argocd app get pawcial-backend-dev"
fi
echo ""
echo "🌐 Access ArgoCD UI at: https://argocd.guven.uk"
echo "🌐 Access Application at: https://pawcial.guven.uk"
echo ""

