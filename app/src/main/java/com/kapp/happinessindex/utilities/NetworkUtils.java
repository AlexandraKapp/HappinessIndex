package com.kapp.happinessindex.utilities;


import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kapp.happinessindex.R;
import com.kapp.happinessindex.data.Team;
import com.kapp.happinessindex.data.Vote;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    //TODO: add server url
    final static String HAPPINESS_INDEX_SERVER = "";
    public final static String HAPPINESS_INDEX_POST_SERVER = "";

    final static String PARAM_TEAM = "team";
    final static String PARAM_HASHTAG = "hashtag";


    public static URL buildURL(String team, String hashtag) {

        Uri builtUri = Uri.parse(HAPPINESS_INDEX_SERVER).buildUpon()
                .appendQueryParameter(PARAM_TEAM, team)
                .appendQueryParameter(PARAM_HASHTAG, hashtag)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


    public static String POST(String urlString, Vote vote) {

        HttpURLConnection client = null;

        JSONObject jsonObjectVote = new JSONObject();
        JSONObject response = new JSONObject();

        try {
            URL url = new URL(urlString);

            jsonObjectVote.accumulate("team", vote.getTeamName());
            jsonObjectVote.accumulate("hashTag", vote.getHashtag());
            jsonObjectVote.accumulate("vote", vote.getVote());
            jsonObjectVote.accumulate("date", vote.getDate());

            client = (HttpURLConnection) url.openConnection();
            client.setDoOutput(true);
            client.setDoInput(true);
            client.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            client.setRequestMethod("POST");
            client.connect();

            OutputStreamWriter writer = new OutputStreamWriter(client.getOutputStream());
            String output = jsonObjectVote.toString();
            writer.write(output);
            writer.flush();
            writer.close();

            InputStream input = client.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            response = new JSONObject(result.toString());

            client.disconnect();

        } catch (JSONException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

}
