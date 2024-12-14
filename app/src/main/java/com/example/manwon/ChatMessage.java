package com.example.manwon;

public class ChatMessage {
    private String senderUid;
    private String message;
    private long timestamp;
    private String senderNickname; // 닉네임 필드 추가

    public ChatMessage() {
        // Firebase Realtime Database에서 필요로 하는 빈 생성자
    }

    public ChatMessage(String senderUid, String message, long timestamp, String senderNickname) {
        this.senderUid = senderUid;
        this.message = message;
        this.timestamp = timestamp;
        this.senderNickname = senderNickname; // 닉네임 초기화
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

    public String getSenderNickname() {
        return senderNickname;
    }
}
