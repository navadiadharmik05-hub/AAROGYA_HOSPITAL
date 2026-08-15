package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WardDAO{

    public boolean addWard(Ward ward) {
        String sql = "INSERT INTO wards (ward_id, ward_name, ward_type) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ward.getWardId());
            stmt.setString(2, ward.getWardName());
            stmt.setString(3, ward.getWardType());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error while adding ward: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Ward> getAllWards() {
        ArrayList<Ward> wardList = new ArrayList<>();
        String sql = "SELECT * FROM wards";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ward w = new Ward(
                        rs.getInt("ward_id"),
                        rs.getString("ward_name"),
                        rs.getString("ward_type")
                );
                wardList.add(w);
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching wards: " + e.getMessage());
        }

        return wardList;
    }
}