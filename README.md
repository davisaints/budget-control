# Budget Control API

## Overview

The **Budget Control API** is an interface that helps users manage their finances by tracking revenue and expenses, enabling them to generate monthly financial summaries.

## Technologies

- **Java 17**
- **Spring Boot 3.3.4**
- **PostgreSQL** (database)

## API Endpoints

### Revenue Endpoints

- **Retrieve all revenues:**  
  `GET /revenue`
- **Retrieve a specific revenue by ID:**  
  `GET /revenue/{id}`
- **Create a new revenue:**  
  `POST /revenue`
- **Update an existing revenue by ID:**  
  `PUT /revenue/{id}`
- **Delete a revenue by ID:**  
  `DELETE /revenue/{id}`
- **Retrieve revenues filtered by year and month:**  
  `GET /revenue/{year}/{month}`

### Expense Endpoints

- **Retrieve all expenses:**  
  `GET /expense`
- **Retrieve a specific expense by ID:**  
  `GET /expense/{id}`
- **Create a new expense:**  
  `POST /expense`
- **Update an existing expense by ID:**  
  `PUT /expense/{id}`
- **Delete an expense by ID:**  
  `DELETE /expense/{id}`
- **Retrieve expenses filtered by year and month:**  
  `GET /expense/{year}/{month}`

### Summary Endpoints

- **Retrieve the financial summary for a specific month:**  
  `GET /summary/{year}/{month}`

### Authentication Endpoints

- **Register a new user:**  
  `POST /auth/signup`
- **Authenticate user and return JWT token:**  
  `POST /auth/login`

## Setting Up the Project

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17**
- **PostgreSQL** 14.5 or higher (optional Docker setup for PostgreSQL)
- **Maven** for managing dependencies

### PostgreSQL Database Setup

The project uses PostgreSQL 14.5. You can either install it on your machine or use Docker to run a container. To set up using Docker, run the following command:

```bash
docker run --name localhost_postgresql -d \
-e POSTGRES_DB=bcontrol \
-e POSTGRES_USER=test \
-e POSTGRES_PASSWORD=r00t \
-p 5432:5432 \
postgres:14.5
```

### Cloning the Repository

To get started, clone the repository by running the following command:

```bash
git clone https://github.com/davisaints/budget-control.git
```

### Installing Dependencies

To install all the required project dependencies, navigate to the project directory and run the following command:

```bash
mvn dependency:resolve
```

### Running the Project

To start the Spring Boot application, run the following command:

```bash
mvn spring-boot:run
```

### Accessing the API

Once the application is running, you can access the API documentation via Swagger UI at:

http://localhost:8080/swagger-ui

This interface provides an easy way to test the API endpoints, view documentation, and interact with the application.
