package com.akf.easyplay;

/**
 * Created by akfri on 02/13/18.
 */

public class Song {
    private final long ID;
    private final String title;
    private final String artist;

    public Song(long ID, String Title, String Artist) {
        this.ID = ID;
        this.title = Title;
        this.artist = Artist;
    }

    public long getID() { return ID; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
}
