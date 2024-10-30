
package com.example.assessment2;
import java.sql.*;

public class ApplyDAO {
    private Connection connection;

    public ApplyDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addApplication(int sID, String itpName, String major, String decision) throws SQLException {
        String query = "INSERT INTO Apply (sID, itpName, major, decision) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sID);
            stmt.setString(2, itpName);
            stmt.setString(3, major);
            stmt.setString(4, decision);
            stmt.executeUpdate();
        }
    }

    public ResultSet getApplications() throws SQLException {
        String query = "SELECT * FROM Apply";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    public void deleteApplication(int sID, String itpName) throws SQLException {
        String query = "DELETE FROM Apply WHERE sID = ? AND itpName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sID);
            stmt.setString(2, itpName);
            stmt.executeUpdate();
        }
    }

    public void updateApplication(int sID, String itpName, String major, String decision) throws SQLException {
        String query = "UPDATE Apply SET major = ?, decision = ? WHERE sID = ? AND itpName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, major);
            stmt.setString(2, decision);
            stmt.setInt(3, sID);
            stmt.setString(4, itpName);
            stmt.executeUpdate();
        }
    }
    public ResultSet searchApplicationsByITPName(String itpName) throws SQLException {
        String query = "SELECT * FROM Apply WHERE itpName LIKE ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, "%" + itpName + "%");  // Use LIKE for partial matching
        return stmt.executeQuery();
    }
}
