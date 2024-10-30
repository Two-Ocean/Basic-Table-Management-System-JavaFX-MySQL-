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

public class ITPController {

    @FXML private TableView<ITP> itpTable;
    @FXML private TableColumn<ITP, String> colITPName;
    @FXML private TableColumn<ITP, String> colRegion;
    @FXML private TableColumn<ITP, Integer> colEnrollment;

    private ITPDAO itpDAO;

    public void initialize() {
        try {
            itpDAO = new ITPDAO();
            colITPName.setCellValueFactory(new PropertyValueFactory<>("itpName"));
            colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
            colEnrollment.setCellValueFactory(new PropertyValueFactory<>("enrollment"));
            handleRetrieve();  // Automatically load data on initialize
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRetrieve() {
        itpTable.getItems().clear();
        try {
            ResultSet rs = itpDAO.getITPs();
            while (rs.next()) {
                itpTable.getItems().add(new ITP(rs.getString("itpName"), rs.getString("region"), rs.getInt("enrollment")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        try {
            String itpName = getInput("Add ITP - Name", "Enter ITP Name:");
            String region = getInput("Add ITP - Region", "Enter Region:");
            int enrollment = Integer.parseInt(getInput("Add ITP - Enrollment", "Enter Enrollment (integer):"));

            itpDAO.addITP(itpName, region, enrollment);
            handleRetrieve(); // Refresh the table
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Enrollment must be an integer.");
        } catch (SQLException e) {
            showAlert("Error", "Failed to add ITP.");
        }
    }

    @FXML
    private void handleUpdate() {
        ITP selected = itpTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                String itpName = selected.getItpName();
                String region = getInput("Update ITP - Region", "Enter new Region:", selected.getRegion());
                int enrollment = Integer.parseInt(getInput("Update ITP - Enrollment", "Enter new Enrollment (integer):", String.valueOf(selected.getEnrollment())));

                itpDAO.updateITP(itpName, region, enrollment);
                handleRetrieve(); // Refresh the table
            } catch (SQLException | NumberFormatException e) {
                showAlert("Error", "Failed to update ITP.");
            }
        }
    }

    @FXML
    private void handleDelete() {
        ITP selected = itpTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                itpDAO.deleteITP(selected.getItpName());
                handleRetrieve();
            } catch (SQLException e) {
                showAlert("Error", "Failed to delete ITP.");
            }
        }
    }

    @FXML
    private void handleSearch() {
        String region = getInput("Search ITP", "Enter Region to search:");
        itpTable.getItems().clear();
        try {
            ResultSet rs = itpDAO.searchITPsByRegion(region);
            while (rs.next()) {
                itpTable.getItems().add(new ITP(rs.getString("itpName"), rs.getString("region"), rs.getInt("enrollment")));
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to search ITPs.");
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
            Stage stage = (Stage) itpTable.getScene().getWindow();  // Replace applyTable with the table ID in each controller
            stage.getScene().setRoot(mainView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
