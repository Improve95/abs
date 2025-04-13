FROM gradle:8.13.0-jdk21-alpine AS build

WORKDIR /app
COPY auth-service auth-service
COPY settings.gradle .
COPY .env .

RUN gradle build

FROM openjdk:21

COPY --from=build /app/auth-service/build/libs/auth-service-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "auth-service-0.0.1-SNAPSHOT.jar", "--server.port=8080"]