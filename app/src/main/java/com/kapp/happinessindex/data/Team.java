package com.kapp.happinessindex.data;

import java.util.ArrayList;

public class Team {
    private String teamName;
    private ArrayList<HashtagResult> hashTags;

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public Team(String teamName, ArrayList<HashtagResult> hashTags) {
        this.teamName = teamName;
        this.hashTags = hashTags;
    }

    /*
    Adds a new Hashtag to the Team.
    If the Hashtag already exists, nothing is done.

     */
    public void addHashTag(HashtagResult hashTag) {
        for (HashtagResult ht : hashTags) {
            if (hashTag.getHashtag().equals(hashTag.getHashtag())) {
                return;
            }
        }
        hashTags.add(hashTag);
    }

    /*
    Updates an existing Hashtag.
    If the Hashtag doesn't exists nothing is done.
     */
    public void updateHashTag(HashtagResult hashTag) {
        for (HashtagResult ht : hashTags) {
            if (hashTag.getHashtag().equals(hashTag.getHashtag())) {
                ht = hashTag;
                return;
            }
        }
    }
}
