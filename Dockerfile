# First stage: complete build environment
FROM gradle:8.10.2-jdk21 AS base

WORKDIR /app
ADD build.gradle.kts build.gradle.kts
ADD settings.gradle.kts settings.gradle.kts
ADD src src

FROM base as build
RUN gradle build

# Second stage: minimal runtime environment
FROM eclipse-temurin:21-jre-alpine as production
COPY --from=build app/build/libs/*.jar cms-engine.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/cms-engine.jar"]
