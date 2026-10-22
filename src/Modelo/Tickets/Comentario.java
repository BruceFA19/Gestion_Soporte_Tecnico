package Modelo.Tickets;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Comentario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idAutor; // Quién escribió (Técnico o Usuario)
    private String texto;
    private LocalDateTime fechaCreacion;

    public Comentario(String idAutor, String texto) {
        this.idAutor = idAutor;
        this.texto = texto;
        this.fechaCreacion = LocalDateTime.now();
    }  
    
    public String getIdAutor() { 
        return idAutor; 
        }
    public String getTexto() { 
        return texto; 
    }
    public LocalDateTime getFechaCreacion() { 
        return fechaCreacion; 
    }
}
