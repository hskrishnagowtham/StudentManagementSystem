# Student Management System



A Java Spring Boot based Student Management System with REST APIs and MySQL.



## About the Project



This project is developed using Java and Spring Boot to manage student information.



The application provides REST APIs for student management operations and uses MySQL as the database.



## Technologies Used



- Java

- Spring Boot

- Spring Data JPA

- Hibernate

- MySQL

- REST APIs

- Maven

- Spring Security

- JWT

- Git

- GitHub



## Features



- Add student

- View all students

- View student by roll number

- Update student details

- Delete student

- Search students by name

- User registration

- User login

- JWT-based authentication

- Input validation

- MySQL database integration



## API Endpoints



### Authentication



POST /auth/register



POST /auth/login



### Students



POST /students



GET /students



GET /students/{rollNumber}



GET /students/search?name={name}



PUT /students/{rollNumber}



DELETE /students/{rollNumber}



## Database



The application uses MySQL.



The local database configuration is kept in `application.properties` and is not committed to GitHub.



## How to Run



1. Clone the repository.



2. Open the project in IntelliJ IDEA.



3. Configure your local MySQL database.



4. Configure `application.properties` with your local database details.



5. Run the Spring Boot application.



The backend runs on:



http://localhost:8080



## Author



**Krishna Gowtham**



B.Tech Computer Science Engineering Student



GitHub:



https://github.com/hskrishnagowtham

