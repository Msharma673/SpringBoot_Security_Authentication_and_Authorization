Project name: com.example.corporate (artifactId: corporate-app)
Java: 17+
Spring Boot: 3.2.x (specified in pom)

Quick start (local MySQL - recommended):
1. Install MySQL locally and create database:
   $ mysql -u root -p
   CREATE DATABASE corporate_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   CREATE USER 'corp'@'localhost' IDENTIFIED BY 'password';
   GRANT ALL PRIVILEGES ON corporate_db.* TO 'corp'@'localhost';
   FLUSH PRIVILEGES;

2. Copy .env.example -> .env and edit if desired (or set env vars). Default application.yml uses placeholders.

3. Build and run:
   $ mvn clean package
   $ mvn spring-boot:run
   OR run the generated jar:
   $ java -jar target/corporate-app-0.0.1-SNAPSHOT.jar

4. Default app will connect to local MySQL using values in application.yml / environment.

Docker (optional):
- Dockerfile provided. Optional docker-compose to stand up MySQL and the app:
  $ docker-compose up --build
- By default the README and configs assume local MySQL. Use docker-compose only if you prefer containerized MySQL.

Run tests:
  $ mvn test

Useful commands:
  - Package: mvn clean package
  - Run: mvn spring-boot:run
  - Tests: mvn test

Postman / curl:
- Example curl commands included at the end of this doc.

Notes:
- Logging: Logback configured in src/main/resources/logback-spring.xml prints timestamp, level, thread, logger, class, file:line and stacktraces.
- Flyway: migrations located in src/main/resources/db/migration and run on startup.
- JWT secret: set via environment var JWT_SECRET or in application.yml for dev profile.
