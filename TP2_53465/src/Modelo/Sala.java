package Modelo;

import java.io.Serializable;
public class Sala implements Serializable {

    private int id;
    private String nombre;

    public Sala(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Sala{id=" + id + ", nombre='" + nombre + "'}";
    }
}
