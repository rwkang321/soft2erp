# soft2erp project decisions

## Folder

All project work is under:

- Windows: `D:\java\workspace\soft2erp`
- WSL: `/mnt/d/java/workspace/soft2erp`

Current source root:

- Java: `src/main/java/com/soft2erp/erp`
- Resources: `src/main/resources`
- MyBatis mapper XML: `src/main/resources/mapper`
- DB scripts: `src/main/resources/db`
- Development/admin Thymeleaf pages: `src/main/resources/templates/admin/dev`

## Stack decisions

- Database: Supabase PostgreSQL
- API style: REST API
- UI: Nexacro is the main UI client
- Thymeleaf: limited to admin/test/development utility pages
- Build: Gradle
- Persistence: MyBatis first, JPA only when explicitly needed

## Supabase requirements

Required values from the Supabase project dashboard:

- Project ref
- Database password
- Connection string
- Host and port
- SSL requirement

Application environment variables:

- `SOFT2ERP_DB_URL`
- `SOFT2ERP_DB_USERNAME`
- `SOFT2ERP_DB_PASSWORD`
- `SOFT2ERP_DB_POOL_MAX`
- `SOFT2ERP_DB_POOL_MIN`

Recommended JDBC URL patterns:

```text
# Direct connection
jdbc:postgresql://db.<project-ref>.supabase.co:5432/postgres?sslmode=require

# Shared pooler session mode, useful on IPv4-only networks
jdbc:postgresql://aws-<region>.pooler.supabase.com:5432/postgres?sslmode=require
```

For Spring Boot on a persistent server, start with direct connection when IPv6 is available.
If the network is IPv4-only and the Supabase IPv4 add-on is not used, use session pooler mode.
Avoid transaction pooler mode for this first server build because transaction pooling can conflict
with prepared statements.

## Naming rules

### Database tables

- Use lowercase snake_case.
- Use module prefixes.
- Use singular business nouns when practical.
- Use `_id` for surrogate primary keys.
- Use `_code` for business codes.
- Use `_yn` for yes/no flags.
- Use `_at` for timestamps.
- Use `_date` for dates.
- Use `created_*` and `updated_*` audit columns on master tables.

### Prefixes

- `sys_`: system, auth, user, role, menu, common code
- `mst_`: master data
- `sal_`: sales
- `pur_`: purchase
- `prd_`: production
- `inv_`: inventory
- `qlt_`: quality
- `log_`: logs and audit trails

### First schema targets

- `sys_common_code_group`
- `sys_common_code`
- `sys_user`
- `sys_role`
- `sys_user_role`
- `sys_menu`
- `sys_role_menu`
- `mst_item`

## API rules

- Base API path: `/api`
- Common response envelope: `ApiResponse<T>`
- Controller handles request/response only.
- Service owns business validation and transactions.
- Mapper owns SQL.
- Nexacro consumes REST JSON responses.

