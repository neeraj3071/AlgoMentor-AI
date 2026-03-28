# Branching Strategy

## Branches

- `main`: Production-ready code only
- `staging`: Pre-production integration and release verification
- `develop`: Active development integration branch
- `feature/*`: Short-lived feature branches created from `develop`
- `hotfix/*`: Critical fixes created from `main`

## Environment Mapping

- `develop` -> `dev` environment
- `staging` -> `staging` environment
- `main` -> `production` environment

## Standard Flow

1. Create feature branch from `develop`
2. Open PR into `develop`
3. After QA on dev, promote to `staging`
4. After staging validation, promote to `main`

## Hotfix Flow

1. Create `hotfix/*` from `main`
2. Merge into `main`
3. Back-merge same fix into `staging` and `develop`

## Protection Rules (to configure in GitHub)

Apply to `main`, `staging`, `develop`:

- Require pull request before merge
- Require status checks to pass
- Require up-to-date branches before merge
- Restrict direct pushes
- Require at least 1 reviewer (`main` should require 2)
