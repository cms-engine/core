# First stage: complete build environment
FROM gradle:8.12-jdk21 AS base

WORKDIR /app
ADD build.gradle.kts build.gradle.kts
ADD settings.gradle.kts settings.gradle.kts
ADD lombok.config lombok.config
ADD src src

FROM base AS build
RUN gradle build

# Second stage: minimal runtime environment
FROM eclipse-temurin:21-jre-alpine AS production
COPY --from=build app/build/libs/*.jar cms-engine.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/cms-engine.jar"]
