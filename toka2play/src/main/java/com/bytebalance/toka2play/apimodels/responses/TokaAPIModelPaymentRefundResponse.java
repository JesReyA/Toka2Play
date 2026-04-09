package com.bytebalance.toka2play.apimodels.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokaAPIModelPaymentRefundResponse {
    private boolean success;
    private int statusCode;
    private String message;
    private RefundData data;

    // Getters y Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public RefundData getData() { return data; }
    public void setData(RefundData data) { this.data = data; }


class RefundData {
    private String refundId;

    // Getter y Setter
    public String getRefundId() { return refundId; }
    public void setRefundId(String refundId) { this.refundId = refundId; }
}
}