# GITAM Aero Astro Club – Backend System

This repository contains the **production-ready backend system** for the official website of the **GITAM Aero Astro Club**.

> 🐳 **Docker-supported backend** — the project includes optional Docker-based setup to enable one-command local execution with PostgreSQL, demonstrating real-world deployment and onboarding practices.

🌐 **Live Website (Frontend):**  
https://gitam-aero-astro-club.vercel.app/

---

## 📌 Project Overview

The **GITAM Aero Astro Club Backend** is the core system that powers the club’s digital platform. It manages **users, members, content, recruitments, and administrative workflows**, and is designed to handle **real users at scale**.

This backend is actively used and built with **production readiness, scalability, and maintainability** in mind.

---

## ✨ Key Features

### 🔐 Authentication & Authorization
- Secure user authentication
- **Role-based authorization** with three roles:
  - User
  - Member
  - Admin
- Role-protected APIs for sensitive operations

---

### 🧑‍🚀 User, Member & Team Management
- User profile and personal details management
- Member and executive role mapping
- Team and core committee data handling
- Structured for future extensions

---

### 📝 Blogs
- Create, update, and delete blogs
- Admin-controlled publishing workflow
- CMS-like backend support

---

### 📣 Recruitment System
- Admin-controlled recruitment workflows
- Application data storage and tracking
- Recruitment lifecycle management

---

### 🛠️ Admin Dashboard Support
- Dedicated APIs for admin dashboards
- Centralized control over:
  - Users
  - Members
  - Content
  - Recruitments

---

### 📧 Automated Email Notifications
- Admin-triggered email notifications
- Used for recruitment updates and system communication
- SMTP-based integration

---

## 📊 Real-World Usage & Scale

- Stores data for **~25,000 real users**
- Designed to support **100–200 concurrent users**
- Optimized database access with connection pooling
- Built for growth and long-term use

---

## 🏗️ Architecture & Design Principles

- REST-based backend architecture
- Clear separation of concerns
- Stateless request handling
- Relational database design
- Externalized configuration

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- HikariCP

### Database
- PostgreSQL

### Infrastructure
- Docker
- Docker Compose

---

## 🚀 Production Readiness

- No secrets baked into images
- Environment-agnostic configuration
- Database connection pooling
- Graceful startup and shutdown
- Persistent database storage
- Secure role-based access control

---

## 🐳 Running the Backend Using Docker (Optional)

Docker is provided as an **optional but recommended way** to run the backend locally.  
It allows anyone to start the backend **without installing Java or PostgreSQL**.

This is especially useful for:
- Recruiters reviewing the project
- New contributors
- Club juniors onboarding quickly

---

## 📘 API Documentation

The backend exposes a set of REST APIs for authentication, content management, recruitment workflows, and admin operations.

For detailed API paths, request/response schemas, and role-based access information, Swagger UI is available when the backend is running locally:

http://localhost:8080/swagger-ui/index.html

--- 

## 📦 Requirements

### Required
- Docker Desktop (Windows / macOS / Linux)

---

## 📥 How to Get the Docker Setup Files

A ready-to-use Docker setup folder is shared via **Google Drive**, containing the required `docker-compose.yml` file.

### 1️⃣ Download the Docker folder
Download the folder from the link below:

👉 **Google Drive Link:**  
https://drive.google.com/drive/folders/15ToTCRS3RNP2HvPPh0eZtrs7m4ZgvkWe?usp=sharing

---

### 2️⃣ Extract the folder
Open the link and click on download all.

Extract the downloaded folder to any location on your system.

Example:

Desktop/
└── gaac-backend-docker/
    └── docker-compose.yml



---

### 3️⃣ Open Terminal / PowerShell
- Windows → PowerShell
- macOS / Linux → Terminal

---

### 4️⃣ Navigate to the folder

Example:
```bash
cd Desktop/gaac-backend-docker
```
Ensure the folder contains `docker-compose.yml`.

### 5️⃣ Start the backend
```bash
docker compose up
```
### 🌐 Accessing the Backend

Once started, the backend will be available at:

**After**
```md
http://localhost:8080
```

## ⛔ Stopping the Backend

From the same folder:
Stop services (keep database data)
```bash
docker compose down
```
Stop services and reset database
```bash
docker compose down -v
```

---

## 🤝 Collaboration & Contributions

This backend is developed as part of the GITAM Aero Astro Club ecosystem.

Contributions are welcome in:
- Backend features
- Security improvements
- Performance optimization
- Documentation
- DevOps & CI/CD

Typical workflow:
- Fork the repository
- Create a feature branch
- Commit changes
- Open a pull request

## 📌 Final Note

This project represents a real, production-oriented backend system, built to support:
- Real users
- Real data
- Real organizational workflows

Docker is included as a **professional deployment and onboarding tool**, while the core focus remains on **robust backend engineering and scalability**.

