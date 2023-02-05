# Melita OrderManagement

## Team information

| Name           | Role                            | Email                                                             |
|----------------|---------------------------------|-------------------------------------------------------------------|
| Ruslan Sorokin | System Architect, Key Developer | [sorokus.dev@gmail.com](mailto:sorokus.dev@gmail.com)     |

## Application information
The solution is represented as two applications:  *Melita Cloud Config Server* and *Melita OrderManagement application*.

They run separately on two application servers.

*Melita OrderManagement application* interacts with  *Melita Cloud Config Server*, *RabbitMQ* middleware, and *PostgresSQL* DB.

### Frameworks and Libraries utilized

| Name                | Purpose / Role                                          | Notes                                                  |
|---------------------|---------------------------------------------------------|--------------------------------------------------------|
| Spring Boot         | Core functionality of the app                           |                                                        |
| Spring Security     | Authentication / authorization                          | Separate 'user' and 'agent' roles and users            |
| Spring Data JPA     | Persistence layer                                       | Default Hibernate implementation is used               |
| Flyway              | Versioned maintenance of DB schema changes              |               |
| Spring Cloud Config | Separate maintenance of config data on dedicated server |                                                        |
| Spring Mail         | Email management                                        |                                                        |
| Themeleaf           | Render HTML content for emails based on templates       |                                                        |
| Jakarta Validation  | Implement field validation for REST API                 |                                                        |
| Lombok              | Dynamic code generation utility                         | Lets minimize code and get rid of setters/getters/etc. |
| ModelMapper         | Simplifies mapping between objects                      | Effectively convert VO<->Entity                        |
| Spring AMQP         | Interactions with RabbitMQ via AMQP                     |                                                        |
| Springdoc           | Generation of OpenAPI specs, Swagger support            | http://localhost:8080/swagger-ui/index.html                                                   |
| postgresql          | Connectivity with PostgreSQL                            |                                                        |

## Application setup and run

### Pre-requisites
* Java SDK 17+
  - https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
* Docker and Docker Compose
  - https://www.docker.com/products/docker-desktop/
  - https://docs.docker.com/compose/install/
* Apache Maven
  - https://maven.apache.org/download.cgi

### Checkout Melita OrderManagement application
Checkout *Melita OrderManagement* from https://github.com/sorokus/melita-ordermanagement repo into any directory.
```bash
git clone https://github.com/sorokus/melita-ordermanagement.git
```

### Run Middleware
RabbitMQ and PostgreSQL are served via Docker. They are started together via Docker Compose.

To start them go to application directory, i.e. `melita-ordermanagement`, and start Docker Compose.
(Make sure Docker Desktop was started prior.)
```bash
cd ./melita-ordermanagement
```
```bash
docker-compose up -d
```


### Melita OrderManagement application config
The main application config is located in https://github.com/sorokus/melita-config-repo repo and served with *Melita Cloud Config Server*.


It has the following properties which can be tweaked:

| Property name                | Meaning                                                                                               | Can be customised                                                                               |
|------------------------------|-------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| app.security.client.username | Login for a user able to place orders                                                                 | Yes                                                                                             |
| app.security.client.password | Password of the user able to place orders                                                             | Yes                                                                                             |
| app.security.agent.username  | Login of an agent able to approve orders                                                              | Yes                                                                                             |
| app.security.agent.password  | Password of the agent able to approve orders                                                          | Yes                                                                                             |
| app.agent.email              | Email where agent will receive mails about new orders                                                 | Yes                                                                                             |
| spring.datasource.url        | DB URL                                                                                                | No, by default<br/> Yes, if docker compose file is tweaked<br/> Yes, if switched to external DB    |
| spring.datasource.username   | DB Username                                                                                           | No, by default<br/> Yes, if docker compose file is tweaked<br/> Yes, if switched to external DB |
| spring.datasource.password   | DB password                                                                                           | No, by default<br/> Yes, if docker compose file is tweaked<br/> Yes, if switched to external DB    |
| spring.mail.*                | Set of SMTP settings                                                                                  | Yes                                                                                             |
| spring.mail.password         | Email password generated by google for app. Note: it's not a regular password for user email account. | Yes                                                                                             |
| amqp.queue.name              | RabbitMQ queue name for order processing                                                              | Yes                                                                                             |
| amqp.routing.key             | RabbitMQ routing key to order queue                                                                   | Yes                                                                                             |
| amqp.exchange.name           | RabbitMQ exchange name                                                                                | Yes                                                                                             |
| springdoc.packagesToScan     | Packages to scan for Swagger/OpenAPI spec fulfillment                                                 | No                                                                                              |
| springdoc.pathsToMatch       | Paths to match for Swagger/OpenAPI spec fulfillment                                                   | No                                                                                              |


### Build and Run Melita Cloud Config Server
Checkout *Melita Cloud Config Server* from https://github.com/sorokus/melita-configserver into directory other than `melita-ordermanagement`.
```bash
git clone https://github.com/sorokus/melita-configserver.git
```
```bash
cd ./melita-configserver
```
```bash
mvn clean install
```
```bash
mvn spring-boot:run
```
From now on, configurations may be obtained from http://localhost:8888.
Refer to https://github.com/sorokus/melita-configserver/blob/main/README.md for more details.

## Melita OrderManagement application Build and Start
Application should be built with Maven as
```bash
mvn clean install
```

And started with Maven as
```bash
mvn spring-boot:run
```
or through favourite IDE.

## Melita OrderManagement Application Security
The application is designed to support two roles `USER` and `AGENT` that are authorized to call different sets of API.

The application has two pre-configured users: `user` and `agent` (see `ordermanagement.properties` storing username/password for each).
(Or `user_dev` and `agent_dev` (see `ordermanagement-dev.properties`) respectively.)

## Application API (Swagger)

Once the application is started, API discovery becomes available via Swagger http://localhost:8080/swagger-ui/index.html

## Application Testing

### Automated Testing
Automated Testing is performed with unit tests during application build.

It can be run explicitly with Maven as
`mvn clean test` or `mvn clean install`

### Manual or Interactive Testing
Manual testing can be done either via Swagger, Postman or Curl.

Collection of postman scripts is located in [Melita OrderManagement.postman_collection.json](Test%2FMelita%20OrderManagement.postman_collection.json).

## Demo
Please refer to Demo [Google drive](https://drive.google.com/file/d/1kT4iE3dnREe0J6ygNW-qO30x69nmnLB4/view?usp=sharing) if struggling with application setup/run/testing or just want to take a look at application functionality without actual local setup.
It's recommended to watch it at x1.5-x2 speed.

## Useful links
### Application repos
- https://github.com/sorokus/melita-config-repo
- https://github.com/sorokus/melita-configserver
- https://github.com/sorokus/melita-ordermanagement
### Required software
- https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
- https://www.docker.com/products/docker-desktop/
- https://docs.docker.com/compose/install/
- https://maven.apache.org/download.cgi


## More Information
More information can be provided by contacting the author @ [sorokus.dev@gmail.com](mailto:sorokus.dev@gmail.com)