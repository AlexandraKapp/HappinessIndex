package com.kapp.happinessindex.utilities;


import android.content.Context;
import android.util.Log;

import com.kapp.happinessindex.data.HashtagResult;
import com.kapp.happinessindex.data.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HappinessIndexUtils {

    public static ArrayList<Team> getTeamObjectsFromJson(Context context, String JsonString) throws JSONException {

        final String RESULTS = "results";
        final String TEAM = "team";
        final String TAGS = "tags";
        final String HASHTAG = "hashTag";
        final String GREEN = "green";
        final String ORANGE = "orange";
        final String RED = "red";
        final String TOTAL_VOTES = "total_votes";
        final String DATE = "date";

        //ERROR MESSAGE CODE?

        ArrayList<Team> parseTeamData;

        JSONObject teamsJson = new JSONObject(JsonString);

        //check if JsonString returns Error Code ??

        JSONArray teamArray = teamsJson.getJSONArray(RESULTS);
        parseTeamData = new ArrayList<Team>();

        String teamName;

        for (int i = 0; i < teamArray.length(); i++) {
            JSONObject teamObject = teamArray.getJSONObject(i);
            teamName = teamObject.getString(TEAM);
            Team team = new Team(teamName);

            JSONArray hashArray = teamObject.getJSONArray(TAGS);

            for (int j = 0; j < hashArray.length(); j++) {
                JSONObject hashObject = hashArray.getJSONObject(j);
                team.addHashTag(new HashtagResult(
                        hashObject.getString(HASHTAG),
                        hashObject.getInt(GREEN),
                        hashObject.getInt(ORANGE),
                        hashObject.getInt(RED),
                        hashObject.getInt(TOTAL_VOTES),
                        hashObject.getLong(DATE)
                ));
            }

            parseTeamData.add(team);
        }

        return parseTeamData;

    }

}
