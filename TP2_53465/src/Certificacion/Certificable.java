package Certificacion;

import Actividades.Estudiante;

public interface Certificable {

    String ENTIDAD_EMISORA = "UTN - Facultad Regional Mendoza";

    String generarCertificado(Estudiante estudiante);
}
