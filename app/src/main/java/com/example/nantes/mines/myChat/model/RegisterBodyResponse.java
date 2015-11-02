package com.example.nantes.mines.myChat.model;

/**
 * Model for a body response after a registration
 */
public class RegisterBodyResponse {
    /** The response status (e.g. 200, 404, ...) */
    private String status;
    /** The response message */
    private String message;
    /** Other element of the body response */
    private String elements;

    public RegisterBodyResponse(){
    }

    public RegisterBodyResponse(String status, String message, String elements) {
        this.status = status;
        this.message = message;
        this.elements = elements;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getElements() {
        return elements;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setElements(String elements) {
        this.elements = elements;
    }
}
