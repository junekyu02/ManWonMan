package com.example.manwon;

public class ChatRoom {
    private String roomId;
    private String lastMessage;
    private long lastMessageTime;
    private String participantUid;
    private String itemTitle; // 아이템 제목 필드 추가

    // 기본 생성자 (Firebase에서 필요)
    public ChatRoom() {
    }

    // 기존 생성자 유지
    public ChatRoom(String roomId, String lastMessage, long lastMessageTime, String participantUid) {
        this.roomId = roomId;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
        this.participantUid = participantUid;
        this.itemTitle = "제목 없음"; // 기본값 설정
    }

    // itemTitle 포함 생성자
    public ChatRoom(String roomId, String lastMessage, long lastMessageTime, String participantUid, String itemTitle) {
        this.roomId = roomId;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
        this.participantUid = participantUid;
        this.itemTitle = itemTitle != null ? itemTitle : "제목 없음"; // 기본값 설정
    }

    public String getRoomId() {
        return roomId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public long getLastMessageTime() {
        return lastMessageTime;
    }

    public String getParticipantUid() {
        return participantUid;
    }

    public String getItemTitle() {
        return itemTitle;
    }
}