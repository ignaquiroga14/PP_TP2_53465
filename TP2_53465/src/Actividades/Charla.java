package Actividades;


public class Charla extends Actividades.Actividad {

    private String disertante;

    public Charla(int id, String titulo, int cupoMaximo, String disertante) {
        super(id, titulo, cupoMaximo); // llama al constructor de Actividad
        this.disertante = disertante;
    }

    public String getDisertante() {
        return disertante;
    }

    @Override
    public double calcularCostoMateriales() {
        return 0.0; // una charla no necesita materiales
    }

    @Override
    public String getTipo() {
        return "Charla";
    }
}
