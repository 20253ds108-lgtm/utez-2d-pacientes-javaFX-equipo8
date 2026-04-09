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

        // Lee el archivo y devuelve una lista de Pacientes
        // Funciona solo porque dios asi lo quizo
        public List<Launcher.Paciente> cargarPacientes() throws IOException {
            List<String> lines = leerLineas();
            // Crea una lista vacía donde guardaremos los objetos de tipo Paciente ya procesado
            List<Launcher.Paciente> lista = new ArrayList<>();
            // Recorre cada una de las líneas obtenidas del archivo de texto
            for (String line : lines) {
                // Separa la línea en trozos cada vez que encuentra una coma.
                // parts[0] será la CURP, parts[1] el Nombre, etc. El "-1" mantiene campos vacíos.
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    Launcher.Paciente p = new Launcher.Paciente(
                            parts[0].trim(), // Extrae la CURP y limpia espacios en blanco accidentales
                            parts[1].trim(), // Extrae el nombre y limpia espacios
                            Integer.parseInt(parts[2].trim()), //Convierte el texto de la edad a un número entero real
                            parts[3].trim(), //Extrae el teléfono
                            parts[4].trim(), //Extrae la información de alergias
                            parts[5].trim()  //Extrae el estatus del paciente
                    );
                    // Agrega el objeto Paciente recién fabricado a nuestra lista de resultados
                    lista.add(p);
                }
            }
            // Devuelve la lista completa de pacientes listos para ser mostrados en la TableView
            return lista;
        }
        // Guarda toda la lista de objetos de vuelta al CSV
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