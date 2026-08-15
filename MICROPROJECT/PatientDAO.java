package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientDAO {

    public int getNextPatientId() {
        String sql = "SELECT COALESCE(MAX(patient_id), 0) + 1 AS next_id FROM patients";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("next_id");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching next patient ID: " + e.getMessage());
        }
        return 1;
    }

    public int getPatientCount() {
        String sql = "SELECT COUNT(*) AS total FROM patients";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error getting patient count: " + e.getMessage());
        }
        return 0;
    }

    public boolean addPatient(Patient patient) {
        String sql = "INSERT INTO patients (patient_id, name, age, gender, contact, address) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patient.getPatientId());
            stmt.setString(2, patient.getName());
            stmt.setInt(3, patient.getAge());
            stmt.setString(4, patient.getGender());
            stmt.setString(5, patient.getContact());
            stmt.setString(6, patient.getAddress());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error while adding patient: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a patient using a database transaction to resolve Foreign Key constraints.
     * Cascades deletion across associated appointments and unassigns ward beds.
     */
    public boolean deletePatient(int patientId) {
        String deleteApptsSql = "DELETE FROM appointments WHERE patient_id = ?";
        String updateBedsSql = "UPDATE beds SET status = 'Available', patient_id = NULL WHERE patient_id = ?";
        String deletePatientSql = "DELETE FROM patients WHERE patient_id = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Begin SQL Transaction

            // 1. Delete associated appointments (Resolves Foreign Key constraint)
            try (PreparedStatement stmtAppts = conn.prepareStatement(deleteApptsSql)) {
                stmtAppts.setInt(1, patientId);
                stmtAppts.executeUpdate();
            }

            // 2. Unassign any beds occupied by this patient
            try (PreparedStatement stmtBeds = conn.prepareStatement(updateBedsSql)) {
                stmtBeds.setInt(1, patientId);
                stmtBeds.executeUpdate();
            }

            // 3. Delete patient record
            int rowsAffected = 0;
            try (PreparedStatement stmtPatient = conn.prepareStatement(deletePatientSql)) {
                stmtPatient.setInt(1, patientId);
                rowsAffected = stmtPatient.executeUpdate();
            }

            if (rowsAffected > 0) {
                conn.commit(); // Commit transaction
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (SQLException e) {
            System.err.println("SQLException during patient deletion (ID: " + patientId + "): " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    System.err.println("Error closing connection: " + closeEx.getMessage());
                }
            }
        }
    }

    public ArrayList<Patient> getAllPatients() {
        ArrayList<Patient> patientList = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY patient_id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("contact"),
                        rs.getString("address")
                );
                patientList.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching patients: " + e.getMessage());
            e.printStackTrace();
        }

        return patientList;
    }
}