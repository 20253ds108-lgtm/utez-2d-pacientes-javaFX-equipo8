package com.consultorio.utez2dpacientesjavafxequipo8;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class AppViewController {

    // cuadros de texto
    @FXML private TextField txtCurp;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtAlergias;
    @FXML private Label lblError;

    // boton guardar
    @FXML
    public void clicEnGuardar(ActionEvent event) {
        // 1. IMPORTANTE: Limpiar el Label al empezar cada clic
        lblError.setText("");

        // lee los datos
        String curp = txtCurp.getText().trim().toUpperCase();
        String nombre = txtNombre.getText().trim();
        String edadStr = txtEdad.getText().trim();
        String tel = txtTelefono.getText().trim();
        String alergias = txtAlergias.getText().trim();
        String estatus = "Activo";

        //VALIDACIONES return detiene todo y muestra el error, solo si entra en el if

        if (curp.isEmpty() || nombre.isEmpty() || edadStr.isEmpty() || tel.isEmpty()) {
            lblError.setText("¡ERROR: Faltan campos por llenar!");
            return;
        }

        if (curp.length() != 18) {
            lblError.setText("¡ERROR: La CURP debe tener 18 caracteres!");
            return;
        }

        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            lblError.setText("¡ERROR: El nombre solo acepta letras!");
            return;
        }

        if (nombre.length() < 5) {
            lblError.setText("¡El nombre es muy corto! (Mínimo 5 letras)");
            return;
        }

        if (!tel.matches("\\d{10}")) {
            lblError.setText("¡ERROR: El teléfono debe ser de 10 números!");
            return;
        }

        // si entra aquí paso las pruebas anteriores
        try {
            int edad = Integer.parseInt(edadStr);
            if (edad < 0 || edad > 120) {
                lblError.setText("¡ERROR: Edad fuera de rango (0-120)!");
                return;
            }

            //guardamos
            Paciente nuevo = new Paciente(curp, nombre, edad, tel, alergias, estatus);
            java.util.List<Paciente> lista = ManejadorArchivos.leerPacientes();

            boolean encontrado = false;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getCurp().equals(curp)) {
                    lista.set(i, nuevo); // reemplazar datos por nuevos
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                lista.add(nuevo); //si no existia se agrega como nuevo
            }

            ManejadorArchivos.guardarPacientes(lista);

            // Cerramos la ventana
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            lblError.setText("¡ERROR: La edad debe ser un número!");
        }
    }

    // btn cancelar
    @FXML
    void clicEnCancelar(ActionEvent event) {
        //busca la ventana donde se hizo clic y la cierra
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        lblError.setText("SISTEMA LISTO");
    }

    //llena cuadros con la informacion del paciente
    public void cargarDatos(Paciente p) {
        txtCurp.setText(p.getCurp());
        txtNombre.setText(p.getNombre());
        txtEdad.setText(String.valueOf(p.getEdad()));
        txtTelefono.setText(p.getTelefono());
        txtAlergias.setText(p.getAlergias());

        // la curp no se debe editar
        txtCurp.setEditable(false);
    }

}