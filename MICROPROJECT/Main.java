 package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

 public class Main
 {
     public static void main(String[] args)
     {
         String url = "jdbc:mysql://localhost:3306/hospital_db";
         String user = "root";
         String password = "Varsha@2408";

         PatientDAO dao = new PatientDAO();
         ArrayList<Patient> patients = dao.getAllPatients();
         for (Patient p : patients)
         {
             System.out.println(p.getName() + " - " + p.getAge());
         }

         DoctorDAO doctorDAO = new DoctorDAO();
         for (Doctor d : doctorDAO.getAllDoctors())
         {
             System.out.println(d.getName() + " - " + d.getSpecialization());
         }

         System.out.println("---");

         BedDAO bedDAO = new BedDAO();
         for (Bed b : bedDAO.getAllBeds())
         {
             System.out.println("Bed " + b.getBedNumber() + " - " + b.getStatus());
         }

         Bed assignedBed = bedDAO.admitPatient(1, 3);
         if (assignedBed != null) {
             System.out.println("Patient 3 admitted to bed: " + assignedBed.getBedNumber());
         } else {
             System.out.println("Admission failed - no bed available.");
         }


         System.out.println("---");

         AppointmentDAO apptDAO = new AppointmentDAO();
         for (Appointment a : apptDAO.getAllAppointments())
         {
             System.out.println("Appt " + a.getApptId() + ": Patient " + a.getPatientId() + " with Doctor " + a.getDoctorId());
         }
         ArrayList<String> history = apptDAO.getPatientHistory(3);
         for (String record : history) {
             System.out.println(record);
         }

     }
 }