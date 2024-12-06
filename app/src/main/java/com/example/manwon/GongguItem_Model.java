package com.example.manwon;

public class GongguItem_Model {
    private String itemCategoryType;
    private String itemTitle;
    private String itemDetailsText;
    private String imageUrl; // 이미지 URL
    private boolean participating;
    private int currentParticipants;
    private int targetParticipants;

    public GongguItem_Model() {
        // Firebase 직렬화를 위해 기본생성자 필요
    }

    public GongguItem_Model(String itemCategoryType, String itemTitle, String itemDetailsText, String imageUrl, boolean participating, int currentParticipants, int targetParticipants) {
        this.itemCategoryType = itemCategoryType;
        this.itemTitle = itemTitle;
        this.itemDetailsText = itemDetailsText;
        this.imageUrl = imageUrl;
        this.participating = participating;
        this.currentParticipants = currentParticipants;
        this.targetParticipants = targetParticipants;
    }

    public String getItemCategoryType() {
        return itemCategoryType;
    }

    public void setItemCategoryType(String itemCategoryType) {
        this.itemCategoryType = itemCategoryType;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDetailsText() {
        return itemDetailsText;
    }

    public void setItemDetailsText(String itemDetailsText) {
        this.itemDetailsText = itemDetailsText;
    }

    public boolean isParticipating() {
        return participating;
    }

    public void setParticipating(boolean participating) {
        this.participating = participating;
    }

    public int getCurrentParticipants() {
        return currentParticipants;
    }

    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = currentParticipants;
    }

    public int getTargetParticipants() {
        return targetParticipants;
    }

    public void setTargetParticipants(int targetParticipants) {
        this.targetParticipants = targetParticipants;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
