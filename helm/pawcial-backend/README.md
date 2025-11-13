# Pawcial Backend Helm Chart

This Helm chart deploys the Pawcial Backend application to Kubernetes with ArgoCD.

## Prerequisites

- Kubernetes cluster
- ArgoCD installed
- Vault running at `https://vault.guven.uk`
- PostgreSQL database at `192.168.0.58:5432`
- Namespace `pawcial` created in Kubernetes

## Installation

### 1. Create namespace
```bash
kubectl create namespace pawcial
```

### 2. Create Vault token secret manually

Before deploying, you need to create the Vault token secret:

```bash
kubectl create secret generic pawcial-backend-vault \
  --from-literal=VAULT_TOKEN='your-vault-root-token' \
  --from-literal=VAULT_URL='https://vault.guven.uk' \
  -n pawcial
```

Or edit the `templates/secret.yaml` file and replace `REPLACE_WITH_YOUR_VAULT_ROOT_TOKEN` with your actual token.

### 3. Install with Helm (Development)

```bash
helm install pawcial-backend ./helm/pawcial-backend \
  --namespace pawcial \
  --create-namespace
```

### 4. Install with Helm (Production)

```bash
helm install pawcial-backend ./helm/pawcial-backend \
  --namespace pawcial \
  --create-namespace \
  -f helm/pawcial-backend/values-prod.yaml
```

### 5. Deploy with ArgoCD

Apply the ArgoCD Application manifest:

```bash
kubectl apply -f argocd/application.yaml
```

## Configuration

All application secrets and configuration are loaded from HashiCorp Vault:

- Database credentials
- AWS S3 credentials
- Keycloak OIDC configuration
- Google OAuth credentials

### Vault Paths

- **Development**: `pawcial/dev`
- **Production**: `pawcial/prod`

## Values

Key configuration values in `values.yaml`:

- `replicaCount`: Number of pod replicas (default: 1)
- `image.repository`: Docker image repository
- `image.tag`: Docker image tag
- `vault.address`: Vault server URL
- `vault.secretPath`: Path to secrets in Vault
- `env.profile`: Quarkus profile (dev or prod)

## Accessing the Application

- **URL**: https://pawcial.guven.uk
- **Swagger UI**: https://pawcial.guven.uk/swagger-ui
- **Health Check**: https://pawcial.guven.uk/q/health

## Troubleshooting

### Check pod logs
```bash
kubectl logs -f deployment/pawcial-backend -n pawcial
```

### Check Vault connection
```bash
kubectl exec -it deployment/pawcial-backend -n pawcial -- env | grep VAULT
```

### Restart deployment
```bash
kubectl rollout restart deployment/pawcial-backend -n pawcial
```

