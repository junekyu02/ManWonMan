package com.example.manwon;

public class ChatRoom {
    private String roomId;
    private String lastMessage;
    private long lastMessageTime;
    private String participantUid;
    private String itemTitle; // 아이템 제목 필드 추가
    private String participantNickname;


    // 기본 생성자 (Firebase에서 필요)
    public ChatRoom() {
    }

    // 기존 생성자 유지
    public ChatRoom(String roomId, String participantUid, String lastMessage, long lastMessageTime) {
        this.roomId = roomId;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
        this.participantUid = participantUid;
        this.participantNickname = "알 수 없는 사용자"; // 기본 닉네임
        this.itemTitle = "제목 없음"; // 기본값 설정
    }

    // itemTitle 포함 생성자
    public ChatRoom(String roomId, String lastMessage, long lastMessageTime, String participantUid, String itemTitle) {
        this.roomId = roomId;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
        this.participantUid = participantUid;
        this.participantNickname = participantNickname != null ? participantNickname : "알 수 없는 사용자";

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

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getParticipantNickname() {
        return participantNickname;
    }

    public void setParticipantNickname(String participantNickname) {
        this.participantNickname = participantNickname;
    }

}
