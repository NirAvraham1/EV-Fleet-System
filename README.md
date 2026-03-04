# ⚡ Eco-Fleet: Electric Vehicle (EV) Fleet Management System

![Angular](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

## 📖 Project Overview
**Eco-Fleet** is a modern, full-stack microservices application designed to optimize and manage a fleet of electric vehicles. 

The system provides a comprehensive dashboard for tracking vehicle statuses (Available, Charging, In-Use, Maintenance), monitoring battery levels, and managing fleet operations. It implements strict **Role-Based Access Control (RBAC)**, ensuring that Drivers and Fleet Admins have tailored experiences and appropriate data access.

This project was built with a focus on **scalability, security, and containerization**, demonstrating enterprise-level architectural patterns.

---

## ✨ Key Features
* **🔐 Secure Authentication:** Stateless token-based authentication using **JWT**.
* **👥 Role-Based Access Control (RBAC):**
    * **Admin:** Full CRUD capabilities (Add, Delete, Update status of vehicles).
    * **Driver:** Read-only access to view available vehicles and fleet status.
* **🚗 Dynamic Fleet Management:** Real-time tracking of vehicle battery levels, locations, and operational status.
* **🏗️ Microservices Architecture:** Independent services for Authentication and Fleet Operations, allowing independent scaling.
* **💾 Polyglot Persistence:** Utilizing **PostgreSQL** for relational user/auth data and **MongoDB** for flexible, document-based vehicle data.
* **🐳 Fully Containerized:** 100% Dockerized environments for databases, backend services, and the Nginx-served frontend, orchestrated via Docker Compose.

---

## 🛠️ Technology Stack

### Frontend
* **Framework:** Angular (Standalone Components, RxJS, NgZone for optimized change detection)
* **UI/UX:** Angular Material, Tailwind CSS, Custom responsive design
* **Deployment:** Nginx (Containerized)

### Backend (Microservices)
* **Auth Service:** Spring Boot, Spring Security, Spring Data JPA
* **Fleet Service:** Spring Boot, Spring Data MongoDB
* **Language:** Java 21

### Databases
* **Relational DB:** PostgreSQL 15 (Managed by Auth Service)
* **NoSQL DB:** MongoDB 6.0 (Managed by Fleet Service)

### DevOps & Infrastructure
* **Containerization:** Docker, Docker Compose
* **Environment Management:** Secure `.env` variable injection

---

## 🏛️ System Architecture

The application is split into highly cohesive, loosely coupled services:

1.  **Frontend Client (Port 80):** Communicates with the backend REST APIs. Intercepts HTTP requests to attach JWT tokens.
2.  **Auth Service (Port 8080):** Handles `/register` and `/login`. Validates credentials against **PostgreSQL**, generates, and signs JWTs.
3.  **Fleet Service (Port 8081/8082):** Handles all vehicle-related operations. Validates incoming JWTs using the shared secret. Reads/Writes data to **MongoDB**.

---

## 🚀 Getting Started (Running Locally)

To run this project locally, you need [Docker](https://www.docker.com/) and Docker Compose installed on your machine.

### Build and Run via Docker Compose
Run the following command to download images, build the microservices, and start the application:
docker-compose up --build -d

### Access the Application
Once the containers are running, open your browser and navigate to:
👉 http://localhost

### 🔒 Security Best Practices Implemented
No Hardcoded Secrets: All sensitive data is injected via environment variables (.env file).

Stateless Sessions: Using JSON Web Tokens (JWT) instead of server-side sessions.

Frontend Guards: Angular CanActivate route guards prevent unauthorized access to the dashboard.

UI Logic Guards: Destructive actions (Delete/Update) are stripped from the DOM (*ngIf) and blocked at the component logic level for non-admin users.

### Developed by Nir Avraham