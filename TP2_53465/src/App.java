import Actividades.Actividad;
import Actividades.Charla;
import Actividades.Curso;
import Actividades.Estudiante;
import Actividades.Inscripcion;
import Actividades.Taller;
import Certificacion.Certificable;
import Excepciones.CupoExcedidoException;
import Hilos.EnvioTicketsThread;
import Modelo.EventoUniversitario;
import Modelo.Sala;

import java.util.List;


public class App {

    public static void main(String[] args) {


        Estudiante nachito = new Estudiante("53465", "Ignacio Quiroga");
        Estudiante bruno = new Estudiante("50222", "Bruno Diaz");
        Estudiante orne = new Estudiante("50333", "Ornella Leguizamon");
        Estudiante diego = new Estudiante("50444", "Diego Quiroga");


        EventoUniversitario evento = new EventoUniversitario("EVENTO 1", "DIA DE SISTEMAS", 1000.0, false);
        evento.asignarSala(new Sala(1, "Aula Magna"));

        evento.crearActividad(1, "Introduccion a Java", "Charla", 10);

        evento.crearActividad(2, "Taller de Testing", "Taller", 2);
        evento.crearActividad(3, "Curso de Bases de Datos", "Curso", 10);


        List<Charla> charlas = evento.filtrarActividadesPorTipo(Charla.class);
        List<Taller> talleres = evento.filtrarActividadesPorTipo(Taller.class);
        List<Curso> cursos = evento.filtrarActividadesPorTipo(Curso.class);

        Charla charlaJava = charlas.get(0);
        Taller tallerTesting = talleres.get(0);
        Curso cursoBD = cursos.get(0);

        System.out.println("----- EJERCICIO 1 -----");
        try {
            charlaJava.inscribir(nachito);
            System.out.println("Caso exitoso: " + nachito.getNombre() + " se inscribio en '" + charlaJava.getTitulo() + "'.");

            tallerTesting.inscribir(bruno);
            tallerTesting.inscribir(orne);
            System.out.println("Caso exitoso: cupo del taller (2) completado con Bruno y Orne.");


            tallerTesting.inscribir(diego);

        } catch (CupoExcedidoException e) {
            System.out.println("Caso fallido controlado -> Excepciones.CupoExcedidoException: " + e.getMessage());
        } finally {
            System.out.println("(finally) fin del intento de inscripcion en el taller.\n");
        }


        try {
            cursoBD.inscribir(diego);
            cursoBD.inscribir(orne);
        } catch (CupoExcedidoException e) {
            System.out.println("No se pudo inscribir en el curso: " + e.getMessage());
        }

        evento.mostrarDatos();
        System.out.println();


        System.out.println("----- Persistencia del evento -----");
        try {
            boolean guardado = evento.persistirEvento();
            System.out.println(guardado
                    ? "Evento persistido correctamente en disco (carpeta 'eventos/')."
                    : "No se pudo persistir el evento (ver mensaje anterior).");

            EventoUniversitario recuperado = EventoUniversitario.recuperarEvento(evento.getId());
            if (recuperado != null) {
                System.out.println("Evento recuperado desde archivo:");
                recuperado.mostrarDatos();
            }


            EventoUniversitario.recuperarEvento("ID-QUE-NO-EXISTE");

        } finally {
            System.out.println("(finally) fin del flujo de persistencia.\n");
        }


        System.out.println("----- EJERCICIO 2 -----");
        charlaJava.getInscripciones().get(0).confirmar();
        tallerTesting.getInscripciones().get(0).confirmar();
        cursoBD.getInscripciones().get(0).confirmar();

        emitirCertificadosSiCorresponde(tallerTesting);
        emitirCertificadosSiCorresponde(cursoBD);
        emitirCertificadosSiCorresponde(charlaJava);
        System.out.println();


        System.out.println("----- EJERCICIO 3 -----");
        System.out.println("Cantidad de charlas: " + charlas.size() + " (List<Charla>)");
        System.out.println("Cantidad de talleres: " + talleres.size() + " (List<Taller>)");
        System.out.println("Cantidad de cursos: " + cursos.size() + " (List<Curso>)");

        System.out.println("Costo materiales charlas: $" + evento.calcularCostoMateriales(charlas));
        System.out.println("Costo materiales talleres: $" + evento.calcularCostoMateriales(talleres));
        System.out.println("Costo materiales cursos: $" + evento.calcularCostoMateriales(cursos));
        System.out.println();


        System.out.println("----- EJERCICIO 4 -----");


        for (Actividad a : evento.getActividades()) {
            for (Inscripcion i : a.getInscripciones()) {
                if (i.estaConfirmada()) {
                    i.generarTicket();
                }
            }
        }

        EnvioTicketsThread hiloEnvio = new EnvioTicketsThread(evento);
        hiloEnvio.start(); // arranca un hilo de ejecucion nuevo, en paralelo


        for (int vuelta = 1; vuelta <= 3; vuelta++) {
            System.out.println("[Hilo principal] (" + Thread.currentThread().getName() + ") vuelta " + vuelta + " - evento '" + evento.getTitulo() + "', " + evento.getActividades().size() + " actividades.");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        try {

            hiloEnvio.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nCantidad total de eventos creados en el sistema: " + EventoUniversitario.getCantidadEventos());
        System.out.println("Programa finalizado.");
    }


    private static void emitirCertificadosSiCorresponde(Actividad actividad) {
        if (actividad instanceof Certificable certificable) {
            for (Inscripcion i : actividad.getInscripciones()) {
                if (i.estaConfirmada()) {
                    System.out.println(certificable.generarCertificado(i.getEstudiante()));
                }
            }
        } else {
            System.out.println("'" + actividad.getTitulo() + "' (" + actividad.getTipo() + ") no es certificable.");
        }
    }
}
