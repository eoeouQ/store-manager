# Store Manager

___
***Microservice application for keeping records of the number of products in stores, registration of products in user orders***

## Tech Stack

* ***Gradle***
* ***Docker***
* ***Keycloak***
* ***PostgreSQL***

# Microservices

___
## Discovery Service

***Service that combines other services in one environment***

### Tech Stack

* ***Spring Cloud Eureka Server***
* ***Spring Cloud Config Client***

---
## Config Service

***Service that stores the configuration of other services***

### Tech Stack

* ***Spring Cloud Config Server***

---
## DB Migration Service

***Service that performs database migration***

### Tech Stack

* ***Spring Data JPA***
* ***PostgreSQL + Flyway***

---
## Gateway Service

***Service that provides a single access point to the application and its services, using OAuth 2 for traffic protection***
* ***<ins>/product</ins> -> Product Service***
* ***<ins>/inventory</ins> -> Inventory Service***
* ***<ins>/order</ins> -> Order Service***

### Tech Stack

* ***Spring OAuth 2 Resource Server***
* ***Spring Cloud Gateway***
* ***Spring Cloud Eureka Client***
* ***Spring Cloud Config Client***

---
## Product Service

***Service that provides a CRUD API for products***

### Tech Stack

* ***Java 17 + Gradle***
* ***JUnit + Mockito***
* ***Spring Boot + Data JPA + WebFlux***
* ***Spring Cloud Eureka Client***
* ***Spring Cloud Config Client***
* ***PostgreSQL + Hibernate***
* ***Lombok***
* ***Open API***

---
## Inventory Service

***Service that provides API for amount change of products in stores***

### Tech Stack

* ***Java 17 + Gradle***
* ***JUnit + Mockito***
* ***Spring Boot + Data JPA + WebFlux***
* ***Spring Cloud Eureka Client***
* ***Spring Cloud Config Client***
* ***PostgreSQL + Hibernate***
* ***Lombok***
* ***Kafka***
* ***Open API***

---
## Order Service

***Service that provides API for order placement, decline, its status change and order history view***

### Tech Stack

* ***Java 17 + Gradle***
* ***JUnit + Mockito***
* ***Spring Boot + Data JPA + WebFlux***
* ***Spring Cloud Eureka Client***
* ***Spring Cloud Config Client***
* ***PostgreSQL + Hibernate + Hypersistence***
* ***Lombok***
* ***Kafka***
* ***Open API***

---

## Installation

___
***To run the application,
you need to [download](https://github.com/iZouiR/store-manager/releases) it,
specify environmental variables which lie in docker/.env file,
through the terminal move to docker directory and run the following script:***

```
docker-compose up --build
```

***Then go to <ins>{app-hostname}:8080</ins> and create store-manager-realm with gateway-client inside it, copy its credentials for further use in authentication***

***Documentation on how to use API
available by <ins>{app-hostname}:8765/{service-name}/v3/api-docs</ins> mappings***
