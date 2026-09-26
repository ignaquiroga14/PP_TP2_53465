package Actividades;

import java.io.Serializable;

public class Estudiante implements Serializable {

    private String legajo;
    private String nombre;

    public Estudiante(String legajo, String nombre) {
        this.legajo = legajo;
        this.nombre = nombre;
    }

    public String getLegajo() {
        return legajo;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre + " (legajo " + legajo + ")";
    }
}
