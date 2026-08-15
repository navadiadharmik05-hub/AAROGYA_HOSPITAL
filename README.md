<div align="center">

# 🏥 AAROGYA — Hospital Management System
### ⚡ Enterprise Healthcare Operations & Appointment Scheduling Portal

[![Java](https://img.shields.io/badge/Java-JDK_20+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Swing](https://img.shields.io/badge/GUI-Java%20Swing-007396?style=for-the-badge&logo=java&logoColor=white)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![Architecture](https://img.shields.io/badge/Design_Pattern-DAO_Architecture-00897B?style=for-the-badge)]()
[![SDG Goal](https://img.shields.io/badge/UN_SDG-Goal_3_Good_Health-4C9F38?style=for-the-badge&logo=united-nations&logoColor=white)](https://sdgs.un.org/goals/goal3)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](LICENSE)

<p align="center">
  A high-performance desktop application engineered in <b>Java Swing</b> and <b>MySQL</b> designed to streamline clinic workflows, automate doctor-patient consultations, manage historical visit logs, and track real-time ward bed allocations.
</p>

</div>

---

## 📑 Table of Contents

- [Executive Summary](#-executive-summary--problem-scope)
- [Tech Stack](#️-tech-stack--system-architecture)
- [Core Functional Modules](#-core-functional-modules)
- [Database Schema](#️-database-schema-overview)
- [GUI Walkthrough](#-graphical-user-interface-walkthrough)
- [Repository Structure](#-repository-organization)
- [Setup & Installation](#️-setup--installation-guide)
- [Roadmap](#️-roadmap--future-enhancements)
- [Contributors](#-contributors--acknowledgements)
- [License](#-license)

---

## 📌 Executive Summary & Problem Scope

Modern healthcare centers require reliable, paperless, and synchronized record management. **AAROGYA** addresses administrative bottlenecks by providing an intuitive desktop interface backed by transactional database operations:

* 🩺 **Collision-Free Doctor Scheduling** — Eliminates double-booking with real-time slot and time availability validation.
* 📋 **Centralized Patient Records** — Automates patient intake, lookup, and historical appointment log generation.
* 🛏️ **Real-Time Ward & Bed Tracking** — Dynamically monitors bed status (`Available` vs `Occupied`) across General, ICU, and Maternity units.
* 🛡️ **Relational Data Integrity** — Enforces strict database transaction control and cascading deletions across linked tables (`ON DELETE CASCADE`).
* 📊 **Live Operational Dashboard** — Surfaces up-to-date KPIs (patient count, active doctors, bed occupancy) the moment the admin logs in.
* 🌍 **UN SDG 3 Alignment** — Contributes directly to **UN Sustainable Development Goal 3 (Good Health and Well-Being)** by reducing wait times and optimizing essential resource distribution.

---

## 🛠️ Tech Stack & System Architecture

| Layer | Technologies / Frameworks |
| :--- | :--- |
| **☕ Core Language** | Java (JDK 20+) |
| **🎨 GUI Framework** | Java Swing (`javax.swing`, `java.awt`) with a custom navy-and-teal visual theme (`Theme.java`) |
| **🗄️ Relational Database** | MySQL Server 8.0 & MySQL Workbench |
| **🔌 Database Connectivity** | JDBC Driver (`mysql-connector-j-8.x.jar`) |
| **🏛️ Software Architecture** | Data Access Object (DAO) Pattern & POJO Data Models |
| **💻 IDE** | JetBrains IntelliJ IDEA / Visual Studio Code |
| **🧪 Testing Approach** | Manual functional testing across all modules, validated via a project viva |

**Design principles followed:**
- Clear separation between UI (`*Frame.java`), business objects (POJOs), and persistence (`*DAO.java`).
- Centralized DB connection handling via `DBConnection.java` to avoid connection leaks.
- Centralized theming via `Theme.java` so colors, fonts, and component styles stay consistent across every screen.

---

## 🚀 Core Functional Modules

| Module | Class | Description |
| :--- | :--- | :--- |
| 🔐 Admin Authentication | `LoginFrame` | Secure administrative credential verification before entering the system. |
| 📊 Executive KPI Dashboard | `MainWindow` | Live hospital metrics — total patients, active doctors, and occupied bed ratios. |
| 👤 Patient Directory & History | `PatientFrame` | Patient registration form, instant ID-based lookup, deletion with cascade, and appointment history dialogs. |
| 📅 Appointment Management | `AppointmentFrame` | Dynamic slot booking linking registered patients, specialist doctors, dates, and consultation hours — with double-booking prevention. |
| 🩺 Doctor Availability Roster | `DoctorFrame` | Directory of physicians with specializations, consulting days, and time slots. |
| 🏥 Beds & Ward Management | `BedFrame` | Real-time bed allocation and discharge interface across General, ICU, and Maternity wards. |

---

## 🗃️ Database Schema Overview

The MySQL schema (`database/AAROGYA.sql`) models the following core entities and relationships:

- **Patient** — personal & contact details, linked to appointments and bed allocations.
- **Doctor** — specialization, consulting days/time slots, linked to appointments.
- **Appointment** — foreign keys to `Patient` and `Doctor`, with date/time slot constraints to prevent overlapping bookings.
- **Ward / Bed** — ward type (General / ICU / Maternity), bed status (`Available` / `Occupied`), linked to the currently admitted patient.

Referential integrity is enforced with foreign keys and `ON DELETE CASCADE`, so removing a patient automatically cleans up their dependent appointment and bed-allocation records.


---

## 📸 Graphical User Interface Walkthrough

### 📊 1. Executive Admin Dashboard Overview
<img src="https://github.com/user-attachments/assets/aa929cca-dcd7-41d6-b21a-9689fe5df356" width="100%" alt="Dashboard Overview" />

<br>

### 👤 2. Patient Directory & Historical Records
<img src="https://github.com/user-attachments/assets/da8eab7a-2109-447a-96c3-5869567d9fa6" width="100%" alt="Patient Directory & Registration" />

<br>

### 📅 3. Appointment Booking & Consultation Scheduler
<img src="https://github.com/user-attachments/assets/026bdb94-e878-46ed-a635-c111186227bd" width="100%" alt="Appointment Booking Management" />

<br>

### 🛏️ 4. Ward Bed Management & Patient Allocation
<img src="https://github.com/user-attachments/assets/d933c346-5251-4be0-8c85-ceb0baea8863" width="100%" alt="Beds & Ward Allocation" />

---

## 📂 Repository Organization

```text
AAROGYA_HOSPITAL/
├── 📁 src/
│   └── 📁 org/example/
│       ├── 📄 DBConnection.java          # JDBC MySQL Connection Manager
│       ├── 📄 MainWindow.java            # Main Dashboard GUI & KPI Metrics
│       ├── 📄 LoginFrame.java            # Admin Authentication Frame
│       ├── 📄 PatientFrame.java          # Patient Registration & History UI
│       ├── 📄 AppointmentFrame.java      # Doctor Slot Scheduling UI
│       ├── 📄 DoctorFrame.java           # Doctor Directory UI
│       ├── 📄 BedFrame.java              # Ward Bed Allocation UI
│       ├── 📄 Theme.java                 # UI Color Palette & Custom Styling
│       ├── 📄 Patient.java / PatientDAO.java
│       ├── 📄 Appointment.java / AppointmentDAO.java
│       ├── 📄 Bed.java / BedDAO.java
│       ├── 📄 DoctorDAO.java
│       └── 📄 Ward.java / WardDAO.java
├── 📁 database/
│   └── 📄 AAROGYA.sql                    # MySQL Schema, Constraints & Seed Data
├── 📁 lib/
│   └── 📦 mysql-connector-j-8.x.jar      # JDBC MySQL Driver
├── 📄 .gitignore
└── 📄 README.md
```

---

## ⚙️ Setup & Installation Guide

### 📋 1. Prerequisites

* ☕ [Java Development Kit (JDK 20+)](https://www.oracle.com/java/technologies/downloads/)
* 🗄️ [MySQL Server](https://dev.mysql.com/downloads/mysql/) & [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
* 🔌 [MySQL Connector/J (.jar)](https://dev.mysql.com/downloads/connector/j/)

### 🗄️ 2. Database Initialization

1. Start your local MySQL Server.
2. Open MySQL Workbench and execute the database script located in `database/AAROGYA.sql`.
3. Configure your local database credentials inside `src/org/example/DBConnection.java`:

```java
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/aarogya";
    private static final String USER = "root";
    private static final String PASSWORD = "your_mysql_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

> ⚠️ **Security note:** Avoid committing real database credentials to version control. For anything beyond local/academic use, load these values from environment variables or a config file excluded via `.gitignore`.

### 🚀 3. Build & Execution

#### 🔹 Using IntelliJ IDEA / Eclipse

1. Clone the repository:

```bash
git clone https://github.com/navadiadharmik05-hub/AAROGYA_HOSPITAL.git
```

2. Open the project root directory in your IDE.
3. Add `mysql-connector-j-8.x.jar` as a project library dependency (Project Structure → Libraries / Modules).
4. Run `MainWindow.java` or `LoginFrame.java`.

#### 🔹 Using Terminal / Command Line

```bash
# 1. Navigate to the source folder
cd src

# 2. Compile all Java files with the JDBC driver in classpath
javac -cp ".;../lib/mysql-connector-j-8.x.jar" org/example/*.java

# 3. Launch the Application
java -cp ".;../lib/mysql-connector-j-8.x.jar" org.example.MainWindow
```

> **Note:** On macOS/Linux, replace the `;` classpath separator with `:` in the commands above.

### 🔑 4. Default Login Credentials

Use the following credentials to sign in from `LoginFrame`:

| Field | Value |
| :--- | :--- |
| **Username** | `admin` |
| **Password** | `admin123` |

> ⚠️ These are hardcoded default credentials meant for local/academic demo use only. Do not use them as-is in any production or public-facing deployment — see the [Roadmap](#️-roadmap--future-enhancements) for planned authentication hardening.

---

## 🗺️ Roadmap & Future Enhancements

- [ ] Password hashing (e.g. BCrypt) for admin authentication instead of plaintext checks.
- [ ] Role-based access control (Admin / Receptionist / Doctor views).
- [ ] Exportable reports (PDF/Excel) for patient history and bed occupancy.
- [ ] Email/SMS appointment reminders.
- [ ] Migration to a connection pool (e.g. HikariCP) for better performance under load.
- [ ] Unit tests for DAO classes using JUnit + an in-memory/test database.

---

## 👥 Contributors & Acknowledgements

* 👨‍💻 **Dharmik J Navadia** — [@navadiadharmik05-hub](https://github.com/navadiadharmik05-hub)
* 🎓 **Project Guide:** Ms. Rupali Shinde (Assistant Professor, Department of Electronics & Computer Science, SAKEC)

Developed as a Micro Project (CIAP) for the Electronics & Computer Science Department, Shah & Anchor Kutchhi Engineering College (SAKEC).

---

## 📜 License

This project is intended for academic purposes. If you'd like to make reuse terms explicit, consider adding an [MIT License](https://choosealicense.com/licenses/mit/) (or another OSS license of your choice) as a `LICENSE` file in the repository root — the badge at the top of this README already points to it.

---

<div align="center">

*Built with ☕ Java, 🗄️ MySQL, and a focus on real hospital workflows.*

</div>
