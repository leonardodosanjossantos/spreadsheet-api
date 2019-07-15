# Spreadsheet API
API Project for expose CRUD implementations and spreadsheet products in a web implementation.

## How it Works 
Presents a interface with a "upload" button to send a spreasheet test for upload in a AWS S3 configured bucket and send a message in rabbitmq.

## Configuration
* Firstval, we need to install a commons jar (available [here](https://github.com/leonardodosanjossantos/spreadsheet-commons))
* Java 8 or above
* Maven
* RabbitMq for messages
* Mysql Client

## How to Run
```bash
mvn spring-boot:run
```

## Main Frameworks

* Spring Boot
* Lombook

## Test Frameworks 

* Junit
* Mockito
* Matcher
