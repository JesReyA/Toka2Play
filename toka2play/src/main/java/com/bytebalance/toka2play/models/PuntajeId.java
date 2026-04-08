package com.bytebalance.toka2play.models;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PuntajeId implements Serializable {
    private int idJuego;
    private int idUsuario;

    // Constructores
    public PuntajeId() {
    }

    public PuntajeId(int idJuego, int idUsuario) {
        this.idJuego = idJuego;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    // IMPORTANTE: equals y hashCode son obligatorios para llaves compuestas
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PuntajeId))
            return false;
        PuntajeId that = (PuntajeId) o;
        return idJuego == that.idJuego && idUsuario == that.idUsuario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJuego, idUsuario);
    }
}