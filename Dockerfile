# First stage: complete build environment
FROM maven:3.8.3-openjdk-17 AS base

WORKDIR /app
ADD pom.xml pom.xml
ADD src src

FROM base as build
RUN mvn clean install

# Second stage: minimal runtime environment
FROM openjdk:17-oracle as production
COPY --from=build app/target/*engine*.jar engine.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/engine.jar"]
