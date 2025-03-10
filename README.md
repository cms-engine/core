# CMS-Engine

CMS-Engine is a powerful and flexible Content Management System designed to simplify the creation and management of
digital content, allowing you to focus on your website or application's front-end while taking care of the backend.

## Get the app

____
Prerequisites

- Java 21
- Node.js && NPM
- PostgreSQL

Before you can run the application, you need to get the application source code onto your machine.

1. Clone the getting-started repository using the following command:

```bash
git clone https://github.com/cms-engine/core.git
```

2. Go to the app folder

```bash
cd cms-engine
```

3. Configure your database settings in application.yml
4. Build and run project locally:

```bash
./gradlew build
./gradlew bootRun
```

### Run with embedded h2 database

In case you don't want to install PostgreSQL, you can run the project with an embedded database called H2.

```bash
./gradlew build
./gradlew bootRun --args='--spring.profiles.active=h2'
```

### Run BE for FE developing

It allows `localhost` pattern in CORS configuration

```bash
./gradlew build
./gradlew bootRun --args='--spring.profiles.active=fedev'
```

## Docker

### Building image

```bash
docker build -t cms-engine .
```

### All in one

To simplify running the application with a PostgreSQL database, use Docker Compose.

```bash
docker-compose up --build
```
