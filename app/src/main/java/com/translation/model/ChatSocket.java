package com.translation.model;

public class ChatSocket {

    private static ChatSocket chatSocket;

    private ChatSocket() {
    }

    public static ChatSocket getInstance() {
        if (chatSocket == null) {
            synchronized (ChatSocket.class) {
                if (chatSocket == null) {
                    chatSocket = new ChatSocket();
                }
            }
        }
        return chatSocket;
    }



}
