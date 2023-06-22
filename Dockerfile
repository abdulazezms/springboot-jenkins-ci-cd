FROM openjdk:17-jdk-slim

RUN mkdir /app


# Copying the file in this directory with the jar extension into the container's /app folder, and give it a fixed name to be consistent.

COPY target/*.jar /app/app.jar


CMD ["java", "-jar", "/app/app.jar"]