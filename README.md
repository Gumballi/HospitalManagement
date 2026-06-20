# Hospital Management System

## Overview

This is a desktop-based Hospital Management System developed in Java, designed to streamline various administrative and operational tasks within a hospital environment. The system provides functionalities for managing patient information, appointments, billing, doctor schedules, medicine inventory, and user authentication for different roles such as administrators, doctors, and receptionists.

## Features

- **User Authentication:** Secure login for different user roles (Admin, Doctor, Receptionist).
- **Patient Management:** Register new patients, view patient details, and manage patient directories.
- **Appointment Scheduling:** Schedule and manage appointments for patients with doctors.
- **Doctor Management:** Manage doctor profiles and their schedules.
- **Medicine Management:** Maintain medicine inventory and prescription details.
- **Billing System:** Generate and manage patient bills.
- **Role-Based Access Control:** Different functionalities accessible based on user roles.

## Technologies Used

- **Language:** Java 25
- **Build Tool:** Intellij Maven
- **Database:** Microsoft SQL Server ```You can use mysql server alternatively```
- **Database Driver:** `mssql-jdbc` (version 12.6.1.jre11)  ```mysql-jdbc altervatively(you have to change the code based on your preference DB Driver)```
- **User Interface:** Java Swing

## Setup Instructions

To set up and run this project locally, follow these steps:

### Prerequisites

- Java Development Kit (JDK) 25 or higher
- Apache/Intellij Maven
- Microsoft SQL Server instance

### Database Configuration

1.  **Create Database:** Create a database named `hospital_mgmt_db` in your Microsoft SQL Server instance.
2.  **Update Credentials:** The database connection details are currently hardcoded in `src/main/java/org/example/database/DatabaseConnection.java`. **For production environments, it is highly recommended to externalize these credentials (e.g., using environment variables or configuration files) for security reasons.**
    ```java
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=hospital_mgmt_db;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "Your_password_here"; // Update this with your SQL Server password
    ```
    Modify `USER` and `PASSWORD` to match your SQL Server credentials.

### Building and Running

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/gumballi/HospitalManagement.git
    cd HospitalManagement
    ```
2.  **Build the project using Maven:**
    ```bash
    mvn clean install
    ```
3.  **Run the application:**
    ```bash
    mvn exec:java -Dexec.mainClass="org.example.Main"
    ```
    Alternatively, you can run the `Main.java` file directly from your IDE.

## Usage

Upon running the application, a login screen will appear. You can log in with predefined user roles (e.g., Admin, Doctor, Receptionist) to access different parts of the system. (Specific default credentials would be provided here if known, otherwise, assume they need to be set up in the database).

## Contributing

Contributions are welcome! Please fork the repository and submit pull requests.

