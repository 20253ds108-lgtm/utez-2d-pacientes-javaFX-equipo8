package com.consultorio.utez2dpacientesjavafxequipo8.service;

import com.consultorio.utez2dpacientesjavafxequipo8.Repositorio.archivoRepositorio;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class personaService {
    private final archivoRepositorio repos = new archivoRepositorio();

    public List<String> loadDataForList() throws IOException {
        List<String> lines = repos.readAllLines(); //lee todas las lineas que estan en pacientes.cvs
        List<String> result = new ArrayList<>(); //en esta linea se las asigna
        for (String line : lines){ //: recorre todas las lineas hasta que acaben
            if (line==null || line.isBlank() ){//ignora las lineas que estan vacias
            continue;
            }
            String[] arreglo = line.split(",",-1); //esta linea almacena los datos que si llrgan a tene algo (separados por una , )
            String curp = arreglo[0].trim();//obtiene el curp
            String nombre = arreglo[1].trim();//obtiene el nombre
            String edad = arreglo[2].trim();//obtiene la edad
            String tel = arreglo[3].trim();//obtiene el telefono
            String alergias = arreglo[4].trim();//obtiene las alergias
            String estatus = arreglo[5].trim();//obtiene el estatus
            result.add(curp+" , "+nombre+" , "+edad+" , "+tel+" , "+alergias+" , "+estatus);//en este orden se mostraran los resultados en la tabla de pacientes
        }
        return  result;
    }
}

