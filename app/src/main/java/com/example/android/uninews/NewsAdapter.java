package com.example.android.uninews;

import android.app.Activity;
import android.content.Context;
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
        newsInitialTextView.setText(currentNewsItem.getmTitle().toUpperCase(Locale.ROOT).charAt(0));

        TextView newsSectionTextView = listItemView.findViewById(R.id.section);
        newsSectionTextView.setText(currentNewsItem.getmSection());

        TextView newsBody = listItemView.findViewById(R.id.title);
        newsBody.setText(currentNewsItem.getmTitle());

        Date formattedDate = new Date(currentNewsItem.getmDate());
        String dateToDisplay = formatDate(formattedDate);

        TextView dateTextView = listItemView.findViewById(R.id.date);
        dateTextView.setText(dateToDisplay);

        String formattedTime = formatTime(formattedDate);
        TextView timeTextView = listItemView.findViewById(R.id.time);
        timeTextView.setText(formattedTime);

        return listItemView;
    }
    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
