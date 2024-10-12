package com.example.baitaphuy;

public class Song {
    private String name;
    private String artist;
    private int imageResId;
    private boolean isFavorite;

    public Song(String name, String artist, int imageResId, boolean isFavorite) {
        this.name = name;
        this.artist = artist;
        this.imageResId = imageResId;
        this.isFavorite = isFavorite;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}

