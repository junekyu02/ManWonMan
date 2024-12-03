package com.example.manwon;

public class ChatMessage {
    private final String message;
    private final boolean isSent;

    public ChatMessage(String message, boolean isSent) {
        this.message = message;
        this.isSent = isSent;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSent() {
        return isSent;
    }
}