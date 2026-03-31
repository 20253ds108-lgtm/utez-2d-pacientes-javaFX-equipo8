package com.consultorio.utez2dpacientesjavafxequipo8;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField txtNombre;
    @FXML
    private Label mensaje;

    public void prueba(){ //pruebas inicialeS
        String nombre=txtNombre.getText();
        if (nombre.isEmpty()){
            mensaje.setText("El nombre esta vacio");
        }else {
            mensaje.setText("el nombre tiene algo");
        }

    }

}
