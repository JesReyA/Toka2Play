package com.bytebalance.toka2play.models;

public class TriviaAccionRequest {
    private String usuario;
    private String codigoTrivia; 
    private String respuesta;    

    public TriviaAccionRequest() {}

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getCodigoTrivia() { return codigoTrivia; }
    public void setCodigoTrivia(String codigoTrivia) { this.codigoTrivia = codigoTrivia; }

    public String getRespuesta() { return respuesta; }
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }
}