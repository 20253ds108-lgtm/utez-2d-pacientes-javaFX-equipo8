package com.consultorio.utez2dpacientesjavafxequipo8.controllers;



import com.consultorio.utez2dpacientesjavafxequipo8.Repository.Paciente;

import com.consultorio.utez2dpacientesjavafxequipo8.service.PersonService;

import javafx.fxml.FXML;

import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;



public class Controlador{


    @FXML private Label lblAlert;
    @FXML private TableView<Paciente> tableView;
    @FXML private TableColumn<Paciente, String> viewCurp;
    @FXML private TableColumn<Paciente, String> viewNombre;
    @FXML private TableColumn<Paciente, Integer> viewEdad;
    @FXML private TableColumn<Paciente, String> viewTelefono;
    @FXML private TableColumn<Paciente, String> viewAlergias;
    @FXML private TableColumn<Paciente, String> viewEstatus;



    private final PersonService service = new PersonService();
    @FXML

    public void initialize() {

        viewCurp.setCellValueFactory(new PropertyValueFactory<>("Curp"));

        viewNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));

        viewEdad.setCellValueFactory(new PropertyValueFactory<>("Edad"));

        viewTelefono.setCellValueFactory(new PropertyValueFactory<>("Telefono"));

        viewAlergias.setCellValueFactory(new PropertyValueFactory<>("Alergias"));

        viewEstatus.setCellValueFactory(new PropertyValueFactory<>("Estatus"));

        ingresarDatos();

    }

    private void ingresarDatos() {

        try {

            service.loadData();

            tableView.setItems(service.getPacientes());

            lblAlert.setText("Se agregaron los datos correctamente");

            lblAlert.setStyle("-fx-text-fill: green");



        } catch (IOException e) {

            lblAlert.setText("No se guardaron los datos");

            lblAlert.setStyle("-fx-text-fill: red");



        } catch (NumberFormatException e) {

            lblAlert.setText("La edad debe de ser numerica");

            lblAlert.setStyle("-fx-text-fill: red");

        }

    }



    @FXML

    private void onReload() {

        service.getPacientes().clear();

        try {

            service.loadData();

            lblAlert.setText("Se agregaron los datos correctamente");

            lblAlert.setStyle("-fx-text-fill: green");

        } catch (IOException e) {

            lblAlert.setText("No se guardaron los datos");

            lblAlert.setStyle("-fx-text-fill: red");

        }
    }

    @FXML

    private void onDelete() {


        Paciente designado = tableView.getSelectionModel().getSelectedItem();

        if (designado != null) {

            try {

                service.eliminarAlPaciente(designado);

                tableView.refresh();

                lblAlert.setText("Se elimino correctamente");

                lblAlert.setStyle("-fx-text-fill: green");

            } catch (IOException e) {

                lblAlert.setText("No se pudo eliminar");

                lblAlert.setStyle("-fx-text-fill: red");

            }
        } else {

            lblAlert.setText("Elige el registro a eliminar");

            lblAlert.setStyle("-fx-text-fill: red");

        }

    }

    @FXML

    private void onStatus() {

        Paciente designado = tableView.getSelectionModel().getSelectedItem();

        if (designado != null) {

            try {

                service.cambiarElEstatus(designado);

                tableView.refresh();

                lblAlert.setText("Se actualizo el estatus");

                lblAlert.setStyle("-fx-text-fill: green");

            } catch (IOException e) {

                lblAlert.setText("No se pudo actualizar el estatus");

                lblAlert.setStyle("-fx-text-fill: red");

            }

        } else {

            lblAlert.setText("Elige reistro a actualizar");

            lblAlert.setStyle("-fx-text-fill: red");

        }

    }

}