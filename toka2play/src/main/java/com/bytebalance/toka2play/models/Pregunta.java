// ruta: src/main/java/com/bytebalance/toka2play/models/Pregunta.java
package com.bytebalance.toka2play.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "pregunta")
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPregunta;

    private String descripcion;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "pregunta_respuesta", joinColumns = @JoinColumn(name = "idPregunta"), inverseJoinColumns = @JoinColumn(name = "idRespuesta"))
    private List<Respuesta> respuestas;

    public Pregunta() {
    }

    public Pregunta(String descripcion, List<Respuesta> respuestas) {
        this.descripcion = descripcion;
        this.respuestas = respuestas;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }
}