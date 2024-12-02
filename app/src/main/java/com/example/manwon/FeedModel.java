package com.example.manwon;

import java.util.ArrayList;
import java.util.List;

public class FeedModel {
    private String id;
    private String uid;
    private String title;
    private String time;
    private String content;
    private String articleTitle;
    private String link;
    private String imageUrl;
    private int likes;
    private int commentsCnt;
    private List<String> likedUsers;

    public FeedModel() {
        // Default constructor required for Firebase
    }

    // Constructor matching the FeedWriteActivity requirements
    public FeedModel(String id, String uid, String title, String time, String content,
                     String articleTitle, String link, String imageUrl, int likes, int commentsCnt) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.time = time;
        this.content = content;
        this.articleTitle = articleTitle;
        this.link = link;
        this.imageUrl = imageUrl;
        this.likes = likes;
        this.commentsCnt = commentsCnt;
        this.likedUsers = new ArrayList<>();
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

    public String getArticleTitle() { return articleTitle; }
    public void setArticleTitle(String articleTitle) { this.articleTitle = articleTitle; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public int getCommentsCnt() { return commentsCnt; }
    public void setCommentsCnt(int commentsCnt) { this.commentsCnt = commentsCnt; }

    public List<String> getLikedUsers() { return likedUsers; }
    public void setLikedUsers(List<String> likedUsers) { this.likedUsers = likedUsers; }
}
