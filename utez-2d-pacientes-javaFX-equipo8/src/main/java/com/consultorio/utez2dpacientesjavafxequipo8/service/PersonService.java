package com.consultorio.utez2dpacientesjavafxequipo8.service;

import com.consultorio.utez2dpacientesjavafxequipo8.Repository.Paciente;
import com.consultorio.utez2dpacientesjavafxequipo8.Repository.FileRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.util.List;

public class PersonService {
    private final FileRepository repository = new FileRepository();
    private final ObservableList<Paciente> pacientes;

    public PersonService() {
        this.pacientes = FXCollections.observableArrayList();
    }

    public List<String> loadData() throws IOException {
        List<Paciente> loaded = repository.cargarPacientes();
        pacientes.setAll(loaded);
        return null;
    }

    public ObservableList<Paciente> getPacientes() {
        return pacientes;
    }

    public void addPaciente(Paciente paciente) throws IOException {
        if (paciente.getCurp().equalsIgnoreCase(paciente.getCurp())) {
            throw new IllegalArgumentException("El curp ya esta registrado");
        } else {
            pacientes.add(paciente);
            repository.guardarTodo(pacientes);
        }
    }

    public void eliminarAlPaciente(Paciente paciente) throws IOException {
        if (paciente != null) {
            pacientes.remove(paciente);
            repository.guardarTodo(pacientes);
        }
    }

    public void cambiarElEstatus(Paciente paciente) throws IOException {
        if (paciente != null) {
            if (paciente.getEstatus().equals("Activo")) {
                paciente.setEstatus("Inacrivo");
            } else {
                paciente.setEstatus("Activo");
            }
            repository.guardarTodo(pacientes);
        }
    }
}
