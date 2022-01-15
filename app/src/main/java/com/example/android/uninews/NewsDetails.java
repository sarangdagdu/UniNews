package com.example.android.uninews;

public class NewsDetails {
    private String mTitle;
    private String mSection;
    private String mDate;
    private String mUrl;

    public String getmTitle() {
        return mTitle;
    }

    public NewsDetails(String mTitle, String mSection, String mDate, String mUrl) {
        this.mTitle = mTitle;
        this.mSection = mSection;
        this.mDate = mDate;
        this.mUrl = mUrl;
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
}
