package com.example.manwon;

public class Gift_CafeDessertItem {
    private String giftId;    // 기프트 ID 추가
    private String tag;
    private String title;
    private int imageResource;
    private String sellerUid; // 등록자 UID
    private long timestamp;   // 등록 시간
    private String detail;

    // 기본 생성자 (Firebase에서 객체 생성 시 필요)
    public Gift_CafeDessertItem() {
    }

    // 생성자
    public Gift_CafeDessertItem(String giftId, String tag, String title, String detail, int imageResource, String sellerUid, long timestamp) {
        this.giftId = giftId;
        this.tag = tag;
        this.title = title;
        this.imageResource = imageResource;
        this.sellerUid = sellerUid;
        this.timestamp = timestamp;
        this.detail=detail;
    }

    // Getter 및 Setter
    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

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

    public String getDetail() {
        return detail; // 상세 설명 Getter 추가
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
