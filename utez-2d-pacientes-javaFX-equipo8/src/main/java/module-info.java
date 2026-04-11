module com.consultorio.utez2dpacientesjavafxequipo8 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.consultorio.utez2dpacientesjavafxequipo8 to javafx.fxml;
    opens com.consultorio.utez2dpacientesjavafxequipo8.controllers to javafx.fxml;
    opens com.consultorio.utez2dpacientesjavafxequipo8.Repository to javafx.base;

    exports com.consultorio.utez2dpacientesjavafxequipo8;
}