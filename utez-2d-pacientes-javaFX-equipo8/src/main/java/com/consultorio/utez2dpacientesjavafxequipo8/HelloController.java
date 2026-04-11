package com.consultorio.utez2dpacientesjavafxequipo8;

import com.consultorio.utez2dpacientesjavafxequipo8.service.PersonService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.util.List;

public class HelloController {
    @FXML
    private TextField txtCurp;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtTel;
    @FXML
    private TextField txtAlergias;
    @FXML
    private TextField txtEstatus;
    @FXML
    private TableView<String> tableView;
    @FXML
    private Label lblAlertas;
    @FXML
    private TableColumn vistaCurp;
    @FXML
    private TableColumn vistaNombre;
    @FXML
    private TableColumn vistaEdad;
    @FXML
    private TableColumn vistaTel;
    @FXML
    private TableColumn vistaAlergias;
    @FXML
    private TableColumn vistaEstatus;

    @FXML
    public void initialize(){
        tableView.getSelectionModel().selectedIndexProperty().addListener((observable,oldValue,newValue)->{
            loadDataFromFile(newValue.toString());
        });
        tableView.setItems(data);
        cargarArchivo();
    }

    @FXML
    private PersonService service = new PersonService();
    private final ObservableList<String> data = FXCollections.observableArrayList();

    private void cargarArchivo(){//tratar de traer los archivos
        try {
            List<String> objeto = service.loadData();
            data.addAll(objeto);
            lblAlertas.setText("se cargaron los datos correctamente");
            lblAlertas.setStyle("-fx-text-fill: green");
        }catch (IOException e){
            lblAlertas.setText("error: no se cargaron los datos");
            lblAlertas.setStyle("-fx-text-fill: red");

        }
    }

    private void loadDataFromFile(String objeto){//cargar la data desde el archivo
        if (objeto==null || objeto.isBlank()){
            return;
        }
        String[] arreglo=objeto.split(",");
        vistaCurp.setText(arreglo[0]);//arreglo para guardar todos los datos
        vistaNombre.setText(arreglo[1]);
        vistaEdad.setText(arreglo[2]);
        vistaTel.setText(arreglo[3]);
        vistaAlergias.setText(arreglo[4]);
        vistaEstatus.setText(arreglo[5]);


    }
}
