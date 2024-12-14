package com.example.manwon;

public class ChatMessage {
    private String senderUid;
    private String message;
    private long timestamp;

    public ChatMessage() {
        // Firebase Realtime Database에서 필요로 하는 빈 생성자
    }

    public ChatMessage(String senderUid, String message, long timestamp) {
        this.senderUid = senderUid;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}