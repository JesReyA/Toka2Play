package com.bytebalance.toka2play.apimodels.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokaAPIModelPaymentCloseRequest {
    @JsonProperty("paymentId")
    private String paymentId; // Identificador único del pago [cite: 193, 218]

    // Constructor vacío requerido para la deserialización de Jackson
    public TokaAPIModelPaymentCloseRequest() {
    }

    // Getters y Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
