package com.example.manwon;

public class ExchangeItem_Model {
    private String itemCategoryType;  // 유형
    private String itemTitle;         // 제목
    private String itemDetailsText;   // 상세 내용
    private int itemImageResId;       // 아이템 이미지 리소스 ID
    private boolean isBookmarked;     // 찜하기 상태 (true/false)

    // 생성자
    public ExchangeItem_Model(String itemCategoryType, String itemTitle, String itemDetailsText, int itemImageResId, boolean isBookmarked) {
        this.itemCategoryType = itemCategoryType;
        this.itemTitle = itemTitle;
        this.itemDetailsText = itemDetailsText;
        this.itemImageResId = itemImageResId;
        this.isBookmarked = isBookmarked;
    }

    // Getter and Setter
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

    public int getItemImageResId() {
        return itemImageResId;
    }

    public void setItemImageResId(int itemImageResId) {
        this.itemImageResId = itemImageResId;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}