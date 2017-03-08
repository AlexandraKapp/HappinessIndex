package com.kapp.happinessindex.data;


public class HashtagResult {
    private String hashtag;
    private int greenAmount;
    private int orangeAmount;
    private int redAmount;
    private int totalAmount;
    private long date;

    public HashtagResult(String hashtag, int greenAmount, int orangeAmount, int redAmount, int totalAmount, long date) {

        this.hashtag = hashtag;
        this.greenAmount = greenAmount;
        this.orangeAmount = orangeAmount;
        this.redAmount = redAmount;
        this.totalAmount = totalAmount;
        this.date = date;
    }

    public String getHashtag() {
        return hashtag;
    }

    public int getGreenAmount() {
        return greenAmount;
    }

    public int getOrangeAmount() {
        return orangeAmount;
    }

    public int getRedAmount() {
        return redAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public long getDate() {
        return date;
    }
}
