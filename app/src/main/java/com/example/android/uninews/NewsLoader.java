package com.example.android.uninews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsDetails>> {

    private String mUrl;

    public NewsLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsDetails> loadInBackground() {
        if (mUrl == null)
            return null;
        else {
            List<NewsDetails> newsDetails = QueryUtils.fetchEarthquakeData(mUrl);
            return newsDetails;
        }
    }

}
