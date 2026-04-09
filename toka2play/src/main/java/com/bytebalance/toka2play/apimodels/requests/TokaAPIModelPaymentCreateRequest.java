package com.bytebalance.toka2play.apimodels.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokaAPIModelPaymentCreateRequest {

    @JsonProperty("userId")
    private String userId; // [cite: 160]

    @JsonProperty("orderTitle")
    private String orderTitle; // [cite: 161]

    @JsonProperty("orderAmount")
    private OrderAmount orderAmount; // [cite: 162]

    // Constructor vacío
    public TokaAPIModelPaymentCreateRequest() {
    }

    // Getters y Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public OrderAmount getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(OrderAmount orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * Clase interna para representar el monto de la orden [cite: 162]
     */
    public static class OrderAmount {

        @JsonProperty("value")
        private String value; //

        @JsonProperty("currency")
        private String currency; //

        public OrderAmount() {
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
    }
}
