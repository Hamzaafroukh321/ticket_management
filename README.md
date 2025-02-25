# Ticket Management

Ticket Management is a project that consists of a backend REST API built with Spring Boot, an Oracle XE database, and a Swing-based desktop client. The backend and Oracle DB are containerized using Docker Compose, while the Swing client is packaged as an executable JAR file that communicates with the backend via REST.

## Getting Started

### Prerequisites

- [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/)
- [JDK 17](https://adoptium.net/) (for building and running the client)
- [Maven](https://maven.apache.org/) for building the projects

### Clone the Repository

Open a terminal and run:

    git clone https://github.com/Hamzaafroukh321/ticket_management.git
    cd ticket_management

## Backend Setup (Docker)

The backend and the Oracle database are dockerized.

### Running the Backend

From the root directory, run:

    docker-compose up --build

This command builds your backend image, starts the Oracle XE container, and runs your Spring Boot application. Your backend will be available at http://localhost:8080

## Client Setup (Swing)

The Swing client is a desktop application that communicates with the backend via REST.

### Build the Client

Navigate to the client module:

    cd client

Build the client using Maven:

    mvn clean package

An executable JAR (e.g., `swing-client-1.0.jar`) will be generated in the `client/target/` directory.

### Run the Client

Run the JAR file:

    java -jar target/swing-client-1.0.jar

The client is configured to communicate with the backend at `http://localhost:8080/api`. Ensure the backend is running before launching the client.

## Running Tests

Backend tests (unit and integration) are located under `src/test/java`. To run the tests, execute:

    mvn test


