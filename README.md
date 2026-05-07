# 📄 DocumentFlow: Automated Document Approval Workflow

## 🌟 Overview
**DocumentFlow** is a robust backend system designed to automate document submission and approval processes within an organization. The core architecture is built on **Spring Integration**, orchestrating asynchronous messaging flows to ensure high scalability and decoupling.

## 🎯 Learning Objectives Achieved
- **Spring Integration Framework:** Implementation of complex event-driven workflows using messaging channels and gateways.
- **Asynchronous Pipelines:** Decoupled architecture using `DirectChannel` and `Service Activators`.
- **Email Automation:** Automated notifications via `Spring Integration Mail` adapter.
- **Security:** Protected REST endpoints using **Spring Security** and **JWT (JSON Web Tokens)**.
- **Robustness:** Implemented **Retry Mechanisms** for transient failures (e.g., mail server issues).

## 🛠 Tech Stack
- **Language:** Java 17
- **Framework:** Spring Boot 3.x
- **Integration:** Spring Integration (Mail, JPA, HTTP)
- **Security:** Spring Security, JWT (io.jsonwebtoken)
- **Database:** MySQL 8.0
- **Documentation:** Swagger / OpenAPI 3.0
- **Infrastructure:** Docker & Docker Compose

## ⚙️ Architecture & Workflow
1. **Submission:** Authenticated users upload documents via REST API.
2. **Messaging:** The `DocumentGateway` triggers a message to `documentSubmissionChannel`.
3. **Audit Log:** A `ServiceActivator` catches the message and persists the initial "SUBMITTED" state to the database.
4. **Transformation:** The `EmailTransformer` converts the `Document` object into a `SimpleMailMessage`.
5. **Notification:** The `MailSendingMessageHandler` sends an approval request to the designated approver.
6. **Decision:** Approvers use the REST API to approve/reject. This triggers a status update flow and final audit logging.

## 🚀 How to Run
### 1. Prerequisites
- Docker & Docker Compose installed.
- Java 17 and Maven.

### 2. Start Infrastructure
Run the following command in the project root to start MySQL and MailHog:
```bash
docker-compose up -d
3. Run the Application
Bash
mvn spring-boot:run
🔗 API Documentation & Testing
Swagger UI: http://localhost:8072/swagger-ui.html

MailHog UI: http://localhost:8025 (To view outgoing emails)

Default Port: 8072

🛡 Security
All endpoints except /api/v1/auth/ and /swagger-ui/ require a valid JWT token.

Header: Authorization: Bearer <your_token>

🔄 Error Handling
The system includes a Retry Advice on the SMTP channel. If the mail server is temporarily unavailable, the system will automatically retry the operation 3 times with a back-off period.