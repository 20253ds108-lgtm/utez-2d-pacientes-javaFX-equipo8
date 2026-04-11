package com.consultorio.utez2dpacientesjavafxequipo8;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManejadorArchivos {
    private static final String NOMBRE_ARCHIVO = "persona.csv";

    // guardar la lista en csv
    public static void guardarPacientes(List<Paciente> lista) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO))) {
            for (Paciente p : lista) {
                // imprimimos los datos separados por comas
                writer.println(p.getCurp() + "," + p.getNombre() + "," + p.getEdad() + "," +
                        p.getTelefono() + "," + p.getAlergias() + "," + p.getEstatus());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    //leer el archivo cuando se abre
    public static List<Paciente> leerPacientes() {
        List<Paciente> lista = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);

        if (!archivo.exists()) return lista; // si el archivo no existe mandamos vacío

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 6) {
                    // creacion de objeto paciente con el texto
                    lista.add(new Paciente(datos[0], datos[1], Integer.parseInt(datos[2]),
                            datos[3], datos[4], datos[5]));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        return lista;
    }
}