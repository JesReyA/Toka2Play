package com.bytebalance.toka2play.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Table;

@Entity
@Table(name = "puntajeTrivia")
public class PuntajeTrivia {

    @EmbeddedId
    private PuntajeTriviaId id;

    private int puntaje;

    public PuntajeTrivia() {
    }

    public PuntajeTrivia(PuntajeTriviaId id, int puntaje) {
        this.id = id;
        this.puntaje = puntaje;
    }

    public PuntajeTriviaId getId() {
        return id;
    }

    public void setId(PuntajeTriviaId id) {
        this.id = id;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}
