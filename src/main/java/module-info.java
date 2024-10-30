module com.example.assessment2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.assessment2 to javafx.fxml;
    exports com.example.assessment2;
}