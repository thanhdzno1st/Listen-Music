package com.example.listenmusic.model;

public class BaiHatOffline {
    private String title;
    private String artist;

    public BaiHatOffline(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }
}
