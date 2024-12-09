# Jumbo Geo Application

## Overview

Jumbo Geo Application is a Spring Boot application designed to manage stores and their locations using MongoDB as the database. It provides a REST API to retrieve the nearest stores based on geographic coordinates.

---

## Features

- **MongoDB Integration**: Connects to MongoDB to store and query store locations.
- **Rate Limiting**: Configured to limit API requests using `bucket4j`.
- **Swagger Documentation**: Provides API documentation through Swagger UI.
- **Logging**: Added SLF4J-based logging for better observability.
- **Spring Security**: Secures endpoints and allows fine-grained access control.
- **Actuator**: Exposes health check and application metrics.
- **Java 21 Compatibility**: Upgraded to leverage the latest Java features.
- **DTOs and Interfaces**: Implements clean code practices using DTOs and interfaces.

---

## Getting Started

### Prerequisites

Ensure you have the following installed:

- **Java 21**
- **Maven 3.8+**
- **Docker and Docker Compose**

---

### Build and Run

1. **Clean and package the application**:
    ```sh
    mvn clean install package
    ```

2. **Start the application using Docker Compose**:
    ```sh
    docker compose up -d
    ```

3. **Check Application Status**:
    Visit the following link in your browser:
    ```
    http://localhost:8080/actuator/health
    ```

4. **Access Swagger Documentation**:
    Visit the following link in your browser:
    ```
    http://localhost:8080/swagger-ui/index.html
    ```

5. **Test the API**:
    Fetch the nearest stores:
    ```
    http://localhost:8080/api/v1/nearest-stores?latitude=52.37867&longitude=4.883832
    ```

---

## Key Technologies Used

| Feature            | Technology/Tool         |
|---------------------|-------------------------|
| REST API Framework  | Spring Boot            |
| Database            | MongoDB                |
| Logging             | SLF4J                  |
| API Documentation   | Springdoc + Swagger UI |
| Security            | Spring Security        |
| Rate Limiting       | bucket4j           |
| Metrics and Health  | Spring Boot Actuator   |
| Docker Management   | Docker Compose         |

---

## Completed Features

- **MongoDB Initialization**:
  - Imported data via `init.js`.
  - Created geospatial index for `location`.

- **SLF4J Logging**:
  - Added logging to service methods for observability.

- **Rate Limiter**:
  - Restricted API calls to avoid abuse.

- **Swagger API Docs**:
  - Accessible at `/swagger-ui/index.html`.

- **Spring Security**:
  - Secured endpoints while allowing public access to specific APIs.
