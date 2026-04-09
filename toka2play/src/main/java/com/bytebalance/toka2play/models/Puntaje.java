package com.bytebalance.toka2play.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "puntajes")
public class Puntaje {

    @EmbeddedId
    private PuntajeId id;

    private int puntaje;

    // Constructores
    public Puntaje() {
    }

    public Puntaje(PuntajeId id, int puntaje) {
        this.id = id;
        this.puntaje = puntaje;
    }

    // Getters y Setters
    public PuntajeId getId() {
        return id;
    }

    public void setId(PuntajeId id) {
        this.id = id;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}
