<div align="center">

# 🏥 Aarogya — Hospital Management System
**A Modern Java Swing & MySQL Desktop Application for Comprehensive Healthcare Administration**

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Swing](https://img.shields.io/badge/GUI-Java%20Swing-blue?style=for-the-badge)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![Architecture](https://img.shields.io/badge/Pattern-DAO%20Architecture-teal?style=for-the-badge)]()

</div>

---

## 📖 Overview

**Aarogya** is an enterprise-grade desktop hospital management portal engineered in **Java (Swing)** and backed by a **MySQL** relational database. It streamlines end-to-end clinical workflows: automating appointment reservations with collision checks, organizing patient medical visit records, maintaining doctor consultation rosters, and orchestrating ward bed occupancy across hospital units.

---

## 🎯 Problem Statement & Scope

* **Collision-Free Scheduling**: Prevents doctor double-booking by validating real-time slot and time availability.
* **Centralized Patient Management**: Offers patient intake, lookup, and historical appointment log generation.
* **Dynamic Bed & Ward Allocation**: Tracks live occupancy status (`Available` vs `Occupied`) across General, ICU, and Maternity wards.
* **Relational Integrity**: Enforces strict database transaction control and cascading deletions across appointments and ward records.
* **UN SDG 3 Alignment**: Supports **UN Sustainable Development Goal 3 (Good Health and Well-Being)** by eliminating administrative bottlenecks and reducing patient wait times.

---

## 🛠️ Tech Stack & Architecture

* **Core Language:** Java (JDK 20+)
* **User Interface:** Java Swing (`javax.swing`, `java.awt`) with custom UI theming
* **Database Engine:** MySQL 8.0
* **Database Connectivity:** JDBC (`mysql-connector-j`)
* **Design Pattern:** Data Access Object (DAO) Pattern & POJO Data Models
* **Development Environment:** JetBrains IntelliJ IDEA / VS Code

---

## ✨ Key System Modules

| Module | Description |
| :--- | :--- |
| **Authentication Gate** | Secure credential validation portal for administrative hospital staff. |
| **KPI Dashboard** | Real-time overview summarizing total patients, registered doctors, and ward bed occupancy ratios. |
| **Patient Directory** | Comprehensive patient registry with instant ID search, delete protection, and visit history logs. |
| **Appointment Engine** | Dynamic booking system mapping patient IDs to doctors, dates, and dedicated time slots. |
| **Doctor Directory** | Medical staff registry showcasing clinical specializations, consultation days, and active hours. |
| **Bed Management** | Automated admission and discharge system managing beds across General, ICU, and Maternity units. |

---

## 📸 Interface Walkthrough

### 1. Dashboard Overview
<img src="https://github.com/user-attachments/assets/aa929cca-dcd7-41d6-b21a-9689fe5df356" width="100%" alt="Dashboard Overview" />

<br>

### 2. Patient Directory & Registration
<img src="https://github.com/user-attachments/assets/da8eab7a-2109-447a-96c3-5869567d9fa6" width="100%" alt="Patient Directory & Registration" />

<br>

### 3. Appointment Booking Management
<img src="https://github.com/user-attachments/assets/026bdb94-e878-46ed-a635-c111186227bd" width="100%" alt="Appointment Booking Management" />

<br>

### 4. Beds & Ward Allocation
<img src="https://github.com/user-attachments/assets/d933c346-5251-4be0-8c85-ceb0baea8863" width="100%" alt="Beds & Ward Allocation" />

---

## 📂 Project Structure

```text
Aarogya-Hospital-Management-System/
├── src/
│   └── org/example/
│       ├── DBConnection.java          # JDBC Database connection manager
│       ├── MainWindow.java            # Central KPI dashboard interface
│       ├── LoginFrame.java            # Authentication window
│       ├── PatientFrame.java          # Patient intake & history UI
│       ├── AppointmentFrame.java      # Appointment booking UI
│       ├── DoctorFrame.java           # Doctor directory interface
│       ├── BedFrame.java              # Ward bed allocation interface
│       ├── Theme.java                 # UI styling & color constants
│       ├── Patient.java / PatientDAO.java
│       ├── Appointment.java / AppointmentDAO.java
│       ├── Bed.java / BedDAO.java
│       └── DoctorDAO.java
├── database/
│   └── AAROGYA.sql                    # Complete MySQL schema & seed data
├── .gitignore
└── README.md
