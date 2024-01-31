# spring-boot
## Technical:

1. Spring Boot 3.1.0
2. Java 17
3. Thymeleaf
4. Bootstrap v.4.3.1

## Setup with Intellij IDE
1. Create project from Initializr: File > New > project > Spring Initializr
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Static: src/main/resource/static
4. Create database with name "demo" as configuration in application.properties
5. Run sql script to create table doc/data.sql

## New Features:
1. Implemented CRUD operations for each domain object in the package repository.
2. Implemented controller for each domain object in the package controller.
3. Configured session-based authentication using Spring Security.