package com.bytebalance.toka2play.models;

public class PuntoRequest {
        private String usuario;
        private int puntos;
        // Constructores, Getters y Setters (obligatorios para que Spring funcione)
        public PuntoRequest() {}
        public String getUsuario() { return usuario; }
        public void setUsuario(String usuario) { this.usuario = usuario; }

        public int getPuntos() { return puntos; }
        public void setPuntos(int puntos) { this.puntos = puntos; }
}
