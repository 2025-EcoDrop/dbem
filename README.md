---
# 💊 DBEM - BackEnd
This is the backend part of the expired medicine collection agency service developed based on Spring Boot.

---
## 📌 Tech Stacks
- Java 21
- Spring Boot 3.x
- Spring Security + JWT (Access/Refresh)
- Redis (Save Refresh-Token)
- PostgresSQL (User, SendEmail, Booking, Review, Point, Region)
- SendGrid
- Swagger
- Validation
- JPA / Batch
- Lombok

---

## 📋 Features
### 1. 🔐 User Authentication (signup, login, logout, Email Verification)
- Spring-Security based authentication/authorization
- Access-Token + Refresh-Token Structure
- Refresh-Tokens can be stored in Redis and reissued.

### 2. 📝 Medicine Review
- Review CRUD API

### 3. 🚕 Expired Medicine Collection
- Schedule medicine collection
- Accept medication collection
- Points awarded after drug collection is completed

---
