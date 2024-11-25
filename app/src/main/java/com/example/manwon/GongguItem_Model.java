package com.example.manwon;

public class GongguItem_Model {
    private String itemCategoryType;
    private String itemTitle;
    private String itemDetailsText;
    private int itemImageResId;
    private boolean isParticipating;
    private int currentParticipants;
    private int targetParticipants;

    // Constructor
    public GongguItem_Model(String itemCategoryType, String itemTitle, String itemDetailsText, int itemImageResId, boolean isParticipating, int currentParticipants, int targetParticipants) {
        this.itemCategoryType = itemCategoryType;
        this.itemTitle = itemTitle;
        this.itemDetailsText = itemDetailsText;
        this.itemImageResId = itemImageResId;
        this.isParticipating = isParticipating;
        this.currentParticipants = currentParticipants;
        this.targetParticipants = targetParticipants;
    }

    // Getters and Setters
    public String getItemCategoryType() {
        return itemCategoryType;
    }

    public void setItemCategoryType(String category) {
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

    public int getItemImageResId() {
        return itemImageResId;
    }

    public void setItemImageResId(int itemImageResId) {
        this.itemImageResId = itemImageResId;
    }

    public boolean isParticipating() {
        return isParticipating;
    }

    public void setParticipating(boolean participating) {
        isParticipating = participating;
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
}