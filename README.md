# 🐱 Cat Microservice

## 📌 Overview
The **Cat Microservice** is a Java-based backend service designed to handle various cat-related operations. It integrates with Kafka for messaging, S3 for storage, and SQS for asynchronous processing. This microservice follows a modular architecture, making it scalable and maintainable.

## 🚀 Features
- **Caching**: Implements caching for optimized performance.
- **Kafka Integration**: Supports real-time event streaming.
- **S3 Integration**: Manages file storage in AWS S3.
- **SQS Messaging**: Handles queue-based message processing.
- **Rate Limiting**: Prevents excessive API requests.
- **Controllers**: Manages cat-related API endpoints.
- **Cat Operations**:
  - **Cat Registration**: Allows users to register a new cat with details like name, breed, and age.
  - **Cat Archive**: Provides historical tracking of cats' records and activities.
  - **Cat Toy Management**: Handles information on different cat toys and their availability.
  - **Health Monitoring**: Tracks health data and vaccination records of registered cats.
  - **Adoption Services**: Facilitates cat adoption by listing available cats and processing adoption requests.

## 🏗️ Project Structure
```
src/main/java/com/lesleyzh/cat_demo
│── cache
│   ├── CatCacheManager
│   ├── DemoCacheManager
│── configuration
│   ├── KafkaConfig
│   ├── OpenApiConfig
│   ├── RedissonClientConfiguration
│   ├── S3ClientConfiguration
│   ├── SqsClientConfiguration
│   ├── SqsMessageProcessorConfiguration
│── controller
│   ├── CatArchiveServiceController
│   ├── CatServiceController
│   ├── CatToyController
│── kafka
│   ├── observers
│   │   ├── KafkaConsumer
│   ├── ProducerService
│   ├── StreamProducerService
│── sqs
│   ├── MessageProcessor
│   ├── MessageProcessorImpl
│   ├── QueueConsumer
│   ├── QueueProducer
│   ├── SqsConsumer
│   ├── SqsProducer
│── model
│── ratelimiter
│── repository
│── s3
│── service
│── CatDemoApplication
```

## 🔧 Technologies Used
- **Java (Spring Boot)** – Core backend framework.
- **Kafka** – Event-driven architecture support.
- **AWS S3** – Object storage for file management.
- **AWS SQS** – Message queue service for async processing.
- **Redis** – Caching layer.
- **Docker** – Containerization support.

## 🛠️ Setup Instructions
### Prerequisites
- Java 11+
- Maven
- Docker (for containerized deployment)

### Steps to Run Locally
1. **Clone the repository**
   ```sh
   git clone https://github.com/lesleyzhao/AWS_Cat_Microservice.git
   cd  AWS_Cat_Microservice
   ```

2. **Run using Docker Compose**
   ```sh
   docker-compose up -d
   ```


## 📡 API Endpoints
| HTTP Method | Endpoint | Description |
|------------|---------|-------------|
| GET | `/api/cats` | Get all cats |
| POST | `/api/cats` | Add a new cat |
| GET | `/api/cats/{id}` | Get cat details |
| DELETE | `/api/cats/{id}` | Delete a cat |
| GET | `/api/cats/toys` | Get available cat toys |
| POST | `/api/cats/adoption` | Submit a cat adoption request |

## 📬 Kafka Topics
- **`cat-events`** – Used for cat-related notifications.

## 📥 SQS Queues
- **`cat-queue`** – Handles asynchronous processing of cat records.

## 📖 Notes
- Ensure AWS credentials are configured properly for S3 and SQS.
- Kafka should be running before triggering events.

## 🤝 Contributing
Contributions are welcome! Please create an issue or submit a pull request.

## 📜 License
This project is licensed under the MIT License.

---
Maintained by **Lesley ZH & Team** 🐾

