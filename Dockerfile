# First stage: complete build environment
FROM gradle:8.12-jdk21 AS base

# Install curl and Node.js
RUN apt-get update && apt-get install -y curl && \
    curl -fsSL https://deb.nodesource.com/setup_20.x | bash - && \
    apt-get install -y nodejs && \
    npm install -g npm@latest

WORKDIR /app
ADD build.gradle.kts build.gradle.kts
ADD settings.gradle.kts settings.gradle.kts
ADD lombok.config lombok.config
ADD src src
ADD frontend frontend

FROM base AS build
RUN gradle build

# Second stage: minimal runtime environment
FROM eclipse-temurin:21-jre-alpine AS production
COPY --from=build app/build/libs/*.jar cms-engine.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/cms-engine.jar"]
