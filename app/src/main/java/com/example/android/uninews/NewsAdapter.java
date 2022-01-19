package com.example.android.uninews;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewsAdapter extends ArrayAdapter<NewsDetails> {
    private static final String LOCATION_SEPARATOR = "T";
    public NewsAdapter(@NonNull Context context, @NonNull ArrayList<NewsDetails> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        NewsDetails currentNewsItem = getItem(position);
          TextView newsInitialTextView = listItemView.findViewById(R.id.initial);
          newsInitialTextView.setText(currentNewsItem.getmTitle().charAt(0)+"");

        TextView newsSectionTextView = listItemView.findViewById(R.id.section);
        newsSectionTextView.setText(currentNewsItem.getmSection());

        TextView newsBody = listItemView.findViewById(R.id.title);
        newsBody.setText(currentNewsItem.getmTitle());

        String fullDateAndTime = currentNewsItem.getmDate();
        String dateString;
        // Check whether the originalLocation string contains the "T" text
        if (fullDateAndTime.contains(LOCATION_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the "T" text. We expect an array of 2 Strings, where
            // the first String will be Date and the second String will be Time.
            String[] parts = fullDateAndTime.split(LOCATION_SEPARATOR);
            // Date from the entire date and time String
            dateString = parts[0];

        } else {
            // Otherwise, there is no "T" text in the originalLocation string.
            // Hence, set the default date offset to blank.
            dateString = "";
        }

        TextView dateTextView = listItemView.findViewById(R.id.date);
        dateTextView.setText(dateString);

        String author = currentNewsItem.getmAuthor();
        TextView authorTextView = listItemView.findViewById(R.id.author);
        authorTextView.setText(author);

        return listItemView;
    }
}
