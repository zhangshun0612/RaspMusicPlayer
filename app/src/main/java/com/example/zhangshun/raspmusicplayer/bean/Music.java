package com.example.zhangshun.raspmusicplayer.bean;

public class Music {
    private int id;
    private String title;
    private String album;
    private String artist;
    private String url;
    private int duration;
    private int size;

    public Music()
    {

    }

    public Music(int id, String title, String album, String artist, String url, int duration, int size) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.url = url;
        this.duration = duration;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
