package com.consultorio.utez2dpacientesjavafxequipo8;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        Application.launch(HelloApplication.class, args);
    }

    public static class Paciente {
        private String curp;
        private String nombre;
        private int edad;
        private String telefono;
        private String alergias;
        private String estatus;

        public Paciente(String curp, String nombre, int edad, String telefono, String alergias, String estatus) {
            this.curp = curp;
            this.nombre = nombre;
            this.edad = edad;
            this.telefono = telefono;
            this.alergias = alergias;
            this.estatus = estatus;
        }
        public String getCurp() { return curp; }
        public String getNombre() { return nombre; }
        public int getEdad() { return edad; }
        public String getTelefono() { return telefono; }
        public String getAlergias() { return alergias; }
        public String getEstatus() { return estatus; }

        public void setEstatus(String estatus) { this.estatus = estatus; }
        public void setNombre(String nombre) { this.nombre = nombre; }
    }
}
