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

    // --- 2. LA TABLA (Configurada para usar tu clase Paciente) ---
    @FXML private TableView<Paciente> tableView;
    @FXML private TableColumn<Paciente, String> vistaCurp;
    @FXML private TableColumn<Paciente, String> vistaNombre;
    @FXML private TableColumn<Paciente, Integer> vistaEdad;
    @FXML private TableColumn<Paciente, String> vistaTel;
    @FXML private TableColumn<Paciente, String> vistaAlergias;
    @FXML private TableColumn<Paciente, String> vistaEstatus;

    // --- 3. BOTONES ---
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEstatus;
    @FXML private Button btnEliminar;

    // --- 4. CONFIGURACIÓN AL ARRANCAR (Solo un initialize) ---
    @FXML
    public void initialize() {
        // 1. Conectamos las columnas (Esto déjalo como lo tienes)
        vistaCurp.setCellValueFactory(new PropertyValueFactory<>("curp"));
        vistaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        vistaEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        vistaTel.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        vistaAlergias.setCellValueFactory(new PropertyValueFactory<>("alergias"));
        vistaEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));

        // 2. Cargamos los datos una sola vez de forma correcta
        ObservableList<Paciente> lista = FXCollections.observableArrayList(ManejadorArchivos.leerPacientes());
        tableView.setItems(lista);

        // 3. Actualizamos los numeritos apenas abre
        actualizarContadores();

        // 4. El listener para la selección (el que ya tienes de "Seleccionaste a:")
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                lblError.setText("Seleccionaste a: " + newSelection.getNombre());
            }
        });
    }

    // --- 5. ACCIONES DE LOS BOTONES ---

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
        // 1. Verificamos selección (Esto ya lo tienes)
        Paciente seleccionado = tableView.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            try {
                // Esta forma busca el archivo fxml en la misma carpeta donde está tu controlador
                // Esta es la ruta real según tu estructura de carpetas:
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
                // Si falla, esto nos va a imprimir en la consola EXACTAMENTE qué buscó Java
                lblError.setText("Error: No encontré el archivo. Yo busqué en: " + getClass().getResource("appview.fxml"));
                e.printStackTrace();
            }
        } else {
            lblError.setText("Por favor, selecciona a un paciente de la tabla para editar.");
        }
    }

    @FXML
    void clicEnEstatus(ActionEvent event) {
        // Vemos quién está seleccionado en la tabla
        Paciente seleccionado = tableView.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            //Cambiamos el estatus (Si es Activo pasa a Inactivo, y viceversa)
            if (seleccionado.getEstatus().equalsIgnoreCase("ACTIVO")) {
                seleccionado.setEstatus("INACTIVO");
            } else {
                seleccionado.setEstatus("ACTIVO");
            }
            //Guardamos los cambios en el archivo CSV
            ManejadorArchivos.guardarPacientes(tableView.getItems());
            //Refrescamos la tabla y los contadores
            tableView.refresh();
            actualizarContadores();

            System.out.println("Estatus actualizado para: " + seleccionado.getNombre());
        } else {
            // Si no seleccionó a nadie, le avisamos
            lblError.setText("Por favor, selecciona a un paciente de la tabla.");
        }
    }

    @FXML
    void clicEnEliminar(ActionEvent event) {
        // 1. Obtenemos al paciente seleccionado de la tabla
        Paciente seleccionado = tableView.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            // 2. Creamos la alerta de confirmación
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar eliminación");
            alerta.setHeaderText("¿Estás seguro de eliminar al paciente?");
            alerta.setContentText("El paciente " + seleccionado.getNombre() + " cambiará a estado INACTIVO.");

            // 3. Esperamos a que el usuario presione un botón
            Optional<ButtonType> resultado = alerta.showAndWait();

            // 4. Si presionó el botón OK
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                // Cambiamos el estatus a INACTIVO
                seleccionado.setEstatus("INACTIVO");

                // Guardamos los cambios en el archivo CSV
                ManejadorArchivos.guardarPacientes(tableView.getItems());

                // Refrescamos la tabla y los contadores de la pantalla principal
                tableView.refresh();
                actualizarContadores();

                System.out.println("Paciente inactivado: " + seleccionado.getNombre());
                lblError.setText("Eliminado correctamente");
            }
        } else {
            // Si no seleccionó a nadie, usamos el label de alerta que tienes en tu diseño
            lblError.setText("Selecciona a alguien para eliminar.");
        }
    }

    @FXML
    void clicEnActualizar(ActionEvent event) {
        // Volvemos a leer el archivo CSV
        ObservableList<Paciente> lista = FXCollections.observableArrayList(ManejadorArchivos.leerPacientes());
        // Se lo pasamos a la tabla
        tableView.setItems(lista);
        // Actualizamos contadores
        actualizarContadores();

        lblError.setText("Datos actualizados desde el archivo.");
    }

    public void actualizarContadores() {
        // 1. Obtenemos cuántos pacientes hay en la tabla actualmente
        int total = tableView.getItems().size();

        int activos = 0;
        int inactivos = 0;

        // 2. Recorremos la lista para contar según el estatus
        for (Paciente paciente : tableView.getItems()) {
            if (paciente.getEstatus().equalsIgnoreCase("ACTIVO")) {
                activos++;
            } else {
                inactivos++;
            }
        }

        // 3. Escribimos los resultados en los Labels de la pantalla
        lblTotal.setText("Total: " + total);
        lblActivos.setText("Activos: " + activos);
        lblInactivos.setText("Inactivos: " + inactivos);

        // Un pequeño truco: limpia el lblAlert para que no se quede el mensaje ahí siempre
        lblError.setText("registro cargado con exito");
    }

}