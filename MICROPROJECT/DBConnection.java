package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "Varsha@2408";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL Driver not found! Make sure connector JAR is added: " + e.getMessage());
            throw new SQLException("Driver missing", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Main method to test database connectivity
    public static void main(String[] args) {
        System.out.println("Testing connection to hospital_db...");

        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ SUCCESS: Java is successfully linked to MySQL!");

                // Run a test query to verify reading data
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM patients")) {

                    if (rs.next()) {
                        System.out.println("📊 Test Query Output: Found " + rs.getInt("total") + " patients in database.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ CONNECTION FAILED!");
            System.err.println("Error details: " + e.getMessage());
        }
    }
}