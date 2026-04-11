package com.consultorio.utez2dpacientesjavafxequipo8.Repository;

import com.consultorio.utez2dpacientesjavafxequipo8.Launcher;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository {
        private final Path rutaArchivo = Paths.get("Data", "persona.csv");
        private void archivoExist() throws IOException {
            if(Files.notExists(rutaArchivo)){
                Files.createFile(rutaArchivo);
            }
        }
        public List<String> leerLineas() throws IOException {
            archivoExist();
            return Files.readAllLines(rutaArchivo, StandardCharsets.UTF_8);
        }

        // lo lee y da la lista de pacientes
        public List<Launcher.Paciente> cargarPacientes() throws IOException {
            List<String> lines = leerLineas();
            //crea una lista para los objetos paciente
            List<Launcher.Paciente> lista = new ArrayList<>();
            // lee las lineas del archivo de texto
            for (String line : lines) {
                // separa cuando hay coma
                // 0 = CURP, 1= Nombre, ... -1 es para campos vacíos
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    Launcher.Paciente p = new Launcher.Paciente(
                            parts[0].trim(), // extrae y limpia, curp
                            parts[1].trim(), // nombre
                            Integer.parseInt(parts[2].trim()), //texto a numero
                            parts[3].trim(), //telefono
                            parts[4].trim(), //alergias
                            parts[5].trim()  //Estatus
                    );
                    // agrega paciente a la lista
                    lista.add(p);
                }
            }
            // da la lista de pacientes para mostrarlos en TableView
            return lista;
        }
        //guarda la lista de objetos en CSV
        public void guardarTodo(List<Launcher.Paciente> pacientes) throws IOException {
            List<String> lines = new ArrayList<>();
            for (Launcher.Paciente paciente : pacientes) {
                lines.add(String.format("%s,%s,%d,%s,%s,%s",
                        paciente.getCurp(), paciente.getNombre(),
                        paciente.getEdad(), paciente.getTelefono(), paciente.getAlergias(),
                        paciente.getEstatus()));
            }
            Files.write(rutaArchivo, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING);
        }
    }