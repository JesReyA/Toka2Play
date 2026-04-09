// ruta: src/main/java/com/bytebalance/toka2play/models/Respuesta.java
package com.bytebalance.toka2play.models;

import jakarta.persistence.*;

@Entity
@Table(name = "respuesta")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRespuesta;

    private String descripcion;
    private boolean esCorrecta;

    public Respuesta() {
    }

    public Respuesta(String descripcion, boolean esCorrecta) {
        this.descripcion = descripcion;
        this.esCorrecta = esCorrecta;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isEsCorrecta() {
        return esCorrecta;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }
}
