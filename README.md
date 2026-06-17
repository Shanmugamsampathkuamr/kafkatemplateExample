# Kafka Microservices Demo

A Spring Boot microservices project demonstrating **Apache Kafka** integration with KRaft mode (no ZooKeeper).

## Architecture

```
POST /orders (JSON)
  --> Order-service (port 8082)
    --> Kafka topic "order-events" (3 partitions, 3 replicas)
      --> notification-service (port 8083)
        --> Simulated email notification
```

## Services

| Service | Port | Role |
|---------|------|------|
| **order-service** | 8082 | REST API producer — accepts orders and publishes to Kafka |
| **notification-service** | 8083 | Kafka consumer — listens for orders and logs notifications |
| **Kafka cluster** (3 brokers) | 9092, 9094, 9096 | Message broker (KRaft mode) |
| **Kafka UI** | 8081 | Web dashboard for Kafka management |

## Prerequisites

- Java 17+
- Docker & Docker Compose
- Maven (or use `mvnw` wrapper)

## Getting Started

### 1. Start Kafka cluster

```bash
docker-compose up -d
```

### 2. Build & run services

```bash
# Order service
cd Order-service
./mvnw spring-boot:run

# Notification service (new terminal)
cd notification-service
./mvnw spring-boot:run
```

### 3. Place an order

```bash
curl -X POST http://localhost:8082/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "John Doe",
    "customerEmail": "john@example.com",
    "amount": 250.75
  }'
```

### 4. View in Kafka UI

Open [http://localhost:8081](http://localhost:8081) to browse topics, messages, and consumer groups.

## Project Structure

```
├── docker-compose.yml              # 3-node Kafka cluster + UI
├── Order-service/
│   ├── src/main/java/com/kafka/
│   │   ├── OrderServiceApplication.java
│   │   ├── config/KafkaTopicConfig.java
│   │   ├── controller/OrderController.java
│   │   ├── model/OrderEvent.java
│   │   └── producer/OrderProducer.java
│   └── src/main/resources/application.yml
└── notification-service/
    ├── src/main/java/com/kafka/
    │   ├── NotificationServiceApplication.java
    │   ├── consumer/NotificationConsumer.java
    │   └── model/OrderEvent.java
    └── src/main/resources/application.yml
```
