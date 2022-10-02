package com.avergreen.grasshopper;

public class MessageDto {
    // { "message": "Hejka" }
    private String message;

    public MessageDto() {

    }

    public MessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
