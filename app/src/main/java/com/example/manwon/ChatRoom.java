package com.example.manwon;

public class ChatRoom {
    private String roomName;
    private String lastMessage;
    private String time;

    public ChatRoom(String roomName, String lastMessage, String time) {
        this.roomName = roomName;
        this.lastMessage = lastMessage;
        this.time = time;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getTime() {
        return time;
    }
}
