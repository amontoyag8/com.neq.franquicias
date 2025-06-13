# Etapa 1: Construcción del JAR usando Gradle
FROM gradle:8.7.0-jdk17-alpine AS build
USER root
RUN apk update && apk upgrade --no-cache && apk add --no-cache curl ca-certificates
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN ./gradlew clean build -x test

# Etapa 2: Imagen final para ejecutar la app
FROM eclipse-temurin:17-jre
VOLUME /tmp
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]