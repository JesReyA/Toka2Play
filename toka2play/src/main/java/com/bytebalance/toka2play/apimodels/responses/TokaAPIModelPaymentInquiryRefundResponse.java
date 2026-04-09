package com.bytebalance.toka2play.apimodels.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokaAPIModelPaymentInquiryRefundResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private RefundDetailData data;

    // Getters y Setters
}

@JsonIgnoreProperties(ignoreUnknown = true)
class RefundDetailData {

    @JsonProperty("refundId")
    private String refundId;

    @JsonProperty("refundTime")
    // Opcional: @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") si usas LocalDateTime
    private String refundTime;

    @JsonProperty("refundStatus")
    private String refundStatus;

    @JsonProperty("refundResultCode")
    private String refundResultCode; // Se mapea como String aunque venga null

    @JsonProperty("refundResultMessage")
    private String refundResultMessage;

    @JsonProperty("refundAmount")
    private RefundAmount refundAmount;

    // Getters y Setters
}

@JsonIgnoreProperties(ignoreUnknown = true)
class RefundAmount {

    @JsonProperty("value")
    private BigDecimal value; // BigDecimal es mejor práctica para dinero que Double o int

    @JsonProperty("currency")
    private String currency;

    // Getters y Setters
}