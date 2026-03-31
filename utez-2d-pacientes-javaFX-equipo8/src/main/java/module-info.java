module com.consultorio.utez2dpacientesjavafxequipo8 {
    requires javafx.controls;
    requires javafx.fxml;



    opens com.consultorio.utez2dpacientesjavafxequipo8 to javafx.fxml;
    exports com.consultorio.utez2dpacientesjavafxequipo8;

    opens com.consultorio.utez2dpacientesjavafxequipo8.controladores to javafx.fxml;
    exports com.consultorio.utez2dpacientesjavafxequipo8.controladores;
}
