package com.example.nantes.mines.myChat.listener;

import com.example.nantes.mines.myChat.model.Message;

import java.util.List;

/**
 * Interface for the listener used when a message is send
 */
public interface MessageSenderListener {
    public void OnMessageSendSuccess();
    public void OnMessageSendFail();
}