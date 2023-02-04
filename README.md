# Melita OrderManagement

## Team information

| Name           | Role                            | Email                                                             |
|----------------|---------------------------------|-------------------------------------------------------------------|
| Ruslan Sorokin | System Architect, Key Developer | [sorokus.dev@gmail.com](mailto:sorokus.dev@gmail.com)     |

## Application information
TODO

### SLA
TODO

## Application setup and run

### Prerequisites
* Java SDK 17+
  - https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
* Docker and Docker Compose
  - https://www.docker.com/products/docker-desktop/
  - https://docs.docker.com/compose/install/
* Apache Maven
  - https://maven.apache.org/download.cgi

### Run Middleware
RabbitMQ and PostgreSQL are served via Docker. They are started together via Docker Compose.

To start them go to application directory, i.e. `OrderManagement` and start Docker Compose.
(Make sure Docker Desktop was started prior.)
```bash
cd ./OrderManagement
```

```bash
docker-compose up -d
```

### Tweak application config
The main application config is located at `/OrderManagement/src/main/resources/application.properties`

It has the following properties:


| Property name                | Meaning                                               | Can be customised                                                                  |
|------------------------------|-------------------------------------------------------|------------------------------------------------------------------------------------|
| app.security.client.username | Login for a user able to place orders                 | Yes                                                                                |
| app.security.client.password | Password of the user able to place orders             | Yes                                                                                |
| app.security.agent.username  | Login of an agent able to approve orders              | Yes                                                                                |
| app.security.agent.password  | Password of the agent able to approve orders          | Yes                                                                                |
| app.agent.email              | Email where agent will receive mails about new orders | Yes                                                                                |
| spring.datasource.url        | DB URL                                                | No, by default Yes, if changed docker compose file Yes, if switched to external DB |
| spring.datasource.username   | DB Username                                           | No, by default Yes, if changed docker compose file Yes, if switched to external DB |
| spring.datasource.password   | DB password                                           | No, by default Yes, if changed docker compose file Yes, if switched to external DB |
| spring.mail.*                | Set of SMTP settings                                  | Yes                                                                                |
| amqp.queue.name              | RabbitMQ queue name for order processing              | Yes                                                                                |
| amqp.routing.key             | RabbitMQ routing key to order queue                   | Yes                                                                                |
| amqp.exchange.name           | RabbitMQ exchange name                                | Yes                                                                                |
| springdoc.packagesToScan     | Packages to scan for Swagger/OpenAPI spec fulfillment | No                                                                                 |
| springdoc.pathsToMatch       | Paths to match for Swagger/OpenAPI spec fulfillment   | No                                                                                 |

## Application Start
Application should be built with Maven as
`mvn clean install`

And started preferably with Maven as `mvn spring-boot:run`
or through favourite IDE.

## Application Security
The application is designed to support two roles `USER` and `AGENT` that are authorized to call different sets of API.

The application has two pre-configured users: `user` and `agent` (see `application.properties` storing username/password for each).

## Application API (Swagger)

Once the application is started, API discovery becomes available via Swagger http://localhost:8080/swagger-ui/index.html

## Application Testing

### Automated Testing
Automated Testing is performed with unit tests during application build.

It can be run explicitly with Maven as
`mvn clean test` or `mvn clean install`

### Manual or Interactive Testing
Manual testing can be done either via Swagger, Postman or Curl.

Collection of postman scripts is located in ...

## Demo
Please refer to Demo (link!) if struggling with application setup/run/testing.

## Troubleshooting
TODO
### Logs
TODO
## More Information
TODO