package com.bytebalance.toka2play.apimodels.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokaAPIModelPaymentCreateResponse {
    @JsonProperty("success")
    private boolean success; // Indica si la operación fue exitosa [cite: 74]

    @JsonProperty("statusCode")
    private int statusCode; // Código de estado HTTP [cite: 75]

    @JsonProperty("message")
    private String message; // Mensaje descriptivo de la respuesta [cite: 76]

    @JsonProperty("data")
    private PaymentData data; // Contenedor de la información del pago [cite: 77, 172]

    // Constructor vacío
    public TokaAPIModelPaymentCreateResponse() {
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PaymentData getData() {
        return data;
    }

    public void setData(PaymentData data) {
        this.data = data;
    }

    /**
     * Clase interna para representar los detalles del pago creado [cite: 172]
     */
    public static class PaymentData {

        @JsonProperty("paymentId")
        private String paymentId; // Identificador único del pago [cite: 173]

        @JsonProperty("paymentUrl")
        private String paymentUrl; // URL para abrir el flujo de pago [cite: 174]

        public PaymentData() {
        }

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }

        public String getPaymentUrl() {
            return paymentUrl;
        }

        public void setPaymentUrl(String paymentUrl) {
            this.paymentUrl = paymentUrl;
        }
    }
}

