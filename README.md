# Event Manager Application

This is the Event Manager Application, a Java-based web application designed for managing events and user accounts. It
provides functionality for creating and viewing event posts, user registration and authentication, and user management
for administrators.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)

## Features

- Create and manage event posts with details such as title, content, location, start date, and end date.
- User registration and authentication with role-based access control.
- User management for administrators to manage user roles.
- Secure password hashing using BCrypt.
- RESTful API for event posts.
- Exception handling for validation and error responses.

## Documentation

https://navjotsrakhra.github.io/EventManagementPlatform/

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 20 or later
- PostgreSQL database

## Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/NavjotSRakhra/EventManagementPlatform.git

2. Navigate to the project directory:
   ```sh
   cd EventManagementPlatform

3. Configure your database settings in `src/main/java/io/github/navjotsrakhra/eventmanager/config/DataSourceConfig`

4. Build the project using Maven:
   ```sh
   ./mvnw clean install

## Usage

1. Run the application.
   ```sh
   mvn spring-boot:run

2. Access the application at 'http://localhost:8080'
3. You can register as a new user or use the provided administrator account to login

## License

Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
