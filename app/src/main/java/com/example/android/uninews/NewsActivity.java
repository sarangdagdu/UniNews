package com.example.android.uninews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsDetails>> {

    NewsAdapter adapter;
    TextView mEmptyStateTextView;
    private static final String GUARDIAN_URL = "https://content.guardianapis.com/search";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);
        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        int NEWS_LOADER_ID = 1;
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        // If there is a network connection, fetch data
        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);
        // Create a new {@link ArrayAdapter} of news
        adapter = new NewsAdapter(this, new ArrayList<NewsDetails>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(adapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsDetails currentNews = adapter.getItem(position);
                Uri newsURi = Uri.parse(currentNews.getmUrl());
                Intent visitUSGSIntent = new Intent(Intent.ACTION_VIEW, newsURi);
                startActivity(visitUSGSIntent);
            }
        });
    }

    @NonNull
    @Override
    public Loader<List<NewsDetails>> onCreateLoader(int id, @Nullable Bundle args) {

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(GUARDIAN_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. For example, the `format=json`
        uriBuilder.appendQueryParameter("format","json");
        uriBuilder.appendQueryParameter("api-key", "test");
        uriBuilder.appendQueryParameter("section","sport");

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsDetails>> loader, List<NewsDetails> news) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous earthquake data
        adapter.clear();
        mEmptyStateTextView.setText(R.string.no_news);

        // If there is a valid list of {@link NewsDetails}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            adapter.addAll(news);
        }
    }
        public void onLoaderReset(Loader<List<NewsDetails>> loader) {
        adapter.clear();
    }
}