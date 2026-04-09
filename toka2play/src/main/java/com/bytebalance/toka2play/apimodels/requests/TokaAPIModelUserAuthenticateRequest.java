package com.bytebalance.toka2play.apimodels.requests;

import com.fasterxml.jackson.annotation.JsonProperty;


public class TokaAPIModelUserAuthenticateRequest {
    @JsonProperty("authcode")
    private String authcode;

    // Constructor vacío requerido para la deserialización
    public TokaAPIModelUserAuthenticateRequest() {
    }

    // Constructor con parámetros
    public TokaAPIModelUserAuthenticateRequest(String authcode) {
        this.authcode = authcode;
    }

    // Getter
    public String getAuthcode() {
        return authcode;
    }

    // Setter
    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }
}
