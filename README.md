# Library Management System

Simple Library Management System built with Spring Boot.
The application allows managing books and their copies through REST API endpoints.

## Technologies

* Java 21
* Spring Boot 4.0.7
* Spring Data JPA
* PostgreSQL
* Flyway
* Lombok
* Spring Validation
* Swagger / OpenAPI
* Thymeleaf
* JUnit 5
* Mockito
* Docker

## Features

* Create, update, delete and retrieve books
* Update book copy availability status
* Input validation
* Custom Exception handling with error messages
* Database migrations using Flyway
* REST API documentation using Swagger
* Unit tests for service and controller
* Pagination and sorting for books endpoint
* Simple Thymeleaf frontend for displaying books and copies

# Running the application

## Requirements

Before running the application make sure you have installed:

* Java 21+
* Docker

## 1. Start PostgreSQL Database

Navigate to the `docker` directory:

```bash
cd docker
```

Then start the PostgreSQL container:

```bash
docker compose up -d
```


This will start PostgreSQL database container.

## 2. Run the application

Using Maven:

```bash
./mvnw spring-boot:run
```

or run the application directly from your IDE.

The application will start on:

```
http://localhost:8080
```

# Database configuration

PostgreSQL configuration:

```
Database: library_db
Username: library_user
Password: library_password
Port: 5432
```

Database initial data are created automatically using Flyway migrations.

# API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui.html
```

OpenAPI specification:

```
http://localhost:8080/v1/api-docs
```

# REST API Endpoints

## Books

### Get all books

```
GET /api/books
```

Supports pagination and sorting:

Example:

```
GET /api/books?page=0&size=10&sort=title,asc
```

### Create book

```
POST /api/books
```

Example request:

```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "publishedYear": 2008
}
```

### Get book by ID

```
GET /api/books/{id}
```

### Update book

```
PUT /api/books/{id}
```

### Delete book

```
DELETE /api/books/{id}
```

## Book Copies

### Get copies for a book

```
GET /api/books/{id}/copies
```

### Add book copy

```
POST /api/books/{id}/copies
```

### Update copy availability

```
PUT /api/books/{id}/copies/{copyId}
```

Example request:

```json
{
  "available": false
}
```

# Testing

The project contains:

* Service layer unit tests
* Controller layer tests using MockMvc and Mockito

# Frontend

A simple Thymeleaf frontend is available at:

```
http://localhost:8080/
```

The page displays:

* Book information
* Book copies
* Copy availability status

# Project Structure

```
src/main/java
|
├── controller
├── dto
├── exception
├── mapper
├── model
├── repository
├── service
└── serviceImpl
```

The application follows a layered architecture:

```
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```
