# Event-Driven Microservices Project (2025)

This is a **microservices project** built on **Spring Boot** with an **event-driven architecture**.

## Overview

- **Broker:** Apache Kafka
- **Database:** PostgreSQL (each service uses its own schema if required)
- **Current limitation:** Schemas need to be created manually.
    - **TODO:** Automate schema creation/migrations for each service.

The application provides functionality for **room and booking inventory**.

## Services

### RoomService
- Manages hotel room data.
- Provides **CRUD operations** for rooms.
- Publishes events when a room is created, updated, or deleted.

### FileService
- Handles storage of files (e.g., room photos).
- Listens to relevant events (for example, deletes images associated with a room when a `RoomDeletedEvent` is received).

## Architecture Highlights

- **Event-driven communication:**  
  Services communicate by publishing and subscribing to **Kafka topics**.
    - Example: `RoomService` publishes a `RoomDeletedEvent`.
    - `FileService` consumes this event and deletes associated images.

- **Database isolation:**  
  Each service has its own **PostgreSQL schema** to avoid conflicts and ensure data encapsulation.

- **Extensible design:**  
  The event model allows new services to easily subscribe to existing events or publish new ones.

## External Dependencies

- There is a **Docker Compose** configuration included that launches external services required for development and testing:
    - **PostgreSQL** (with volume persistence)
    - **Kafka** 
- This allows you to run all necessary infrastructure locally without manual installation.

## TODO / Next Steps

- Automate creation and migration of database schemas.
- Implement centralized configuration for Kafka producers and consumers.
- Add error handling and retry mechanisms for event processing.

## Getting Started

1. Ensure Docker and Docker Compose are installed.
2. Run the external services using the provided Docker Compose file:
   ```bash
   docker-compose up -d
 