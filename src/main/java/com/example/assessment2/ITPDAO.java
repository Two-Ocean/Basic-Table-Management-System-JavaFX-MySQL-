
package com.example.assessment2;
import java.sql.*;

public class ITPDAO {
    private Connection connection;

    public ITPDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addITP(String itpName, String region, int enrollment) throws SQLException {
        String query = "INSERT INTO ITP (itpName, region, enrollment) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, itpName);
            stmt.setString(2, region);
            stmt.setInt(3, enrollment);
            stmt.executeUpdate();
        }
    }

    public ResultSet getITPs() throws SQLException {
        String query = "SELECT * FROM ITP";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    public void deleteITP(String itpName) throws SQLException {
        String query = "DELETE FROM ITP WHERE itpName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, itpName);
            stmt.executeUpdate();
        }
    }

    public void updateITP(String itpName, String region, int enrollment) throws SQLException {
        String query = "UPDATE ITP SET region = ?, enrollment = ? WHERE itpName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, region);
            stmt.setInt(2, enrollment);
            stmt.setString(3, itpName);
            stmt.executeUpdate();
        }
    }

    public ResultSet searchITPsByRegion(String region) throws SQLException {
        String query = "SELECT * FROM ITP WHERE region LIKE ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, "%" + region + "%");  // Use LIKE for partial matching
        return stmt.executeQuery();
    }
}
