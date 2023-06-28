# FROM gradle:jdk17-focal
FROM eclipse-temurin:17-jdk-focal AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chomd +x ./gradlew
RUN ./gradlew bootJar

COPY --from=builder build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
VOLUME /myApplication/java
