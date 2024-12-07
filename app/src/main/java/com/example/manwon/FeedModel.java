package com.example.manwon;

import java.util.ArrayList;
import java.util.List;

public class FeedModel {
    private String id;
    private String uid;
    private String title;
    private String time;
    private String content;
    private String link;
    private String nickname;
    private int likes;
    private int commentsCnt;
    private List<String> likedUsers;

    // 기본 생성자 (Firebase에서 필요)
    public FeedModel() {
        // likedUsers를 빈 리스트로 초기화
        this.likedUsers = new ArrayList<>();
    }

    // FeedWriteActivity
    public FeedModel(String id, String uid, String title, String time, String content, int likes, int commentsCnt, String nickname) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.time = time;
        this.content = content;
        this.link = link;
        this.likes = likes;
        this.commentsCnt = commentsCnt;
        this.nickname = nickname;
        this.likedUsers = new ArrayList<>();  // likedUsers를 빈 리스트로 초기화
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public int getCommentsCnt() { return commentsCnt; }
    public void setCommentsCnt(int commentsCnt) { this.commentsCnt = commentsCnt; }

    public List<String> getLikedUsers() { return likedUsers; }
    public void setLikedUsers(List<String> likedUsers) { this.likedUsers = likedUsers; }
}
