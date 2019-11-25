# Online_Shop_Backend
Online shop back end demo application, based on Spring framework, Hibernate ORM, JPA and REST API. 
This is my personal java beck end practice project, that simulates online shop backend. It contains the basic functionality,
that almost every online shop has.

# Tech stack
The project is Spring Boot project. It uses Hibernate to create and manage the database, JUnit and Mockito for the tests, Maven for the
dependencies, REST API, MySQL database and of course Java.

# Project Structure

The project the following main layers : controller, service, repository, model, which separates on entity and DTO.
The controller layer, contains the REST API endpoints. Then comes the service layer, that implements the main business logic
and functionality. The service layer calls the repositories to "communicate" with the database. The model layer separates on entities and
DTO's. The entities represent the Database tables, as Java objects, using hibernate to create and manage the DB, based on those objects.
The DTO's ( Data Transfer Objects ) are used to receive and respond the optimal amount of data, that is requested from the front end.
