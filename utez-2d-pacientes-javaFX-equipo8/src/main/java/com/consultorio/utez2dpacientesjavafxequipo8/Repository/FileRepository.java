package com.consultorio.utez2dpacientesjavafxequipo8.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository {
    private final Path rutaArchivo = Paths.get("Data", "persona.csv");

    private void archivoExist() throws IOException {
        if (Files.notExists(rutaArchivo)) {
            Files.createFile(rutaArchivo);
        }
    }

    public List<String> leerLineas() throws IOException {
        archivoExist();
        return Files.readAllLines(rutaArchivo, StandardCharsets.UTF_8);
    }

    public List<Paciente> cargarPacientes() throws IOException {
        List<String> lines = leerLineas();
        List<Paciente> lista = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",", -1);
            if (parts.length >= 6) {
                Paciente p = new Paciente(
                        parts[0].trim(),
                        parts[1].trim(),
                        Integer.parseInt(parts[2].trim()),
                        parts[3].trim(),
                        parts[4].trim(),
                        parts[5].trim()
                );
                lista.add(p);
            }
        }
        return lista;
    }

    public void guardarTodo(List<Paciente> pacientes) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Paciente paciente : pacientes) {
            lines.add(String.format("%s,%s,%d,%s,%s,%s",
                    paciente.getCurp(), paciente.getNombre(),
                    paciente.getEdad(), paciente.getTelefono(), paciente.getAlergias(),
                    paciente.getEstatus()));
        }
        Files.write(rutaArchivo, lines, StandardCharsets.UTF_8,
                StandardOpenOption.TRUNCATE_EXISTING);
    }
}