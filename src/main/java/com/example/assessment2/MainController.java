package com.example.assessment2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {

    @FXML
    private void showStudentTable(ActionEvent event) {
        loadView("StudentTable.fxml", event);
    }

    @FXML
    private void showApplyTable(ActionEvent event) {
        loadView("ApplyTable.fxml", event);
    }

    @FXML
    private void showITPTable(ActionEvent event) {
        loadView("ITPTable.fxml", event);
    }

    private void loadView(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane view = loader.load();

            // Get the Stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
