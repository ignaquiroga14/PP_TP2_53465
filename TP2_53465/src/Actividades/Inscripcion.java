package Actividades;

import java.io.Serializable;
import java.time.LocalDate;

public class Inscripcion implements Serializable {

    private Estudiante estudiante;
    private LocalDate fecha;
    private String estado; // "PENDIENTE", "CONFIRMADA"
    private TicketDeAcceso ticket; // null hasta que se genera

    public Inscripcion(Estudiante estudiante) {
        this.estudiante = estudiante;
        this.fecha = LocalDate.now();
        this.estado = "PENDIENTE";
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void confirmar() {
        this.estado = "CONFIRMADA";
    }

    public boolean estaConfirmada() {
        return "CONFIRMADA".equals(estado);
    }

    public TicketDeAcceso getTicket() {
        return ticket;
    }

    public TicketDeAcceso generarTicket() {
        if (!estaConfirmada()) {
            throw new IllegalStateException(
                    "No se puede emitir un ticket: la inscripción de "
                            + estudiante.getNombre() + " no está confirmada.");
        }
        if (this.ticket == null) {
            this.ticket = new TicketDeAcceso();
        }
        return this.ticket;
    }

    @Override
    public String toString() {
        return "Inscripcion{" + estudiante.getNombre() + ", estado=" + estado + ", fecha=" + fecha + "}";
    }


    public class TicketDeAcceso implements Serializable {

        private String idTicket;
        private LocalDate fechaEmision;

        // Constructor privado: solo Inscripcion (la clase contenedora) puede
        // crear tickets, a través del método generarTicket().
        private TicketDeAcceso() {
            this.idTicket = "TCK-" + Inscripcion.this.estudiante.getLegajo() + "-" + System.nanoTime();
            this.fechaEmision = LocalDate.now();
        }

        public String getIdTicket() {
            return idTicket;
        }

        public void enviarTicket() {
            // Simula el envío (ej: por email). Usa datos de la inscripción
            // "externa" gracias al vínculo implícito de la inner class.
            System.out.println("  -> Enviando " + idTicket + " a " + Inscripcion.this.estudiante.getNombre());
        }

        @Override
        public String toString() {
            return "Ticket{" + idTicket + ", emitido=" + fechaEmision + "}";
        }
    }
}
