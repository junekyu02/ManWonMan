//package com.example.manwon;
//
//public class ExchangeItem_Model {
//    private String itemCategoryType;  // 유형
//    private String itemTitle;         // 제목
//    private String itemDetailsText;   // 상세 내용
//    private int itemImageResId;       // 아이템 이미지 리소스 ID
//    private boolean isBookmarked;     // 찜하기 상태 (true/false)
//
//    // 생성자
//    public ExchangeItem_Model(String itemCategoryType, String itemTitle, String itemDetailsText, int itemImageResId, boolean isBookmarked) {
//        this.itemCategoryType = itemCategoryType;
//        this.itemTitle = itemTitle;
//        this.itemDetailsText = itemDetailsText;
//        this.itemImageResId = itemImageResId;
//        this.isBookmarked = isBookmarked;
//    }
//
//    // Getter and Setter
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
//    public int getItemImageResId() {
//        return itemImageResId;
//    }
//
//    public void setItemImageResId(int itemImageResId) {
//        this.itemImageResId = itemImageResId;
//    }
//
//    public boolean isBookmarked() {
//        return isBookmarked;
//    }
//
//    public void setBookmarked(boolean bookmarked) {
//        isBookmarked = bookmarked;
//    }
//}

package com.example.manwon;

public class ExchangeItem_Model {

    private String key; // Firebase 키
    private String region; // 지역 정보
    private String itemCategoryType; // 아이템 유형
    private String exchangeCategoryType; // 교환을 희망하는 물품 유형
    private String itemTitle; // 제목
    private String purchaseDate; // 구매일자
    private String damageRate; // 손상도
    private String details; // 상세 내용 (기타 사항)
    private String imageUrl; // 이미지 URL
    private String userId; // 사용자 ID
    private boolean isBookmarked; // 찜 상태

    // 기본 생성자 (Firebase 직렬화/역직렬화를 위해 필요)
    public ExchangeItem_Model() {
    }

    // 전체 필드를 포함한 생성자
    public ExchangeItem_Model(String region, String itemCategoryType, String exchangeCategoryType,
                              String itemTitle, String purchaseDate, String damageRate,
                              String details, String imageUrl, String userId, boolean isBookmarked) {
        this.region = region;
        this.itemCategoryType = itemCategoryType;
        this.exchangeCategoryType = exchangeCategoryType;
        this.itemTitle = itemTitle;
        this.purchaseDate = purchaseDate;
        this.damageRate = damageRate;
        this.details = details;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.isBookmarked = isBookmarked;
    }

    // Getter와 Setter
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getItemCategoryType() {
        return itemCategoryType;
    }

    public void setItemCategoryType(String itemCategoryType) {
        this.itemCategoryType = itemCategoryType;
    }

    public String getExchangeCategoryType() {
        return exchangeCategoryType;
    }

    public void setExchangeCategoryType(String exchangeCategoryType) {
        this.exchangeCategoryType = exchangeCategoryType;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getDamageRate() {
        return damageRate;
    }

    public void setDamageRate(String damageRate) {
        this.damageRate = damageRate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}
