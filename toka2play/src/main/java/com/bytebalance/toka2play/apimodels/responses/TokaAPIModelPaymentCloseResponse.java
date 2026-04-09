package com.bytebalance.toka2play.apimodels.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokaAPIModelPaymentCloseResponse {

    private boolean success;
    private int statusCode;
    private String message;
    private RefundData data;

    // --- Getters y Setters ---

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public RefundData getData() { return data; }
    public void setData(RefundData data) { this.data = data; }

    /**
     * Clase interna para el objeto "data"
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RefundData {
        private String refundId;
        private String refundTime;
        private String refundStatus;
        private String refundResultCode;
        private String refundResultMessage;
        private RefundAmount refundAmount;

        // --- Getters y Setters ---

        public String getRefundId() { return refundId; }
        public void setRefundId(String refundId) { this.refundId = refundId; }

        public String getRefundTime() { return refundTime; }
        public void setRefundTime(String refundTime) { this.refundTime = refundTime; }

        public String getRefundStatus() { return refundStatus; }
        public void setRefundStatus(String refundStatus) { this.refundStatus = refundStatus; }

        public String getRefundResultCode() { return refundResultCode; }
        public void setRefundResultCode(String refundResultCode) { this.refundResultCode = refundResultCode; }

        public String getRefundResultMessage() { return refundResultMessage; }
        public void setRefundResultMessage(String refundResultMessage) { this.refundResultMessage = refundResultMessage; }

        public RefundAmount getRefundAmount() { return refundAmount; }
        public void setRefundAmount(RefundAmount refundAmount) { this.refundAmount = refundAmount; }
    }

    /**
     * Clase interna para el objeto "refundAmount"
     */
    public static class RefundAmount {
        private double value; // Usamos double para montos monetarios
        private String currency;

        // --- Getters y Setters ---

        public double getValue() { return value; }
        public void setValue(double value) { this.value = value; }

        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
    }
}