package Hilos;

import Actividades.Actividad;
import Actividades.Inscripcion;
import Modelo.EventoUniversitario;


public class EnvioTicketsThread extends Thread {

    private EventoUniversitario evento;

    public EnvioTicketsThread(EventoUniversitario evento) {
        this.evento = evento;
    }

    @Override
    public void run() {
        System.out.println("[Hilo envío] Empieza el envío de tickets de '" + evento.getTitulo()
                + "' (hilo: " + Thread.currentThread().getName() + ")");

        for (Actividad actividad : evento.getActividades()) {
            for (Inscripcion inscripcion : actividad.getInscripciones()) {
                if (inscripcion.estaConfirmada() && inscripcion.getTicket() != null) {
                    inscripcion.getTicket().enviarTicket();
                    try {
                        // Simula la demora real de un envío (red, servidor de mail, etc.)
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        System.out.println("[Hilo envío] Terminó de enviar todos los tickets.");
    }
}
