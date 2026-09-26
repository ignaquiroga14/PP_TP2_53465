package Actividades;

import Excepciones.CupoExcedidoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Actividad implements Serializable {

    public static final int CUPO_MINIMO = 3;

    private int id;
    private String titulo;
    private int cupoMaximo;
    private List<Inscripcion> inscripciones = new ArrayList<>();

    public Actividad(int id, String titulo, int cupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public boolean alcanzoCupoMinimo() {
        return inscripciones.size() >= CUPO_MINIMO;
    }

    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException {
        if (inscripciones.size() >= cupoMaximo) {
            throw new CupoExcedidoException(
                    "No hay cupo en '" + titulo + "' (máximo " + cupoMaximo + " inscriptos).");
        }
        Inscripcion inscripcion = new Inscripcion(estudiante);
        inscripciones.add(inscripcion);
        return inscripcion;
    }

    public void mostrarInscripciones() {
        System.out.println("Inscriptos en '" + titulo + "':");
        if (inscripciones.isEmpty()) {
            System.out.println("  (sin inscriptos)");
        }
        for (Inscripcion i : inscripciones) {
            System.out.println("  - " + i);
        }
    }


    public final void mostrarIdentificacion() {
        System.out.println("[" + getTipo() + "] #" + id + " - " + titulo);
    }

    // Métodos abstractos: cada subclase (Charla/Taller/Curso) los implementa
    // a su manera. Es el corazón del polimorfismo: el mismo mensaje
    // "calcularCostoMateriales()" produce un resultado distinto según el
    // tipo real del objeto en tiempo de ejecución.
    public abstract double calcularCostoMateriales();

    public abstract String getTipo();
}
