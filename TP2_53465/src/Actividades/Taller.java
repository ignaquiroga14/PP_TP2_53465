package Actividades;

import Certificacion.Certificable;

public class Taller extends Actividades.Actividad implements Certificable {

    private boolean requiereNotebook;

    public Taller(int id, String titulo, int cupoMaximo, boolean requiereNotebook) {
        super(id, titulo, cupoMaximo);
        this.requiereNotebook = requiereNotebook;
    }

    public boolean isRequiereNotebook() {
        return requiereNotebook;
    }

    @Override
    public double calcularCostoMateriales() {
        return requiereNotebook ? 5000.0 : 1500.0;
    }

    @Override
    public String getTipo() {
        return "Taller";
    }

    @Override
    public String generarCertificado(Actividades.Estudiante estudiante) {
        return "Certificado emitido por " + ENTIDAD_EMISORA + " a " + estudiante.getNombre()
                + " por participar en el taller '" + getTitulo() + "'.";
    }
}
