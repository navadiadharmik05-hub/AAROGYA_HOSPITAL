package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BedDAO {

    public int getOccupiedBedCount() {
        String sql = "SELECT COUNT(*) AS total FROM beds WHERE patient_id IS NOT NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching occupied bed count: " + e.getMessage());
        }
        return 0;
    }

    public int getTotalBedCount() {
        String sql = "SELECT COUNT(*) AS total FROM beds";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total bed count: " + e.getMessage());
        }
        return 0;
    }

    public boolean addBed(Bed bed) {
        String sql = "INSERT INTO beds (bed_id, ward_id, bed_number, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bed.getBedId());
            stmt.setInt(2, bed.getWardId());
            stmt.setString(3, bed.getBedNumber());
            stmt.setString(4, bed.getStatus());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error while adding bed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Bed> getAllBeds() {
        ArrayList<Bed> bedList = new ArrayList<>();
        String sql = "SELECT * FROM beds ORDER BY bed_id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Integer patientId = getOptionalInt(rs, "patient_id");
                String status = (patientId != null) ? "Occupied" : "Available";

                Bed b = new Bed(
                        rs.getInt("bed_id"),
                        rs.getInt("ward_id"),
                        rs.getString("bed_number"),
                        status,
                        patientId
                );
                bedList.add(b);
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching beds: " + e.getMessage());
            e.printStackTrace();
        }

        return bedList;
    }

    public boolean updateBedStatus(int bedId, String newStatus) {
        String sql = "UPDATE beds SET status = ? WHERE bed_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, bedId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error while updating bed status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Bed getFirstAvailableBedInWard(int wardId) {
        String sqlWithPatient = "SELECT * FROM beds WHERE ward_id = ? AND (LOWER(status) = 'available' OR patient_id IS NULL) LIMIT 1";
        String sqlStatusOnly = "SELECT * FROM beds WHERE ward_id = ? AND LOWER(status) = 'available' LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlWithPatient)) {

            stmt.setInt(1, wardId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Integer patientId = getOptionalInt(rs, "patient_id");
                return new Bed(
                        rs.getInt("bed_id"),
                        rs.getInt("ward_id"),
                        rs.getString("bed_number"),
                        patientId != null ? "Occupied" : "Available",
                        patientId
                );
            }
        } catch (SQLException e) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sqlStatusOnly)) {

                stmt.setInt(1, wardId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new Bed(
                            rs.getInt("bed_id"),
                            rs.getInt("ward_id"),
                            rs.getString("bed_number"),
                            "Available",
                            null
                    );
                }
            } catch (SQLException ex) {
                System.err.println("Error finding available bed: " + ex.getMessage());
            }
        }
        return null;
    }

    public boolean assignBed(int bedId, int patientId) {
        String sqlWithPatient = "UPDATE beds SET status = 'Occupied', patient_id = ? WHERE bed_id = ?";
        String sqlStatusOnly = "UPDATE beds SET status = 'Occupied' WHERE bed_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlWithPatient)) {

            stmt.setInt(1, patientId);
            stmt.setInt(2, bedId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sqlStatusOnly)) {

                stmt.setInt(1, bedId);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;

            } catch (SQLException ex) {
                System.err.println("Error assigning bed: " + ex.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    public Bed admitPatient(int wardId, int patientId) {
        Bed availableBed = getFirstAvailableBedInWard(wardId);

        if (availableBed == null) {
            System.out.println("No beds available in this ward.");
            return null;
        }

        boolean success = assignBed(availableBed.getBedId(), patientId);
        if (success) {
            availableBed.setStatus("Occupied");
            availableBed.setPatientId(patientId);
        }
        return success ? availableBed : null;
    }

    public boolean dischargeBed(int bedId) {
        String sqlWithPatient = "UPDATE beds SET status = 'Available', patient_id = NULL WHERE bed_id = ?";
        String sqlStatusOnly = "UPDATE beds SET status = 'Available' WHERE bed_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlWithPatient)) {

            stmt.setInt(1, bedId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sqlStatusOnly)) {

                stmt.setInt(1, bedId);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;

            } catch (SQLException ex) {
                System.err.println("Error discharging bed: " + ex.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    private Integer getOptionalInt(ResultSet rs, String colName) {
        try {
            Object obj = rs.getObject(colName);
            return obj != null ? rs.getInt(colName) : null;
        } catch (SQLException e) {
            return null;
        }
    }
}