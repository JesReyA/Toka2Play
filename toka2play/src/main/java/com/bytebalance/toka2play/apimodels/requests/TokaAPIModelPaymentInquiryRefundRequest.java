package com.bytebalance.toka2play.apimodels.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokaAPIModelPaymentInquiryRefundRequest{

    @JsonProperty ("refundId")
    private String refundId;

    // Constructor vacío (necesario para la deserialización de Jackson)
    public TokaAPIModelPaymentInquiryRefundRequest() {
    }

    public TokaAPIModelPaymentInquiryRefundRequest(String refundId) {
        this.refundId = refundId;
    }

    // Getter y Setter
    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    @Override
    public String toString() {
        return "RefundRequest{" +
                "refundId='" + refundId + '\'' +
                '}';
    }
}