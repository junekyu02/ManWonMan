package com.example.manwon;

public class CafeDessertItem {
    private String tag;
    private String title;
    private int imageResource;

    public CafeDessertItem(String tag, String title, int imageResource) {
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
