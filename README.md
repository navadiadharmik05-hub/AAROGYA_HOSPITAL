<div align="center">

# 🏥 AAROGYA — Hospital Management System
### ⚡ Enterprise Healthcare Operations & Appointment Scheduling Portal

[![Java](https://img.shields.io/badge/Java-JDK_20+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Swing](https://img.shields.io/badge/GUI-Java%20Swing-007396?style=for-the-badge&logo=java&logoColor=white)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![Architecture](https://img.shields.io/badge/Design_Pattern-DAO_Architecture-00897B?style=for-the-badge)]()
[![SDG Goal](https://img.shields.io/badge/UN_SDG-Goal_3_Good_Health-4C9F38?style=for-the-badge&logo=united-nations&logoColor=white)](https://sdgs.un.org/goals/goal3)

<p align="center">
  A high-performance desktop application engineered in <b>Java Swing</b> and <b>MySQL</b> designed to streamline clinic workflows, automate doctor-patient consultations, manage historical visit logs, and track real-time ward bed allocations.
</p>

</div>

---

## 📌 Executive Summary & Problem Scope

Modern healthcare centers require reliable, paperless, and synchronized record management. **AAROGYA** addresses administrative bottlenecks by providing an intuitive desktop interface backed by transactional database operations:

* 🩺 **Collision-Free Doctor Scheduling**: Eliminates double-booking with real-time slot and time availability validation.
* 📋 **Centralized Patient Records**: Automates patient intake, lookup, and historical appointment log generation.
* 🛏️ **Real-Time Ward & Bed Tracking**: Dynamically monitors bed status (`Available` vs `Occupied`) across General, ICU, and Maternity units.
* 🛡️ **Relational Data Integrity**: Enforces strict database transaction control and cascading deletions across linked tables (`ON DELETE CASCADE`)[cite: 7].
* 🌍 **UN SDG 3 Alignment**: Contributes directly to **UN Sustainable Development Goal 3 (Good Health and Well-Being)** by reducing wait times and optimizing essential resource distribution.

---

## 🛠️ Tech Stack & System Architecture

| Layer | Technologies / Frameworks |
| :--- | :--- |
| **☕ Core Language** | Java (JDK 20+) |
| **🎨 GUI Framework** | Java Swing (`javax.swing`, `java.awt`) with custom visual theming |
| **🗄️ Relational Database** | MySQL Server 8.0 & MySQL Workbench |
| **🔌 Database Connectivity** | JDBC Driver (`mysql-connector-j-8.x.jar`) |
| **🏛️ Software Architecture** | Data Access Object (DAO) Pattern & POJO Data Models |
| **💻 IDE** | JetBrains IntelliJ IDEA / Visual Studio Code |

---

## 🚀 Core Functional Modules

* 🔐 **Admin Authentication Gate (`LoginFrame`)**: Secure administrative credential verification.
* 📊 **Executive KPI Dashboard (`MainWindow`)**: Live hospital metrics tracking total patients, active doctors, and occupied bed ratios.
* 👤 **Patient Directory & History (`PatientFrame`)**: Patient registration form, instant ID query, deletion cascade, and appointment history dialogs[cite: 7].
* 📅 **Appointment Management (`AppointmentFrame`)**: Dynamic slot booking linking registered patients, specialist doctors, dates, and dedicated consultation hours[cite: 3].
* 🩺 **Doctor Availability Roster (`DoctorFrame`)**: Directory displaying physician names, specializations, consulting days, and time slots.
* 🏥 **Beds & Ward Management (`BedFrame`)**: Real-time bed allocation and discharge interface supporting multi-ward operations[cite: 5].

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
│   └── 📦 mysql-connector-j-8.x.jar     # JDBC MySQL Driver
├── 📄 .gitignore
└── 📄 README.md
⚙️ Setup & Installation Guide
📋 1. Prerequisites
☕ Java Development Kit (JDK 20+)

🗄️ [suspicious link removed] & MySQL Workbench

🔌 MySQL Connector/J (.jar)

🗄️ 2. Database Initialization
Start your local MySQL Server.

Open MySQL Workbench and execute the database script located in database/AAROGYA.sql.

Configure your local database credentials inside src/org/example/DBConnection.java:

Java
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/aarogya";
    private static final String USER = "root";
    private static final String PASSWORD = "your_mysql_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
🚀 3. Build & Execution
🔹 Using IntelliJ IDEA / Eclipse:
Clone the repository:

Bash
git clone [https://github.com/navadiadharmik05-hub/AAROGYA_HOSPITAL.git](https://github.com/navadiadharmik05-hub/AAROGYA_HOSPITAL.git)
Open the project root directory in your IDE.

Add mysql-connector-j-8.x.jar as a project library dependency (Project Structure > Libraries / Modules).

Run MainWindow.java or LoginFrame.java[cite: 6].

🔹 Using Terminal / Command Line:
Bash
# 1. Navigate to the source folder
cd src

# 2. Compile all Java files with the JDBC driver in classpath
javac -cp ".;../lib/mysql-connector-j-8.x.jar" org/example/*.java

# 3. Launch the Application
java -cp ".;../lib/mysql-connector-j-8.x.jar" org.example.MainWindow
👥 Contributors & Acknowledgements
👨‍💻 Dharmik J Navadia — (@navadiadharmik05-hub)

🎓 Project Guide: Ms. Rupali Shinde (Assistant Professor, Department of Electronics & Computer Science, SAKEC)
