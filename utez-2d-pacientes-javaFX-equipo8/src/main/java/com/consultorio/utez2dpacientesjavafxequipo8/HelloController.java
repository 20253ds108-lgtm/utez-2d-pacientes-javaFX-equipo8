package com.consultorio.utez2dpacientesjavafxequipo8;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class HelloController {

    @FXML private Label lblError;


    @FXML private Label lblTotal;
    @FXML private Label lblActivos;
    @FXML private Label lblInactivos;

    //TABLA
    @FXML private TableView<Paciente> tableView;
    @FXML private TableColumn<Paciente, String> vistaCurp;
    @FXML private TableColumn<Paciente, String> vistaNombre;
    @FXML private TableColumn<Paciente, Integer> vistaEdad;
    @FXML private TableColumn<Paciente, String> vistaTel;
    @FXML private TableColumn<Paciente, String> vistaAlergias;
    @FXML private TableColumn<Paciente, String> vistaEstatus;

    @FXML
    public void initialize() {
        //conectamos las columnas
        vistaCurp.setCellValueFactory(new PropertyValueFactory<>("curp"));
        vistaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        vistaEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        vistaTel.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        vistaAlergias.setCellValueFactory(new PropertyValueFactory<>("alergias"));
        vistaEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));

        //cargamos los datos una sola vez
        ObservableList<Paciente> lista = FXCollections.observableArrayList(ManejadorArchivos.leerPacientes());
        tableView.setItems(lista);

        //actualizamos los numeritos
        actualizarContadores();

        // el listener para la selección
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                lblError.setText("Seleccionaste a: " + newSelection.getNombre());
            }
        });
    }

    @FXML
    void clicEnNuevo(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/appview.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Registrar Nuevo Paciente");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error al abrir el formulario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void clicEnEditar(ActionEvent event) {
        // verificamos selección
        Paciente seleccionado = tableView.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            try {
                // esta forma busca el archivo fxml
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/consultorio/utez2dpacientesjavafxequipo8/views/appview.fxml"));
                Parent root = loader.load();

                AppViewController controller = loader.getController();
                controller.cargarDatos(seleccionado);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.sizeToScene();
                stage.setTitle("Editar Paciente");
                stage.show();

            } catch (Exception e) {
                //si falla, esto va a imprimir el error
                lblError.setText("Error: No encontré el archivo. Yo busqué en: " + getClass().getResource("appview.fxml"));
                e.printStackTrace();
            }
        } else {
            lblError.setText("Por favor, selecciona a un paciente de la tabla para editar.");
        }
    }

    @FXML
    void clicEnEstatus(ActionEvent event) {
        // vemos quién está seleccionado en la tabla
        Paciente seleccionado = tableView.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            //cambiamos el estatus
            if (seleccionado.getEstatus().equalsIgnoreCase("ACTIVO")) {
                seleccionado.setEstatus("INACTIVO");
            } else {
                seleccionado.setEstatus("ACTIVO");
            }
            //guardamos los cambios en el archivo CSV
            ManejadorArchivos.guardarPacientes(tableView.getItems());
            //refrescamos la tabla y los contadores
            tableView.refresh();
            actualizarContadores();

            System.out.println("Estatus actualizado para: " + seleccionado.getNombre());
        } else {
            //si no seleccionó a nadie, le avisamos
            lblError.setText("Por favor, selecciona a un paciente de la tabla.");
        }
    }

    @FXML
    void clicEnEliminar(ActionEvent event) {
        // obtenemos al paciente seleccionado de la tabla
        Paciente seleccionado = tableView.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            // creamos la alerta de confirmación
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar eliminación");
            alerta.setHeaderText("¿Estás seguro de eliminar al paciente?");
            alerta.setContentText("El paciente " + seleccionado.getNombre() + " cambiará a estado INACTIVO.");

            // esperamos a que el usuario presione un botón
            Optional<ButtonType> resultado = alerta.showAndWait();

            // si presionó el botón
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                // cambiamos el estatus a INACTIVO
                seleccionado.setEstatus("INACTIVO");

                // guardamos los cambios en el archivo CSV
                ManejadorArchivos.guardarPacientes(tableView.getItems());

                //refrescamos la tabla y los contadores de la pantalla principal
                tableView.refresh();
                actualizarContadores();

                System.out.println("Paciente inactivado: " + seleccionado.getNombre());
                lblError.setText("Eliminado correctamente");
            }
        } else {
            // Si no seleccionó a nadie se avisa
            lblError.setText("Selecciona a alguien para eliminar.");
        }
    }

    @FXML
    void clicEnActualizar(ActionEvent event) {
        // volvemos a leer el archivo CSV
        ObservableList<Paciente> lista = FXCollections.observableArrayList(ManejadorArchivos.leerPacientes());
        //se pasa a la tabla
        tableView.setItems(lista);
        // actualizamos contadores
        actualizarContadores();

        lblError.setText("Datos actualizados desde el archivo.");
    }

    public void actualizarContadores() {
        // obtenemos cuántos pacientes hay en la tabla actualmente
        int total = tableView.getItems().size();

        int activos = 0;
        int inactivos = 0;

        // contamos el estatus activos e inactivos
        for (Paciente paciente : tableView.getItems()) {
            if (paciente.getEstatus().equalsIgnoreCase("ACTIVO")) {
                activos++;
            } else {
                inactivos++;
            }
        }

        //escribimos los resultados con label
        lblTotal.setText("Total: " + total);
        lblActivos.setText("Activos: " + activos);
        lblInactivos.setText("Inactivos: " + inactivos);

        //limpiar el lblAlert dejandolo en blanco
        lblError.setText("registro cargado con exito");
    }

}
