module com.consultorio.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.consultorio.demo to javafx.fxml;
    exports com.consultorio.demo;
}