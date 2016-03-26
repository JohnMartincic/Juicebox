package com.robbyduke.soundapp.player;

/**
 * Created by rduke on 3/26/16.
 */
public class Track {
    private final String title, stream_url;

    private long duration, id;
    private String artwork, artist;

    public Track(String title, String stream_url) {
        this.title = title;
        this.stream_url = stream_url;
    }

    public long id() {
        return id;
    }

    public void id(long id) {
        this.id = id;
    }

    public long duration() {
        return duration;
    }

    public void duration(long duration) {
        this.duration = duration;
    }

    public String artwork() {
        return artwork;
    }

    public void artwork(String artwork) {
        this.artwork = artwork;
    }

    public String artist() {
        return artist;
    }

    public void artist(String artist) {
        this.artist = artist;
    }

    public String title() {
        return title;
    }

    public String stream_url() {
        return stream_url;
    }
}
