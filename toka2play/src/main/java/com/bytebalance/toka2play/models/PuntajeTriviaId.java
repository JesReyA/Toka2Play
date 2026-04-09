package com.bytebalance.toka2play.models;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PuntajeTriviaId implements Serializable {
    private String idTrivia;
    private int idUsuario;

    public PuntajeTriviaId() {}

    public PuntajeTriviaId(String idTrivia, int idUsuario) {
        this.idTrivia = idTrivia;
        this.idUsuario = idUsuario;
    }

    public String getIdTrivia() {
        return idTrivia;
    }

    public void setIdTrivia(String idTrivia) {
        this.idTrivia = idTrivia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuntajeTriviaId that = (PuntajeTriviaId) o;
        return idUsuario == that.idUsuario && Objects.equals(idTrivia, that.idTrivia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTrivia, idUsuario);
    }
}
