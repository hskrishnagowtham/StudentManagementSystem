# Student Management System

A Java Full Stack web application built using Spring Boot, React, and MySQL to manage student records, academic performance, and AI-powered learning assistance.

## Live Demo

- **Frontend:** https://student-management-frontend-5nti.onrender.com/
- **Backend:** https://studentmanagementsystem-0xvp.onrender.com/

## About the Project

The Student Management System is designed to simplify student information management and provide academic support through Artificial Intelligence.

The application supports separate Admin and Student roles, secure authentication, student record management, academic performance analysis, and AI-powered learning features.

## Technologies Used

### Backend
- Java 24
- Spring Boot
- Spring Data JPA
- Hibernate
- Spring Security
- JWT Authentication
- Maven
- REST APIs

### Frontend
- React
- JavaScript
- HTML
- CSS
- React Router

### Database
- MySQL
- Aiven Cloud

### AI Integration
- Generative AI
- Large Language Models (LLMs)
- Groq API
- Spring AI / OpenAI-compatible API integration

### Tools
- IntelliJ IDEA
- Postman
- Git
- GitHub
- Render

## Features

### Authentication and Authorization
- User registration and login
- JWT-based authentication
- Separate Admin and Student roles
- Role-based access control

### Student Management
- Add student records
- View all students
- View student by roll number
- Update student details
- Delete student records
- Search students by name
- Link student login accounts with student records
- View individual student profiles

### AI-Powered Features
- **AI Student Assistant:** Provides academic assistance.
- **Performance Analysis:** Analyzes student academic performance.
- **Question Generator:** Generates educational questions.
- **Learning Recommendations:** Provides learning suggestions.

### Academic Management
- Manage student marks
- Calculate percentages
- Assign grades
- View academic performance

## API Endpoints

### Authentication

| Method | Endpoint |
|---|---|
| POST | `/auth/register` |
| POST | `/auth/login` |

### Students

| Method | Endpoint |
|---|---|
| POST | `/students` |
| GET | `/students` |
| GET | `/students/{rollNumber}` |
| GET | `/students/search?name={name}` |
| PUT | `/students/{rollNumber}` |
| DELETE | `/students/{rollNumber}` |
| GET | `/students/my-profile` |
| PUT | `/students/{rollNumber}/link-account?username={username}` |

## Database

The application uses MySQL for storing student and user information.

- Local development uses a locally configured MySQL database.
- Production uses an Aiven MySQL database.
- Database credentials and API keys should be configured through environment variables and must not be committed to GitHub.

## Deployment

- **Frontend:** Render
- **Backend:** Render
- **Database:** Aiven MySQL

## How to Run Locally

1. Clone the backend repository.
2. Open the project in IntelliJ IDEA.
3. Configure your local MySQL database.
4. Update `application.properties` with your local database configuration.
5. Configure the required environment variables, including your Groq API key and JWT secret.
6. Run the Spring Boot application.

The backend runs at:

`http://localhost:10000`

For the frontend, clone the frontend repository, install its dependencies using `npm install`, configure the backend API URL, and start the React development server.

## GitHub Repositories

- **Backend:** https://github.com/hskrishnagowtham/StudentManagementSystem
- **Frontend:** https://github.com/hskrishnagowtham/student-management-frontend

## Author

**Krishna Gowtham**

B.Tech – Computer Science and Engineering

Interested in Java Full Stack Development and Artificial Intelligence.