module com.simple.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.simple.javafxapp to javafx.fxml;
    exports com.simple.javafxapp;
}