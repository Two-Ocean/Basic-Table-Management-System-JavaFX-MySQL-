package com.example.assessment2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class StudentController {

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Integer> colId;
    @FXML private TableColumn<Student, String> colName;
    @FXML private TableColumn<Student, Double> colGPA;
    @FXML private TableColumn<Student, Integer> colSizeHS;

    private StudentDAO studentDAO;

    public void initialize() {
        try {
            studentDAO = new StudentDAO();
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colGPA.setCellValueFactory(new PropertyValueFactory<>("gpa"));
            colSizeHS.setCellValueFactory(new PropertyValueFactory<>("sizeHS"));
            handleRetrieve();  // Automatically load data on initialize
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRetrieve() {
        studentTable.getItems().clear();
        try {
            ResultSet rs = studentDAO.getStudents();
            while (rs.next()) {
                studentTable.getItems().add(new Student(rs.getInt("sID"), rs.getString("sName"), rs.getDouble("GPA"), rs.getInt("sizeHS")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        TextInputDialog idDialog = new TextInputDialog();
        idDialog.setTitle("Add Student - ID");
        idDialog.setHeaderText("Enter Student ID (integer):");
        Optional<String> idInput = idDialog.showAndWait();

        if (idInput.isPresent()) {
            int id;
            try {
                id = Integer.parseInt(idInput.get());
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "ID must be an integer.");
                return;
            }

            TextInputDialog nameDialog = new TextInputDialog();
            nameDialog.setTitle("Add Student - Name");
            nameDialog.setHeaderText("Enter Student Name:");
            Optional<String> nameInput = nameDialog.showAndWait();
            String name = nameInput.orElse("Unknown");

            TextInputDialog gpaDialog = new TextInputDialog();
            gpaDialog.setTitle("Add Student - GPA");
            gpaDialog.setHeaderText("Enter GPA (decimal number):");
            Optional<String> gpaInput = gpaDialog.showAndWait();
            double gpa;
            try {
                gpa = Double.parseDouble(gpaInput.orElse("0.0"));
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "GPA must be a decimal number.");
                return;
            }

            TextInputDialog sizeHSDialog = new TextInputDialog();
            sizeHSDialog.setTitle("Add Student - High School Size");
            sizeHSDialog.setHeaderText("Enter High School Size (integer):");
            Optional<String> sizeHSInput = sizeHSDialog.showAndWait();
            int sizeHS;
            try {
                sizeHS = Integer.parseInt(sizeHSInput.orElse("1000"));
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "High School Size must be an integer.");
                return;
            }

            try {
                studentDAO.addStudent(id, name, gpa, sizeHS);
                handleRetrieve();
            } catch (SQLException e) {
                showAlert("Error", "Failed to add student.");
            }
        }
    }

    @FXML
    private void handleUpdate() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            TextInputDialog dialog = new TextInputDialog(selected.getName());
            dialog.setTitle("Update Student");
            dialog.setHeaderText("Enter new details for the student");

            dialog.setContentText("Name:");
            String newName = dialog.showAndWait().orElse(selected.getName());

            dialog.setContentText("GPA:");
            double newGPA = Double.parseDouble(dialog.showAndWait().orElse(Double.toString(selected.getGpa())));

            dialog.setContentText("Size HS:");
            int newSizeHS = Integer.parseInt(dialog.showAndWait().orElse(Integer.toString(selected.getSizeHS())));

            try {
                studentDAO.updateStudent(selected.getId(), newName, newGPA, newSizeHS);
                handleRetrieve();
            } catch (SQLException e) {
                showAlert("Error", "Failed to update student.");
            }
        }
    }

    @FXML
    private void handleDelete() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                studentDAO.deleteStudent(selected.getId());
                handleRetrieve();
            } catch (SQLException e) {
                showAlert("Error", "Failed to delete student.");
            }
        }
    }

    @FXML
    private void handleSearch() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Student");
        dialog.setHeaderText("Enter the student name to search:");
        Optional<String> nameInput = dialog.showAndWait();

        if (nameInput.isPresent()) {
            String name = nameInput.get();
            studentTable.getItems().clear();
            try {
                ResultSet rs = studentDAO.searchStudentsByName(name);
                while (rs.next()) {
                    studentTable.getItems().add(new Student(rs.getInt("sID"), rs.getString("sName"), rs.getDouble("GPA"), rs.getInt("sizeHS")));
                }
            } catch (SQLException e) {
                showAlert("Error", "Failed to search for students.");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            AnchorPane mainView = loader.load();
            Stage stage = (Stage) studentTable.getScene().getWindow();  // Replace applyTable with the table ID in each controller
            stage.getScene().setRoot(mainView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
