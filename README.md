# candidate-management
REST API to candidate management. This project work with design first, this is the principle to build the contract with OPEN API and then write the code.

# Versions:
- Java 17
- Spring Boot 2.7.3
- Spring Security 5.7.3
- Spring Framework 5.3.22
- Embedded DB (H2)
- Swagger2

# Port
8080

# Manage Dependencies
Maven

# Steps to run the project
- Clone the repository https://github.com/AlexanderOchoa/api-user
- Config JDK 17 in the IDE
- Download dependencies (mvn clean install)
- Run the project (mvn spring-boot:run)
- Import the collection /resources/collection/candidate-management.postman_collection.json
- Generate token jwt
- Call methods

# Endpoints
http://localhost:8080/login

http://localhost:8080/api/v1/candidates

# Contract (Swagger)
This project works with API FIRST resources/openapi/api.yaml

# Test REST API
Use resources/collection/candidate-management.postman_collection.json

# Licence
Alexander Ochoa - https://github.com/AlexanderOchoa