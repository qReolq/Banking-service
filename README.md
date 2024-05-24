# Banking-service
A simple REST API for managing banking operations

## Technologies
Spring(Boot, Security, JPA), Swagger, Docker, Maven, PostgreSQL, Java, Liquibase, JUnit, Mockito, JWT

## Environments

To run this application you need to create `.env` file in root directory with next environments:

- `POSTGRES_HOST` - host of Postgresql database
- `POSTGRES_USERNAME` - username for Postgresql database
- `POSTGRES_PASSWORD` - password for Postgresql database
- `POSTGRES_DATABASE` - name of Postgresql database
- `JWT_SECRET` - secret string for JWT tokens

## Quick start
1. Clone this repo into folder.

```Bash
git clone https://github.com/qReolq/Banking-service.git
cd Banking-service
```
2. Start docker compose

```Bash
docker compose up
```
3. Go to localhost:8080/swagger-ui/index.html#/
