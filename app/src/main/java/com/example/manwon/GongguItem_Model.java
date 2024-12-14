//package com.example.manwon;
//
//public class GongguItem_Model {
//    private String itemCategoryType;
//    private String itemTitle;
//    private String itemDetailsText;
//    private String imageUrl; // 이미지 URL
//    private boolean participating;
//    private int currentParticipants;
//    private int targetParticipants;
//    private String userId; // 게시물 작성자 ID
//
//    public GongguItem_Model() {
//        // Firebase 직렬화를 위해 기본생성자 필요
//    }
//
//    public GongguItem_Model(String itemCategoryType, String itemTitle, String itemDetailsText, String imageUrl, boolean participating, int currentParticipants, int targetParticipants, String userId) {
//        this.itemCategoryType = itemCategoryType;
//        this.itemTitle = itemTitle;
//        this.itemDetailsText = itemDetailsText;
//        this.imageUrl = imageUrl;
//        this.participating = participating;
//        this.currentParticipants = currentParticipants;
//        this.targetParticipants = targetParticipants;
//        this.userId = userId;
//    }
//
//    public String getItemCategoryType() {
//        return itemCategoryType;
//    }
//
//    public void setItemCategoryType(String itemCategoryType) {
//        this.itemCategoryType = itemCategoryType;
//    }
//
//    public String getItemTitle() {
//        return itemTitle;
//    }
//
//    public void setItemTitle(String itemTitle) {
//        this.itemTitle = itemTitle;
//    }
//
//    public String getItemDetailsText() {
//        return itemDetailsText;
//    }
//
//    public void setItemDetailsText(String itemDetailsText) {
//        this.itemDetailsText = itemDetailsText;
//    }
//
//    public boolean isParticipating() {
//        return participating;
//    }
//
//    public void setParticipating(boolean participating) {
//        this.participating = participating;
//    }
//
//    public int getCurrentParticipants() {
//        return currentParticipants;
//    }
//
//    public void setCurrentParticipants(int currentParticipants) {
//        this.currentParticipants = currentParticipants;
//    }
//
//    public int getTargetParticipants() {
//        return targetParticipants;
//    }
//
//    public void setTargetParticipants(int targetParticipants) {
//        this.targetParticipants = targetParticipants;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//}

package com.example.manwon;

public class GongguItem_Model {
    private String key; // Firebase에서 항목의 고유 키를 저장하기 위한 필드
    private String itemCategoryType;
    private String itemTitle;
    private String itemDetailsText;
    private String imageUrl; // 이미지 URL
    private boolean participating;
    private int currentParticipants;
    private int targetParticipants;
    private String userId; // 게시물 작성자 ID

    public GongguItem_Model() {
        // Firebase 직렬화를 위해 기본생성자 필요
    }

    public GongguItem_Model(String itemCategoryType, String itemTitle, String itemDetailsText, String imageUrl, boolean participating, int currentParticipants, int targetParticipants, String userId) {
        this.itemCategoryType = itemCategoryType;
        this.itemTitle = itemTitle;
        this.itemDetailsText = itemDetailsText;
        this.imageUrl = imageUrl;
        this.participating = participating;
        this.currentParticipants = currentParticipants;
        this.targetParticipants = targetParticipants;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    // Getter와 Setter
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
