package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppointmentDAO {

    public int getNextApptId() {
        String sql = "SELECT COALESCE(MAX(appt_id), 0) + 1 AS next_id FROM appointments";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("next_id");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching next appt ID: " + e.getMessage());
        }
        return 1;
    }

    public boolean isSlotTaken(int doctorId, String apptDate, String apptTime) {
        String sql = "SELECT COUNT(*) AS cnt FROM appointments " +
                "WHERE doctor_id = ? AND appt_date = ? AND appt_time = ? AND status != 'Cancelled'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setString(2, apptDate);
            stmt.setString(3, apptTime);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cnt") > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error checking slot availability: " + e.getMessage());
        }
        return false;
    }

    public boolean addAppointment(Appointment appt) {
        String sql = "INSERT INTO appointments (appt_id, patient_id, doctor_id, appt_date, appt_time, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, appt.getApptId());
            stmt.setInt(2, appt.getPatientId());
            stmt.setInt(3, appt.getDoctorId());
            stmt.setString(4, appt.getApptDate());
            stmt.setString(5, appt.getApptTime());
            stmt.setString(6, appt.getStatus());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error while adding appointment: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> apptList = new ArrayList<>();
        String sql = "SELECT * FROM appointments";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Appointment a = new Appointment(
                        rs.getInt("appt_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getString("appt_date"),
                        rs.getString("appt_time"),
                        rs.getString("status")
                );
                apptList.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching appointments: " + e.getMessage());
        }

        return apptList;
    }

    public ArrayList<String> getPatientHistory(int patientId) {
        ArrayList<String> history = new ArrayList<>();
        String sql = "SELECT a.appt_id, d.name AS doctor_name, d.specialization, a.appt_date, a.appt_time, a.status " +
                "FROM appointments a " +
                "JOIN doctors d ON a.doctor_id = d.doctor_id " +
                "WHERE a.patient_id = ? " +
                "ORDER BY a.appt_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String record = "Appt #" + rs.getInt("appt_id")
                        + " | Dr. " + rs.getString("doctor_name")
                        + " (" + rs.getString("specialization") + ")"
                        + " | " + rs.getString("appt_date") + " " + rs.getString("appt_time")
                        + " | Status: " + rs.getString("status");
                history.add(record);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching patient history: " + e.getMessage());
        }

        return history;
    }
}