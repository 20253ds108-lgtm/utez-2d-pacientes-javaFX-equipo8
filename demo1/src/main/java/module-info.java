module com.consultorio.demo1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.consultorio.demo1 to javafx.fxml;
    exports com.consultorio.demo1;
}