# CMS-Engine

CMS-Engine is a powerful and flexible Content Management System designed to simplify the creation and management of
digital content, allowing you to focus on your website or application's front-end while taking care of the backend.

## Get the app

____
Prerequisites

- Java 17
- PostgreSQL

Before you can run the application, you need to get the application source code onto your machine.

1. Clone the getting-started repository using the following command:

```bash
git clone https://github.com/lipiridi/cms-engine.git
```

2. Go to the app folder

```bash
cd cms-engine
```

3. Configure your database settings in application.yml
4. Build and run project locally:

```bash
./gradlew jar
java -jar build/libs/awd-backoffice-0.0.1-SNAPSHOT.jar
```

or

```bash
./gradlew bootRun
```

## Building image

```bash
docker build -t cms-engine .
```
