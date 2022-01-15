package com.example.android.uninews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {
    /**
     * Return a list of {@link NewsDetails} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<NewsDetails> extractFeaturesFromJson(String earthquakeJSON) {

        if(TextUtils.isEmpty(earthquakeJSON)){
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        List<NewsDetails> news = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject rootObject = new JSONObject(earthquakeJSON);
            JSONArray newsDetailsArray = rootObject.getJSONArray("response");

            //looping through the features array to create separate objects
            for(int i = 0 ; i < newsDetailsArray.length(); i++){
                JSONObject newsSpecifics = newsDetailsArray.getJSONObject(i).getJSONObject("results");
                String title = newsSpecifics.getString("webTitle");
                String section = newsSpecifics.getString("sectionName");
                String date = newsSpecifics.getString("webPublicationDate");
                String url = newsSpecifics.getString("webUrl");

                NewsDetails tempNewsObject = new NewsDetails(title,section,date,url);
                news.add(tempNewsObject);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of news
        return news;
    }

    /**
     * Used to create URL from a String
     * @param url
     * @return URL object
     */
    private static URL createUrl(String url){
        URL created_url = null;
        try{
            created_url = new URL(url);
        }
        catch (MalformedURLException e){
            Log.e("QueryUtils", "createURL: error creating URL");
        }
        return created_url;
    }
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        if (url == null) {
            return jsonResponse;
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if (urlConnection.getResponseCode() != 200){
                Log.e("QueryUtils", "makeHttpRequest: Status Code = "+urlConnection.getResponseCode());
            }
            else {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }

        } catch (IOException e) {
            // TODO: Handle the exception
            Log.e("MainActivity", "makeHttpRequest: IOException is : "+e.toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
