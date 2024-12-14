package com.example.manwon;

public class Gift_CafeDessertItem {
    private String tag;
    private String title;
    private int imageResource;
    private String sellerUid; // 등록자 UID 추가
    private long timestamp;   // 등록 시간 추가

    // 기본 생성자 (Firebase에서 객체 생성 시 필요)
    public Gift_CafeDessertItem() {
    }

    // 생성자
    public Gift_CafeDessertItem(String tag, String title, int imageResource, String sellerUid, long timestamp) {
        this.tag = tag;
        this.title = title;
        this.imageResource = imageResource;
        this.sellerUid = sellerUid;
        this.timestamp = timestamp;
    }

    // Getter 및 Setter
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getSellerUid() {
        return sellerUid;
    }

    public void setSellerUid(String sellerUid) {
        this.sellerUid = sellerUid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
