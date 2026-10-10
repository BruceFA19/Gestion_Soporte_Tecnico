package Controlador;



import Estructuras.ColaPrioridad;
import Estructuras.ListaEnlazada;
import Estructuras.Pila;
import Estructuras.TablaHash;
import Modelo.AccionAuditoria;
import Modelo.DispositivoEquipo;
import Modelo.EvaluadorPrioridadEquipo;
import Modelo.GestorPersistencia;
import Modelo.Solicitante;
import Modelo.Tecnico;
import Modelo.Tickets.Ticket;
import Modelo.TipoProblema;
import Modelo.Usuario;

//ESTO ES EL CONTROLADOR PRINCIPAL DEL SISTEMA

public class GestorSoporteBancario{
    private ColaPrioridad<Ticket> colaTurnosPendientes;
    private TablaHash<String, Usuario> listaUsuarios;
    private ListaEnlazada<Ticket> historicoTickets;
    private Pila<AccionAuditoria>pilaAuditoria;
    private EvaluadorPrioridadEquipo evaluadorPrioridad;
    private GestorPersistencia gestorPersistencia;

    public GestorSoporteBancario(){
        this.colaTurnosPendientes=new ColaPrioridad<>();
        this.listaUsuarios = new TablaHash<>();
        this.historicoTickets = new ListaEnlazada<>();
        this.pilaAuditoria = new Pila<>();	
        this.evaluadorPrioridad = new EvaluadorPrioridadEquipo();
        this.gestorPersistencia = new GestorPersistencia("data/usuarios.txt", "data/tickets.txt", "data/historial.txt");
    }

    public Ticket registrarLlegadaSolicitante(Solicitante solicitante, DispositivoEquipo equipo, TipoProblema problema, 
                                                int alcanceUsuarios, int gradoBloqueo){
        // 1.) Calcular el puntaje con evaluadorPrioridad usando solicitante.getDepartamento().getCriticidadDepartamento(),alcanceUsuarios y gradoBloqueo
        // 2.) Crear el ticket (TicketExpress o TicketMantenimiento) y llamar registrarEvaluacionPrioridad
        // 3.) Encolarlo en colaTurnosPendientes y registrar la accion en pilaAuditoria

        return null;
    }

    public Ticket asignarSiguienteTurno(Tecnico tecnico){
        // Desencolar el ticket de mayor prioridad, asignarlo al tecnico y cambiar su estado a EN_ATENCION
        return null;
    }

    public Ticket buscarTicketPorCodigo(String codigoTicket) {
        // Buscar en colaTurnosPendientes y en historicoTickets.
        return null;
    }

    public void ordenarTicketsPorPuntajePrioridad() {
        // Ordenar los tickets por puntajeTotalPrioridad (mayor primero).
    }

    public boolean deshacerUltimaAccion() {
        // Sacar la ultima AccionAuditoria de pilaAuditoria y revertirla.
        return false;
    }

    public boolean guardarEstadoSistema() {
        return gestorPersistencia.guardarUsuariosEnArchivo(listaUsuarios)
                && gestorPersistencia.guardarTicketsEnArchivo(colaTurnosPendientes, historicoTickets);
    }

    public boolean cargarEstadoSistema() {
        // Cargar usuarios y tickets con gestorPersistencia y reconstruir la cola y el historico.
        return false;
    }

    public EvaluadorPrioridadEquipo getEvaluadorPrioridad() { return evaluadorPrioridad; }
}
