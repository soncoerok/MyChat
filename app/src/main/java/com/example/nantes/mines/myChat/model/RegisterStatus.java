package com.example.nantes.mines.myChat.model;

/**
 *  The response object for the RegisterAsyncTask
 */
public class RegisterStatus {
    private boolean isRegistered;
    private String message;

    public RegisterStatus(boolean isRegistered, String message) {
        this.isRegistered = isRegistered;
        this.message = message;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public String getMessage() {
        return message;
    }

    public void setRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

