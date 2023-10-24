# First stage: complete build environment
FROM gradle:8.3-jdk17 AS base

WORKDIR /app
ADD build.gradle.kts build.gradle.kts
ADD settings.gradle.kts settings.gradle.kts
ADD src src

FROM base as build
RUN gradle build --no-daemon

# Second stage: minimal runtime environment
FROM eclipse-temurin:17-jre-alpine as production
COPY --from=build app/build/libs/*.jar cms-engine.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/cms-engine.jar"]
