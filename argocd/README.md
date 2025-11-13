# ArgoCD Deployment Guide

This directory contains ArgoCD Application manifests for deploying Pawcial Backend.

## Prerequisites

1. ArgoCD installed and accessible at `argocd.guven.uk`
2. Kubernetes cluster with namespace `pawcial`
3. Git repository configured in ArgoCD
4. Vault token secret created in the `pawcial` namespace

## Setup Steps

### 1. Create Vault Token Secret

Before deploying via ArgoCD, create the Vault token secret:

```bash
kubectl create secret generic pawcial-backend-vault \
  --from-literal=VAULT_TOKEN='hvs.your-actual-vault-token' \
  --from-literal=VAULT_URL='https://vault.guven.uk' \
  -n pawcial
```

### 2. Add Git Repository to ArgoCD (if not already added)

```bash
argocd repo add ssh://git@192.168.0.79:2222/mustafaguven/pawcial-backend.git \
  --ssh-private-key-path ~/.ssh/id_rsa \
  --insecure-skip-server-verification
```

Or via ArgoCD UI:
- Go to Settings > Repositories
- Click "Connect Repo"
- Method: SSH
- Repository URL: `ssh://git@192.168.0.79:2222/mustafaguven/pawcial-backend.git`
- SSH private key: Paste your private key

### 3. Deploy Development Environment

```bash
kubectl apply -f argocd/application-dev.yaml
```

Or via ArgoCD UI:
- Go to Applications
- Click "New App"
- Use the YAML from `application-dev.yaml`

### 4. Deploy Production Environment

```bash
kubectl apply -f argocd/application-prod.yaml
```

## Accessing ArgoCD

- **URL**: https://argocd.guven.uk
- **CLI Login**: 
  ```bash
  argocd login argocd.guven.uk
  ```

## Managing Applications

### Sync Application
```bash
argocd app sync pawcial-backend-dev
```

### Check Application Status
```bash
argocd app get pawcial-backend-dev
```

### View Application Logs
```bash
argocd app logs pawcial-backend-dev
```

### Delete Application
```bash
kubectl delete -f argocd/application-dev.yaml
# or
argocd app delete pawcial-backend-dev
```

## Automated Sync

Both applications are configured with automated sync:
- **Prune**: Automatically delete resources when removed from Git
- **Self-heal**: Automatically sync when cluster state differs from Git
- **Retry**: Automatically retry failed syncs with exponential backoff

## Image Tag Updates

To update the Docker image:

1. Build and push new image to registry
2. Update `image.tag` in ArgoCD application or let CI/CD update it
3. ArgoCD will automatically detect and sync changes

## Troubleshooting

### Application Out of Sync
```bash
argocd app sync pawcial-backend-dev --force
```

### Check Sync Status
```bash
argocd app get pawcial-backend-dev --refresh
```

### View Events
```bash
kubectl get events -n pawcial --sort-by='.lastTimestamp'
```

### Check ArgoCD Application Health
```bash
argocd app wait pawcial-backend-dev --health
```

