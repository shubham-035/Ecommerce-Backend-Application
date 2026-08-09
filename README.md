# 🛒 Cartly - Cloud-Native E-Commerce Platform

Cartly is a modern, scalable, and cloud-native **E-Commerce Platform** developed using **Java, Spring Boot, Spring Security, React.js, and Microservices Architecture**.

The application is designed to provide a complete online shopping experience where users can register, authenticate, browse products, manage their cart, place orders, make secure payments, and receive notifications.

The backend is divided into multiple independent microservices such as **Authentication, Product, Order, Inventory, Payment, and Notification services**. These services communicate through REST APIs and asynchronous event-driven communication using **Apache Kafka**.

The application uses **Spring Cloud Gateway** as the centralized API Gateway and **Netflix Eureka** for service discovery. **Redis** is used for caching frequently accessed data, while **MySQL** is used for persistent data storage.

The application is containerized using **Docker** and can be deployed on **AWS** infrastructure using services such as EC2, RDS, S3, IAM, and ELB. **Jenkins** is used to implement an automated CI/CD pipeline.

---

# 📌 Table of Contents

- [Project Overview](#-project-overview)
- [Project Objectives](#-project-objectives)
- [Key Features](#-key-features)
- [System Architecture](#-system-architecture)
- [Microservices](#-microservices)
- [Authentication and Security](#-authentication-and-security)
- [JWT Authentication Flow](#-jwt-authentication-flow)
- [Role-Based Access Control](#-role-based-access-control)
- [Product Service](#-product-service)
- [Order Service](#-order-service)
- [Inventory Service](#-inventory-service)
- [Payment Service](#-payment-service)
- [Notification Service](#-notification-service)
- [Apache Kafka](#-apache-kafka)
- [Saga Pattern](#-saga-pattern)
- [Redis Caching](#-redis-caching)
- [API Gateway](#-api-gateway)
- [Eureka Service Discovery](#-eureka-service-discovery)
- [Database](#-database)
- [Frontend](#-frontend)
- [REST APIs](#-rest-apis)
- [Docker](#-docker)
- [AWS Deployment](#-aws-deployment)
- [Jenkins CI/CD](#-jenkins-cicd)
- [Testing](#-testing)
- [Project Structure](#-project-structure)
- [Technology Stack](#-technology-stack)
- [Prerequisites](#-prerequisites)
- [Configuration](#-configuration)
- [Installation](#-installation)
- [Running the Application](#-running-the-application)
- [Environment Variables](#-environment-variables)
- [Example Workflow](#-example-e-commerce-workflow)
- [Performance Improvements](#-performance-improvements)
- [Security Practices](#-security-practices)
- [Future Enhancements](#-future-enhancements)
- [Project Highlights](#-project-highlights)
- [Developer](#-developer)

---

# 📖 Project Overview

Cartly is a full-stack e-commerce application developed using a distributed microservices architecture.

The main goal of the project is to build a scalable e-commerce system where individual business functionalities are separated into independent services.

Instead of creating one large monolithic application, Cartly divides the application into multiple microservices.

Each service is responsible for a specific business functionality.

For example:

- Authentication Service handles users and authentication.
- Product Service manages product information.
- Order Service handles customer orders.
- Inventory Service manages stock.
- Payment Service handles payments.
- Notification Service manages notifications.

This architecture allows each service to be developed, tested, deployed, and scaled independently.

---

# 🎯 Project Objectives

The major objectives of the Cartly project are:

1. Build a scalable e-commerce application.
2. Implement Microservices Architecture.
3. Secure REST APIs using Spring Security and JWT.
4. Implement Role-Based Access Control.
5. Implement service-to-service communication.
6. Implement asynchronous communication using Apache Kafka.
7. Implement distributed transaction management using the Saga Pattern.
8. Improve application performance using Redis caching.
9. Implement centralized request routing using API Gateway.
10. Implement service discovery using Eureka.
11. Integrate Stripe for payment processing.
12. Containerize applications using Docker.
13. Deploy services on AWS.
14. Implement automated CI/CD using Jenkins.
15. Write unit tests using JUnit and Mockito.

---

# 🚀 Key Features

## 👤 User Management

- User registration
- User login
- Password encryption
- JWT authentication
- User profile management
- Role-based authorization
- Secure API access

---

## 🔐 Authentication

- Spring Security
- JWT-based authentication
- BCrypt password encryption
- Token validation
- Role-based authorization
- Protected REST APIs

---

## 📦 Product Management

- Add products
- Update products
- Delete products
- View product details
- Search products
- Product categorization
- Product availability

---

## 🛒 Order Management

- Create orders
- View orders
- Update order status
- Cancel orders
- Order history
- Inventory validation
- Payment integration

---

## 📊 Inventory Management

- Stock management
- Stock availability checking
- Reserve inventory
- Update inventory
- Release inventory
- Inventory event processing

---

## 💳 Payment

- Stripe payment integration
- Payment creation
- Payment status tracking
- Payment success handling
- Payment failure handling

---

## 🔔 Notifications

- Order notifications
- Payment notifications
- Inventory notifications
- Event-based notification processing

---

## ⚡ Performance

- Redis caching
- Database optimization
- API Gateway
- Load balancing
- Asynchronous communication

---

# 🏗️ System Architecture

Cartly follows a **Microservices Architecture**.

```text
                         ┌──────────────────────┐
                         │     React Frontend   │
                         └───────────┬──────────┘
                                     │
                                     ▼
                         ┌──────────────────────┐
                         │     API Gateway      │
                         │  Spring Cloud        │
                         └───────────┬──────────┘
                                     │
              ┌──────────────────────┼──────────────────────┐
              │                      │                      │
              ▼                      ▼                      ▼
      ┌───────────────┐      ┌───────────────┐      ┌───────────────┐
      │ Auth Service  │      │Product Service│      │ Order Service │
      └───────────────┘      └───────────────┘      └───────┬───────┘
                                                             │
                                                             ▼
                                                    ┌─────────────────┐
                                                    │Inventory Service │
                                                    └────────┬────────┘
                                                             │
                                                             ▼
                                                    ┌─────────────────┐
                                                    │ Apache Kafka     │
                                                    └────────┬────────┘
                                                             │
                                      ┌──────────────────────┼──────────────────────┐
                                      │                      │                      │
                                      ▼                      ▼                      ▼
                              ┌──────────────┐       ┌───────────────┐      ┌──────────────┐
                              │Payment       │       │Notification   │      │Other Services│
                              │Service       │       │Service        │      │              │
                              └──────────────┘       └───────────────┘      └──────────────┘

                         ┌──────────────────────┐
                         │       Eureka         │
                         │  Service Discovery   │
                         └──────────────────────┘

                         ┌──────────────────────┐
                         │        Redis         │
                         │       Caching        │
                         └──────────────────────┘

                         ┌──────────────────────┐
                         │        MySQL         │
                         │      Database        │
                         └──────────────────────┘
