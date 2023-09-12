# FROM gradle:jdk17-focal
FROM eclipse-temurin:17-jdk-focal AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY api .
COPY dispersion-queue .
COPY domain-modules .
COPY security .
COPY socket .
RUN chomd +x ./gradlew
RUN ./gradlew bootJar

COPY --from=builder build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]