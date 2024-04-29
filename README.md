# account-registry Service

A 24/7 web service that allows users to manage their banking details.

The application provides REST APIs for users to: register, login, and manage their account 
details.

The resources are secured with JWT authentication.

## Setup local development environment
The minimum requirements to run the application are:
- ``Java 21``
  - any ``JDK 21`` distro should work: Eclipse Temurin, OpenJDK, Oracle JDK..etc
- ``Docker engine``
  - the integration tests need a Docker engine to run MySQL in a container;
  - Docker Desktop is an easy way to get Docker on your OS;
    - https://docs.docker.com/desktop/
- If you run the application through an IDE, such as IntelliJ IDEA, you may need to enable 
  annotation processing to support Lombok and MapStruct.
- Local ``MySQL`` database instance with an empty schema named ``account_registry``.

## Building the application
The application can be built via gradle on your IDE.

Or you can build the application via the command line in terminal:

````./gradlew clean build````

## Running the application

The project provides an easy to use ``dev`` Spring profile:
[application-dev.yml](src/main/resources/application-dev.yml).

From that file, you can adjust the connection to a local MySQL database instance.

Or you may simply run a MySQL instance in a Docker container with the expected url configuration 
and credentials described in the file.


The application then can be run in two ways:
- through the IDE
  - IDEs via auto-detection of the main class with the Spring profile: dev
- through the command line
  - ```./gradlew bootRun --args='--spring.profiles.active=dev'```

## Features

### OpenAPI documentation
The application provides an OpenAPI documentation for better integration with other services.

- The Swagger UI can be accessed via:
  - http://localhost:8080/swagger-ui.html
- The OpenAPI yaml can be downloaded via:
  - http://localhost:8080/v3/api-docs.yaml

## Register an account 
via POST /accounts
- 
- Login and generate JWT
- Account Overview listing
- 