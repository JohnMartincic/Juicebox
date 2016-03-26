package edu.erau.hackathon.player;

import javax.media.*;
import javax.media.format.AudioFormat;
import java.net.URL;

/**
 * HackRiddle hackathon 03/26/2016
 * --
 * Team Members
 * - @author Robert Duke
 * - @author John Martincic
 * - @author Ryan Newitt
 * - @author Andy Artze
 * <p>
 * This application is designed to replace the annoying web-only interface of SoundCloud on the desktop.
 */

public class PlayerThread {
    private Track track;
    private Player player = null;

    public Track track() {
        return track;
    }

    public void track(Track track) {
        if(player != null) {
            player.stop();
            player = null;
        }
        PlugInManager.addPlugIn(
                "com.sun.media.codec.audio.mp3.JavaDecoder",
                new Format[]{new AudioFormat(AudioFormat.MPEGLAYER3), new AudioFormat(AudioFormat.MPEG)},
                new Format[]{new AudioFormat(AudioFormat.LINEAR)},
                PlugInManager.CODEC
        );

        try {
            player = Manager.createPlayer(new MediaLocator(new URL(track.stream_url() + "?client_id=26933908e3bb4c32011c70341450961e")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.track = track;
        player.start();
    }


    public void pause() {
        player.stop();
    }

    public void start() {
        System.out.println("Now Playing: " + track.stream_url());
        player.start();
    }
}
