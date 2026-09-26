package Actividades;

import Certificacion.Certificable;


public class Curso extends Actividad implements Certificable {

    private int nivel;

    public Curso(int id, String titulo, int cupoMaximo, int nivel) {
        super(id, titulo, cupoMaximo);
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    @Override
    public double calcularCostoMateriales() {
        return 800.0 * nivel;
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "Certificado emitido por " + ENTIDAD_EMISORA + " a " + estudiante.getNombre()
                + " por aprobar el curso '" + getTitulo() + "' (nivel " + nivel + ").";
    }
}
