# GitHub Environments Setup

Configure these in GitHub repository settings:

- `dev`
- `staging`
- `production`

## Branch to Environment Mapping

- `develop` -> `dev`
- `staging` -> `staging`
- `main` -> `production`

## Recommended Environment Secrets

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SPRING_REDIS_HOST`
- `SPRING_REDIS_PORT`
- `JWT_SECRET`
- `GEMINI_API_KEY`
- `GEMINI_MODEL`

## Deployment Protection Rules

- Require reviewers before deployment for `production`
- Optional wait timer for `production`
- Restrict who can deploy to `production`
