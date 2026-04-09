package com.bytebalance.toka2play.apimodels.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TokaAPIModelUserInfoRequest {
    @JsonProperty("authCodes")
    private List<String> authCodes;

    // Constructor vacío para Jackson
    public TokaAPIModelUserInfoRequest() {
    }

    // Constructor con parámetros para facilitar el uso en tu lógica
    public TokaAPIModelUserInfoRequest(List<String> authCodes) {
        this.authCodes = authCodes;
    }

    // Getter
    public List<String> getAuthCodes() {
        return authCodes;
    }

    // Setter
    public void setAuthCodes(List<String> authCodes) {
        this.authCodes = authCodes;
    }
}
