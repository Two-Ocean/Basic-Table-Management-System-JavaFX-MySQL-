package com.example.assessment2;
import java.sql.*;

public class StudentDAO {
    private Connection connection;

    public StudentDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addStudent(int id, String name, double gpa, int sizeHS) throws SQLException {
        String query = "INSERT INTO Student (sID, sName, GPA, sizeHS) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setDouble(3, gpa);
            stmt.setInt(4, sizeHS);
            stmt.executeUpdate();
        }
    }

    public ResultSet getStudents() throws SQLException {
        String query = "SELECT * FROM Student";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    public void deleteStudent(int id) throws SQLException {
        String query = "DELETE FROM Student WHERE sID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void updateStudent(int id, String name, double gpa, int sizeHS) throws SQLException {
        String query = "UPDATE Student SET sName = ?, GPA = ?, sizeHS = ? WHERE sID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, gpa);
            stmt.setInt(3, sizeHS);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }
    public ResultSet searchStudentsByName(String name) throws SQLException {
        String query = "SELECT * FROM Student WHERE sName LIKE ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, "%" + name + "%");  // Use LIKE for partial matches
        return stmt.executeQuery();
    }
}
