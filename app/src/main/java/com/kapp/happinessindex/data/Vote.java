package com.kapp.happinessindex.data;


public class Vote {

    private String hashtag;
    private String TeamName;
    private int vote;
    private long date;
    private double latitude;
    private double longitude;

    public Vote(String hashtag, String teamName, int vote, long date, double latitude, double longitude) {
        this.hashtag = hashtag;
        TeamName = teamName;
        this.vote = vote;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getHashtag() {
        return hashtag;
    }

    public String getTeamName() {
        return TeamName;
    }

    public int getVote() {
        return vote;
    }

    public long getDate() {
        return date;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
