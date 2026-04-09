package com.bytebalance.toka2play.apimodels.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokaAPIModelPaymentRefundRequest {
    /**
     * Modelo para la solicitud de reembolso basada en userId y paymentId.
     */
        private String userId;
        private String paymentId;
        private RefundAmount refundAmount;

        // --- Getters y Setters ---

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }

        public String getPaymentId() { return paymentId; }
        public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

        public RefundAmount getRefundAmount() { return refundAmount; }
        public void setRefundAmount(RefundAmount refundAmount) { this.refundAmount = refundAmount; }

        /**
         * Clase interna para representar el monto del reembolso
         */
        public static class RefundAmount {
            private String value; // Mantenido como String para coincidir con el JSON ("500")
            private String currency;

            // --- Getters y Setters ---

            public String getValue() { return value; }
            public void setValue(String value) { this.value = value; }

            public String getCurrency() { return currency; }
            public void setCurrency(String currency) { this.currency = currency; }
        }
    }

