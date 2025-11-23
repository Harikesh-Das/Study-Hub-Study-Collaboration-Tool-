#  Study Hub – Study Collaboration Tool

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)

##  Overview

**Study Hub** is a collaborative study platform designed to help students share notes, discuss topics, manage schedules, and learn together effectively. The application supports real-time interaction, group collaboration, and study resource management in a clean and accessible environment.

---

##  Features

- **User Authentication & Profile Management** – Secure login and personalized user profiles
- **Create, Share & Organize Study Notes and Resources** – Easily upload and manage study materials
- **Real-Time Discussion Forum / Chat System** – Engage in live conversations with peers
- **Subject-wise & Group-wise Collaboration Rooms** – Organize learning by topics and study groups
- **Task & Study Schedule Management** – Track assignments and study plans
- **Search & Filter System for Notes & Groups** – Quickly find relevant content
- **Modern, Responsive UI** – Optimized for both mobile and desktop
- **Secure Data Storage & Access Control** – Protected user data and privacy

---

##  Tech Stack

| Layer        | Technology                      |
|--------------|---------------------------------|
| **Backend**  | Java, Spring Boot               |
| **Frontend** | HTML, CSS, JavaScript           |
| **Database** | MySQL (SQL)                     |
| **Build**    | Maven / Gradle                  |

---

##  Getting Started

### Prerequisites

- Java 11 or higher
- MySQL 8.0 or higher
- Maven 3.6+ or Gradle 7.0+

### 1. Clone the Repository

```bash
git clone https://github.com/Harikesh-Das/Study-Hub-Collaboration-Tool.git
cd Study-Hub
```

### 2. Configure Database

Update your MySQL credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studyhub
spring.datasource.username=yourUser
spring.datasource.password=yourPassword
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the Application

Using Maven:
```bash
mvn spring-boot:run
```

Using Gradle:
```bash
gradle bootRun
```

### 4. Open Browser

Navigate to: **http://localhost:8080/**

---

##  Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

##  Contact

**Project Link:** [https://github.com/Harikesh-Das/Study-Hub-Collaboration-Tool](https://github.com/Harikesh-Das/Study-Hub-Collaboration-Tool)

---

<div align="center">
  Thanks for your contribution
</div>
