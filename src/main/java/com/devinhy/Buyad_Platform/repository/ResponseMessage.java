package com.devinhy.Buyad_Platform.repository;

public class ResponseMessage {
    private String message;
    private String status;
    public ResponseMessage() {
    }
    public ResponseMessage(String message, String status) {
        this.message = message;
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public String getStatus() {
        return status;
    }
}
