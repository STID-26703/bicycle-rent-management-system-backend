# bicycle-rent-management-system-backend

## Bicycle Rent Management System Backend

- Student Name: Student Name
- Student ID: STID-26703
- Project Name: Bicycle Rent Management System Backend
- GitHub Repository: https://github.com/STID-26703/bicycle-rent-management-system-backend

This project is a Spring Boot backend for managing bicycle rentals, racks, accessories, customers, hires, return charges, and dashboard metrics.

## Features

- CRUD for accessories, racks, bicycles, customers, and hires
- Search and reporting endpoints for the midterm requirements
- Business-rule validation for status transitions and date rules
- Return charge management
- Dashboard summary endpoints

## Prerequisites

- Java 21
- Maven 3.9+
- MySQL 8.x
- A local database named `bicycle_rent_db`

## Database setup

1. Start MySQL.
2. Create the database:

```sql
CREATE DATABASE bicycle_rent_db;
```

3. Update credentials in `src/main/resources/application.properties` if needed.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bicycle_rent_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=asd123
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Run the API

From the project root:

```bash
mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

## Project endpoints

- Root path: http://localhost:8080/
- Dashboard summary: http://localhost:8080/api/dashboard/summary
- Bicycles: http://localhost:8080/api/bicycles/all
- Customers: http://localhost:8080/api/customers/all
- Hires: http://localhost:8080/api/hires/all

## Postman collection

The requirement-based Postman collection is included at the repository root as:

- `bicycle-rent-management-system-midterm.postman_collection.json`

## Notes

- The app exposes a JSON response at the root URL for a quick health check.
- The API is designed for local MySQL-backed testing.

## Submission summary

This repository contains the backend implementation, the README, and the Postman collection required for the midterm submission.
