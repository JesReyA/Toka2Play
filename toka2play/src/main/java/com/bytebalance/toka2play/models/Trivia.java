// ruta: src/main/java/com/bytebalance/toka2play/models/Trivia.java
package com.bytebalance.toka2play.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "trivia")
public class Trivia {

    @Id
    private String idTrivia;

    private String nombre;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "trivia_pregunta", joinColumns = @JoinColumn(name = "idTrivia"), inverseJoinColumns = @JoinColumn(name = "idPregunta"))
    private List<Pregunta> preguntas;

    public Trivia() {
    }

    public Trivia(String idTrivia, String nombre, List<Pregunta> preguntas) {
        this.idTrivia = idTrivia;
        this.nombre = nombre;
        this.preguntas = preguntas;
    }

    public String getIdTrivia() {
        return idTrivia;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setIdTrivia(String idTrivia) {
        this.idTrivia = idTrivia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}