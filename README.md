# 🏥 Aarogya — Hospital Management System

A robust desktop application built using **Java Swing**, **JDBC**, and **MySQL** that streamlines healthcare administrative operations, automates doctor appointment scheduling, maintains historical patient logs, and manages ward bed allocations.

---

## 📌 Problem Statement & Scope
* **Schedule Appointments:** Prevent double-booking with doctor time-slot availability checks.
* **Patient Records:** Securely register, look up, and delete patient records with transactional relational integrity.
* **Appointment History:** Track and retrieve chronological visit logs per patient.
* **Ward Allocation:** Monitor and manage real-time bed occupancy across General, ICU, and Maternity wards.
* **UN SDG Alignment:** Contributes to **SDG Goal 3 (Good Health and Well-Being)** by optimizing resource allocation and patient intake.

---

## 🛠️ Tech Stack & Tools
* **Language:** Java (JDK 20+)
* **GUI Toolkit:** Java Swing (`javax.swing`, `java.awt`)
* **Database:** MySQL 8.0
* **Driver:** MySQL Connector/J (`mysql-connector-j-8.x.jar`)
* **Design Pattern:** Data Access Object (DAO) Pattern
* **IDE:** JetBrains IntelliJ IDEA

---

## 🚀 Key Modules & Features
1. **Admin Login:** Secure authentication gate.
2. **Dashboard Overview:** Real-time KPI summary (Total Patients, Total Doctors, Bed Occupancy).
3. **Patient Directory:** Registration, ID search, deletion cascade, and full appointment history dialog.
4. **Appointment Management:** Dynamic slot booking linked to registered doctors and patients.
5. **Doctor Directory:** View specialties, availability days, and consultation hours.
6. **Beds & Wards:** Admit and discharge patients across multiple ward types.

---

## 📸 Screenshots
| Dashboard Overview | Patient Directory & History |
| :---: | :---: |
| *(<img width="869" height="611" alt="image" src="https://github.com/user-attachments/assets/aa929cca-dcd7-41d6-b21a-9689fe5df356" />)* | 
*(<img width="1023" height="630" alt="image" src="https://github.com/user-attachments/assets/da8eab7a-2109-447a-96c3-5869567d9fa6" />)* |

| Appointment Booking | Beds & Ward Allocation |
| :---: | :---: |
| *(<img width="1007" height="634" alt="image" src="https://github.com/user-attachments/assets/026bdb94-e878-46ed-a635-c111186227bd" />)* |
*(<img width="966" height="612" alt="image" src="https://github.com/user-attachments/assets/d933c346-5251-4be0-8c85-ceb0baea8863" />)* |

---

## ⚙️ Setup & Installation

### 1. Prerequisites
* [Java JDK 20+](https://www.oracle.com/java/technologies/downloads/)
* [MySQL Server & Workbench](https://dev.mysql.com/downloads/workbench/)

### 2. Database Setup
1. Open MySQL Workbench.
2. Execute the schema script located in `database/schema.sql`.
3. Update database credentials in `DBConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/aarogya";
   private static final String USER = "your_username";
   private static final String PASSWORD = "your_password";
