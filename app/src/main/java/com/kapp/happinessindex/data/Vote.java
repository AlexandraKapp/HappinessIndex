package com.kapp.happinessindex.data;


public class Vote {

    private String hashtag;
    private String TeamName;
    private int vote;
    private long date;

    public Vote(String hashtag, String teamName, int vote, long date) {
        this.hashtag = hashtag;
        TeamName = teamName;
        this.vote = vote;
        this.date = date;
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
}
