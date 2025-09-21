# Room-microservices
## Event-Driven Microservices Project (2025)

This is a **microservices project** built on **Spring Boot** with an **event-driven architecture**.

## Overview

- **Broker:** Apache Kafka
- **Database:** PostgreSQL (each service uses its own schema if required)
- **Object Storage:** MinIO
- **Logging/Monitoring:** ELK Stack (Elasticsearch, Logstash, Kibana)

Project scheme: [project_scheme.drawio](project_scheme.drawio)

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

- **Centralized logging:**  
  The project uses an **ELK stack** for log collection and visualization:
    - Logstash collects logs and events from services via pipelines.
    - Elasticsearch stores and indexes logs and events in different indexes.
    - Kibana provides a UI to explore logs.

## External Dependencies

The project relies on the following **external containers** started via Docker Compose:

- **PostgreSQL** (with volume persistence)
- **Kafka**
- **MinIO**
- **ELK Stack** (optional for logging)

These containers provide the necessary infrastructure for development and testing.

## Getting Started

1. Ensure Docker and Docker Compose are installed.
2. Go to the project directory
3. Build services jar files with project maven wrapper
   ```bash
   ./mvnw clean package
