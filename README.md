# Employee-Management-System
A Java JDBC-based application for managing employee records in MySQL database.
# Features
- Add new employees
- View all employees
- Update employee details
- Delete employees
- MySQL database connectivity
# Technologies Used
- Java
- JDBC (Java Database Connectivity)
- MySQL Database
- MySQL Connector/J Driver

 # Setup Instructions

 # Prerequisites
1. Java JDK 9 or higher
2. MySQL Server
3. MySQL Connector/J driver

 # Database Setup
```sql
CREATE DATABASE employeedb;

CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    department VARCHAR(50),
    salary DECIMAL(10, 2),
    hire_date DATE
);

Running the Application
# Compile
javac -cp ".;mysql-connector-j-9.0.x.jar" EmployeeDBApp.java

# Run
java -cp ".;mysql-connector-j-9.0.x.jar" EmployeeDBApp

Configuration
Update database credentials in EmployeeDBApp.java:
private static final String URL = "jdbc:mysql://localhost:3306/employeedb";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";



