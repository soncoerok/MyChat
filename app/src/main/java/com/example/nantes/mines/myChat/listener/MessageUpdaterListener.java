package com.example.nantes.mines.myChat.listener;

import com.example.nantes.mines.myChat.model.Message;

import java.util.List;

/**
 * Interface for the listener used when a message is send
 */
public interface MessageUpdaterListener {
    public void OnMessageUpdateSuccess(List<Message> messages);
    public void OnMessageUpdateFail();
}