package com.bytebalance.toka2play.apimodels.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokaAPIModelPaymentInquiryResponse {
    @JsonProperty("success")
    private boolean success; // [cite: 197]

    @JsonProperty("statusCode")
    private int statusCode; // [cite: 198]

    @JsonProperty("message")
    private String message; // [cite: 199]

    @JsonProperty("data")
    private InquiryData data; // [cite: 200]

    public TokaAPIModelPaymentInquiryResponse() {}

    // Getters y Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public InquiryData getData() { return data; }
    public void setData(InquiryData data) { this.data = data; }

    /**
     * Detalles específicos del estado del pago[cite: 200].
     */
    public static class InquiryData {

        @JsonProperty("paymentId")
        private String paymentId; // [cite: 201]

        @JsonProperty("paymentRequestId")
        private String paymentRequestId; // [cite: 202-203]

        @JsonProperty("paymentAmount")
        private double paymentAmount; //

        @JsonProperty("paymentTime")
        private String paymentTime; // [cite: 205]

        @JsonProperty("paymentCreateTime")
        private String paymentCreateTime; // [cite: 206]

        @JsonProperty("paymentStatus")
        private String paymentStatus; //

        @JsonProperty("paymentResultCode")
        private String paymentResultCode; // [cite: 208]

        @JsonProperty("paymentResultMessage")
        private String paymentResultMessage; // [cite: 209]

        @JsonProperty("paymentMethod")
        private String paymentMethod; // [cite: 210]

        @JsonProperty("cardNumber")
        private String cardNumber; // [cite: 211]

        public InquiryData() {}

        // Getters y Setters
        public String getPaymentId() { return paymentId; }
        public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

        public String getPaymentRequestId() { return paymentRequestId; }
        public void setPaymentRequestId(String paymentRequestId) { this.paymentRequestId = paymentRequestId; }

        public double getPaymentAmount() { return paymentAmount; }
        public void setPaymentAmount(double paymentAmount) { this.paymentAmount = paymentAmount; }

        public String getPaymentTime() { return paymentTime; }
        public void setPaymentTime(String paymentTime) { this.paymentTime = paymentTime; }

        public String getPaymentCreateTime() { return paymentCreateTime; }
        public void setPaymentCreateTime(String paymentCreateTime) { this.paymentCreateTime = paymentCreateTime; }

        public String getPaymentStatus() { return paymentStatus; }
        public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

        public String getPaymentResultCode() { return paymentResultCode; }
        public void setPaymentResultCode(String paymentResultCode) { this.paymentResultCode = paymentResultCode; }

        public String getPaymentResultMessage() { return paymentResultMessage; }
        public void setPaymentResultMessage(String paymentResultMessage) { this.paymentResultMessage = paymentResultMessage; }

        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

        public String getCardNumber() { return cardNumber; }
        public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
}
}
