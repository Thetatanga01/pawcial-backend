# Pawcial Backend - Kubernetes Deployment

This repository contains Kubernetes deployment configurations for the Pawcial Backend application using Helm and ArgoCD.

## 📁 Structure

```
pawcial-backend/
├── helm/
│   └── pawcial-backend/          # Helm chart
│       ├── Chart.yaml             # Chart metadata
│       ├── values.yaml            # Default values (dev)
│       ├── values-prod.yaml       # Production values
│       ├── templates/             # Kubernetes manifests
│       │   ├── deployment.yaml
│       │   ├── service.yaml
│       │   ├── ingress.yaml
│       │   ├── secret.yaml
│       │   ├── serviceaccount.yaml
│       │   └── hpa.yaml
│       └── README.md
└── argocd/
    ├── application-dev.yaml       # ArgoCD app for dev
    ├── application-prod.yaml      # ArgoCD app for prod
    ├── deploy.sh                  # Deployment script
    └── README.md
```

## 🎯 Architecture

- **Application**: Quarkus-based Java/Kotlin backend
- **Database**: PostgreSQL (external at 192.168.0.58)
- **Secret Management**: HashiCorp Vault
- **Container Registry**: GitLab Registry (192.168.0.79:5050)
- **Deployment**: ArgoCD (GitOps)
- **Domain**: pawcial.guven.uk
- **Namespace**: pawcial

## 🚀 Quick Start

### Prerequisites

1. Kubernetes cluster
2. ArgoCD installed at `argocd.guven.uk`
3. Vault running at `https://vault.guven.uk`
4. kubectl configured
5. Access to GitLab registry

### Deployment Steps

#### Option 1: Using the Deploy Script (Recommended)

```bash
cd argocd
./deploy.sh
```

The script will:
- Create the `pawcial` namespace
- Create Vault token secret (if not exists)
- Deploy the application via ArgoCD

#### Option 2: Manual Deployment

1. **Create namespace:**
   ```bash
   kubectl create namespace pawcial
   ```

2. **Create Vault secret:**
   ```bash
   kubectl create secret generic pawcial-backend-vault \
     --from-literal=VAULT_TOKEN='No5X2dwo8mylNU' \
     --from-literal=VAULT_URL='https://vault.guven.uk' \
     -n pawcial
   ```

3. **Deploy with ArgoCD:**
   ```bash
   # For development
   kubectl apply -f argocd/application-dev.yaml
   
   # For production
   kubectl apply -f argocd/application-prod.yaml
   ```

## 🔐 Vault Integration

All application secrets are managed by HashiCorp Vault:

### Development Secrets Path: `pawcial/dev`
- Database credentials
- AWS S3 credentials
- Keycloak OIDC configuration
- Google OAuth credentials
- CORS origins
- HTTP port and other configs

### Production Secrets Path: `pawcial/prod`
Same structure as dev but with production values.

### Required Vault Secrets

```json
{
  "aws.access.key.id": "...",
  "aws.secret.access.key": "...",
  "aws.s3.bucket-name": "...",
  "aws.s3.region": "...",
  "quarkus.datasource.jdbc.url": "...",
  "quarkus.datasource.username": "...",
  "quarkus.datasource.password": "...",
  "quarkus.oidc.auth-server-url": "...",
  "quarkus.oidc.client-id": "...",
  "quarkus.oidc.credentials.secret": "...",
  "quarkus.oidc.token.issuer": "...",
  "quarkus.oidc.google.client-id": "...",
  "quarkus.oidc.google.credentials.secret": "...",
  "quarkus.http.cors.origins": "...",
  "quarkus.http.port": "8000",
  "quarkus.log.level": "..."
}
```

## 📊 Monitoring & Management

### ArgoCD UI
- URL: https://argocd.guven.uk
- View application status
- Trigger manual syncs
- View deployment history

### Application Access
- API: https://pawcial.guven.uk
- Swagger UI: https://pawcial.guven.uk/swagger-ui
- Health Check: https://pawcial.guven.uk/q/health

### Useful Commands

```bash
# Check application status
kubectl get pods -n pawcial
kubectl get applications -n argocd

# View logs
kubectl logs -f deployment/pawcial-backend -n pawcial

# Describe deployment
kubectl describe deployment pawcial-backend -n pawcial

# Restart deployment
kubectl rollout restart deployment/pawcial-backend -n pawcial

# Check ArgoCD sync status
argocd app get pawcial-backend-dev
argocd app sync pawcial-backend-dev
```

## 🔄 CI/CD Workflow

1. **Code changes** pushed to GitLab
2. **GitLab CI** builds Docker image
3. **Image pushed** to GitLab registry
4. **Git repository updated** with new image tag (optional)
5. **ArgoCD detects** changes automatically
6. **ArgoCD syncs** and deploys new version

## 🛠️ Configuration

### Development Environment
- Profile: `dev`
- Replicas: 1
- Resources: 500m CPU, 1Gi RAM
- Vault path: `pawcial/dev`
- Auto-scaling: Disabled

### Production Environment
- Profile: `prod`
- Replicas: 2
- Resources: 1 CPU, 2Gi RAM
- Vault path: `pawcial/prod`
- Auto-scaling: Enabled (2-5 replicas)

## 🐛 Troubleshooting

### Pod not starting
```bash
kubectl describe pod <pod-name> -n pawcial
kubectl logs <pod-name> -n pawcial
```

### Vault connection issues
```bash
# Check Vault secret
kubectl get secret pawcial-backend-vault -n pawcial -o yaml

# Verify environment variables in pod
kubectl exec -it <pod-name> -n pawcial -- env | grep VAULT
```

### ArgoCD sync issues
```bash
# Force sync
argocd app sync pawcial-backend-dev --force

# Check application details
argocd app get pawcial-backend-dev

# View sync history
argocd app history pawcial-backend-dev
```

## 📝 License

Private - Pawcial Project

