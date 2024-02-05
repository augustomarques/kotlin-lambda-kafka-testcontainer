# AWS Serverless Kafka Producer Example
This project demonstrates the creation of an AWS Serverless function that receives HTTP requests and publishes the incoming messages to a specified topic in Apache Kafka. The project is implemented in Kotlin, leveraging the Micronaut framework for the Serverless function setup and Kafka communication configuration. Gradle is used for dependency management and project builds. Furthermore, the test suite employs Kotest and Testcontainers to ensure proper integration with Kafka.

## Features
- Reception of HTTP requests via AWS API Gateway.
- Publication of messages to a Kafka topic.
- Integration testing with Kafka using Testcontainers.

## Technologies Used
- Kotlin: Programming language.
- Micronaut: Framework for developing Serverless applications and microservices.
- Gradle (with Kotlin DSL): Build automation and dependency management tool.
- AWS Lambda and API Gateway: AWS services for creating Serverless functions and managing APIs.
- Apache Kafka: Messaging system for real-time stream processing.
- Kotest: Testing framework for Kotlin.
- Testcontainers: Library for supporting tests with Docker containers.
