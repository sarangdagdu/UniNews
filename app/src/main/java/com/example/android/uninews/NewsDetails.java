package com.example.android.uninews;

public class NewsDetails {
    private String mTitle;
    private String mSection;
    private String mDate;
    private String mUrl;
    private String mAuthor;


    public NewsDetails(String mTitle, String mSection, String mDate, String mUrl, String mAuthor) {
        this.mTitle = mTitle;
        this.mSection = mSection;
        this.mDate = mDate;
        this.mUrl = mUrl;
        this.mAuthor = mAuthor;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmAuthor() {
        return mAuthor;
    }
}
