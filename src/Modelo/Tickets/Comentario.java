package Modelo.Tickets;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Comentario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idAutor; // Quién escribió (Técnico o Usuario)
    private String texto;
    private LocalDateTime fechaCreacion;

}
