package com.consultorio.utez2dpacientesjavafxequipo8.service;

    import com.consultorio.utez2dpacientesjavafxequipo8.Launcher;
    import com.consultorio.utez2dpacientesjavafxequipo8.Repository.FileRepository;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import java.io.IOException;
    import java.util.List;

public class PersonService {
    private final FileRepository repository = new FileRepository();
    private final ObservableList<Launcher.Paciente> pacientes;

    //Inicio la lista que esta conectada (La que esta en Data y se llama person.csv)
    public PersonService() {
        this.pacientes = FXCollections.observableArrayList();
    }
    //Cargo datos desde el archivo al iniciar
    public void loadData() throws IOException {
        List<Launcher.Paciente> loaded = repository.cargarPacientes();
        pacientes.setAll(loaded);
    }

    //Retornar la lista
    public ObservableList<Launcher.Paciente> getPacientes() {
        return pacientes;
    }
    //Botones
    // Agregar nuevo paciente
    public void addPaciente(Launcher.Paciente paciente) throws IOException {
        if (paciente.getCurp().equalsIgnoreCase(paciente.getCurp())) {
            throw new IllegalArgumentException("El curp ya esta registrado");
        } else {
            pacientes.add(paciente);
            repository.guardarTodo(pacientes);
        }
    }
    // Eliminar un pasciente
    public void eliminarAlPaciente(Launcher.Paciente paciente) throws IOException {
        if (paciente != null) {
            pacientes.remove(paciente);
            repository.guardarTodo(pacientes);
        }
    }
    //Cambiar el estatus de un paciente
    public void cambiarElEstatus(Launcher.Paciente paciente) throws IOException {
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