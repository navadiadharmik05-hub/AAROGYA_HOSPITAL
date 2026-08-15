package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorDAO {

    public int getDoctorCount() {
        String sql = "SELECT COUNT(*) AS total FROM doctors";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error getting doctor count: " + e.getMessage());
        }
        return 0;
    }

    public boolean addDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctors (doctor_id, name, specialization, available_days, available_time) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctor.getDoctorId());
            stmt.setString(2, doctor.getName());
            stmt.setString(3, doctor.getSpecialization());
            stmt.setString(4, doctor.getAvailableDays());
            stmt.setString(5, doctor.getAvailableTime());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error while adding doctor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Doctor> getAllDoctors() {
        ArrayList<Doctor> doctorList = new ArrayList<>();
        String sql = "SELECT * FROM doctors ORDER BY doctor_id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Doctor d = new Doctor(
                        rs.getInt("doctor_id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("available_days"),
                        rs.getString("available_time")
                );
                doctorList.add(d);
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching doctors: " + e.getMessage());
            e.printStackTrace();
        }

        return doctorList;
    }
}