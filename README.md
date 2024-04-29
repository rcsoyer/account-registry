# account-registry Service

A 24/7 web service that allows users to manage their banking details.

The application provides REST APIs for users to: register, login, and manage their account
details.

The resources are secured with JWT, Bearer Token, authentication.

## Setup local development environment

The minimum requirements to run the application are:

- ``Java 21``
    - any ``JDK 21`` distro should work: Eclipse Temurin, OpenJDK, Oracle JDK..etc
- ``Docker engine``
    - the integration tests need a Docker engine to run `MySQL` in a container;
    - `Docker Desktop` is an easy way to get Docker on your OS;
        - https://docs.docker.com/desktop/
- If you run the application through an `IDE`, such as `IntelliJ IDEA`, you may need to enable
  annotation processing to support `Lombok` and `MapStruct`.
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

- The `Swagger UI` can be accessed via:
    - http://localhost:8080/swagger-ui.html
- The `OpenAPI` `yaml` can be downloaded via:
    - http://localhost:8080/v3/api-docs.yaml

### Manage Accounts

- **Register** - ````POST /accounts````
    - http://localhost:8080/swagger-ui/index.html#/account-controller/register
    - Create a new client's Account wit a default Payments Bank Account linked to it;
        - The Bank Account is created with a default balance of `0.00` and currency of client's
          country;
    - Only Netherlands and Belgium are supported at the moment;
    - Only legal age clients are allowed to register;
    - The username must be unique;
    - Upon successful registration, a default random secure password is generated and returned to
      the user;
    - No authentication is required to register;
- Login 
  - ```GET /login```
    - via browser its possible to access the login UI page: http://localhost:8080/login
    - this is the ``formLogin`` feature automatically provided by Spring Security;
    - upon successful login, the user receives their `JWT` that allows them to access protected
      resources;
  - ````POST /login````
    - http://localhost:8080/swagger-ui/index.html#/login-endpoint/post_login
    - Existing clients can login with their username and password;
    - A `JWT` is generated upon successfully authenticated client;
    - This endpoint is generated automatically by Spring Security and connects to a custom 
      success handler that generates the `JWT`;
    - This is the same endpoint used by the `formLogin` feature;
- Account Overview listing - ````GET /accounts````
    - http://localhost:8080/swagger-ui/index.html#/account-controller/getAccountOverview
    - List all the client's Bank Accounts;
    - This is a secured endpoint that requires a valid `JWT` for access;
    - If a valid `JWT` is provided, the client's banking details associated with the token are
      fetched and returned;