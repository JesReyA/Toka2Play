package com.bytebalance.toka2play.apimodels.responses;

public class TokaAPIModelUserAuthenticateResponse<T> {

        private boolean success; // Indica si la operación fue exitosa [cite: 74]
        private int statusCode;  // Código de estado HTTP (ej. 200) [cite: 75]
        private String message;   // Mensaje descriptivo (ej. "Authentication successful.") [cite: 76]
        private T data;          // Cuerpo útil de la respuesta [cite: 77, 78]

        public TokaAPIModelUserAuthenticateResponse() {}

        // Getters y Setters
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public int getStatusCode() { return statusCode; }
        public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public T getData() { return data; }
        public void setData(T data) { this.data = data; }


}
