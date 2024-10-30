package com.example.assessment2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class ApplyController {

    @FXML private TableView<Apply> applyTable;
    @FXML private TableColumn<Apply, Integer> colSID;
    @FXML private TableColumn<Apply, String> colITPName;
    @FXML private TableColumn<Apply, String> colMajor;
    @FXML private TableColumn<Apply, String> colDecision;

    private ApplyDAO applyDAO;

    public void initialize() {
        try {
            applyDAO = new ApplyDAO();
            colSID.setCellValueFactory(new PropertyValueFactory<>("sID"));
            colITPName.setCellValueFactory(new PropertyValueFactory<>("itpName"));
            colMajor.setCellValueFactory(new PropertyValueFactory<>("major"));
            colDecision.setCellValueFactory(new PropertyValueFactory<>("decision"));
            handleRetrieve();  // Automatically load data on initialize
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRetrieve() {
        applyTable.getItems().clear();
        try {
            ResultSet rs = applyDAO.getApplications();
            while (rs.next()) {
                applyTable.getItems().add(new Apply(rs.getInt("sID"), rs.getString("itpName"), rs.getString("major"), rs.getString("decision")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        try {
            int sID = Integer.parseInt(getInput("Add Application - Student ID", "Enter Student ID (integer):"));
            String itpName = getInput("Add Application - ITP Name", "Enter ITP Name:");
            String major = getInput("Add Application - Major", "Enter Major:");
            String decision = getInput("Add Application - Decision", "Enter Decision (Y/N):");

            applyDAO.addApplication(sID, itpName, major, decision);
            handleRetrieve(); // Refresh the table
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Student ID must be an integer.");
        } catch (SQLException e) {
            showAlert("Error", "Failed to add application.");
        }
    }

    @FXML
    private void handleUpdate() {
        Apply selected = applyTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                int sID = selected.getsID();
                String itpName = selected.getItpName();
                String major = getInput("Update Application - Major", "Enter new Major:", selected.getMajor());
                String decision = getInput("Update Application - Decision", "Enter new Decision (Y/N):", selected.getDecision());

                applyDAO.updateApplication(sID, itpName, major, decision);
                handleRetrieve(); // Refresh the table
            } catch (SQLException e) {
                showAlert("Error", "Failed to update application.");
            }
        }
    }

    @FXML
    private void handleDelete() {
        Apply selected = applyTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                applyDAO.deleteApplication(selected.getsID(), selected.getItpName());
                handleRetrieve();
            } catch (SQLException e) {
                showAlert("Error", "Failed to delete application.");
            }
        }
    }

    @FXML
    private void handleSearch() {
        String itpName = getInput("Search Application", "Enter ITP Name to search:");
        applyTable.getItems().clear();
        try {
            ResultSet rs = applyDAO.searchApplicationsByITPName(itpName);
            while (rs.next()) {
                applyTable.getItems().add(new Apply(rs.getInt("sID"), rs.getString("itpName"), rs.getString("major"), rs.getString("decision")));
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to search applications.");
        }
    }

    private String getInput(String title, String header, String defaultValue) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        return dialog.showAndWait().orElse(defaultValue);
    }

    private String getInput(String title, String header) {
        return getInput(title, header, "");
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
            Stage stage = (Stage) applyTable.getScene().getWindow();  // Replace applyTable with the table ID in each controller
            stage.getScene().setRoot(mainView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
