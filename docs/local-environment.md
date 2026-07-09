# soft2erp local environment

Last checked: 2026-07-09

## Working folder

- Windows: `D:\java\workspace\soft2erp`
- WSL: `/mnt/d/java/workspace/soft2erp`

Run backend commands from:

```bash
cd /mnt/d/java/workspace/soft2erp
```

## Current verification

JDK 21 is installed in WSL.

```text
openjdk version "21.0.11" 2026-04-21
OpenJDK Runtime Environment (build 21.0.11+10-1-26.04.2-Ubuntu)
OpenJDK 64-Bit Server VM (build 21.0.11+10-1-26.04.2-Ubuntu, mixed mode, sharing)
javac 21.0.11
```

Gradle is not installed in WSL yet.

```text
gradle: command not found
```

## Required next tool

Install Gradle or add a Gradle Wrapper.

Recommended project direction:

1. Install Gradle once in WSL.
2. Generate Gradle Wrapper in this project.
3. Use `./gradlew` afterward so every developer runs the same Gradle version.

After Gradle is available:

```bash
gradle wrapper
./gradlew clean test
./gradlew bootRun
```

## Supabase environment variables

The project reads DB connection settings from environment variables.

```bash
export SOFT2ERP_DB_URL='jdbc:postgresql://db.<project-ref>.supabase.co:5432/postgres?sslmode=require'
export SOFT2ERP_DB_USERNAME='postgres'
export SOFT2ERP_DB_PASSWORD='<database-password>'
export SOFT2ERP_DB_POOL_MAX='10'
export SOFT2ERP_DB_POOL_MIN='2'
```

If the WSL/server network is IPv4-only, use Supabase session pooler instead of direct connection.

## DB scripts

Run these SQL files against Supabase PostgreSQL:

1. `src/main/resources/db/schema.sql`
2. `src/main/resources/db/seed.sql`

## Dev URLs after boot

- Dev page: `http://localhost:8080/admin/dev`
- Swagger UI: `http://localhost:8080/admin/dev/swagger`
- Common code API: `GET http://localhost:8080/api/system/common-codes`
- Item API: `GET http://localhost:8080/api/master/items`

