package com.example.manwon;

public class Gift_CafeDessertItem {
    private String tag;
    private String title;
    private int imageResource;

    public Gift_CafeDessertItem(String tag, String title, int imageResource) {
        this.tag = tag;
        this.title = title;
        this.imageResource = imageResource;
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }
}
