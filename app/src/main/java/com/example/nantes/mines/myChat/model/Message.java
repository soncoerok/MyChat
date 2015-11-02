package com.example.nantes.mines.myChat.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Model for a message
 */
public class Message {

    /** The uuid of the user who send the message */
    private String uuid;
    /** The login of the user who send the message */
    private String login;
    /** The content of the message */
    private String message;

    public Message(){}

    public Message(String uuid, String login, String message) {
        this.uuid = uuid;
        this.login = login;
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public String getLogin() {
        return login;
    }

    public String getMessage() {
        return message;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
