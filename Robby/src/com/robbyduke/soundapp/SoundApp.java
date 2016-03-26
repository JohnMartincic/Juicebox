package com.robbyduke.soundapp;

import com.robbyduke.soundapp.player.SoundCloud;
import com.robbyduke.soundapp.player.Track;

import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.bean.playerbean.MediaPlayer;
import javax.media.format.AudioFormat;
import javax.media.pim.PlugInManager;
import javax.swing.*;
import java.net.URL;
import java.util.List;

/**
 * HackRiddle hackathon 03/26/2016
 * --
 * Team Members
 * - @author Robert Duke
 * - @author John Martincic
 * - @author Ryan Newitt
 * - @author Andy Artze

 * This application is designed to replace the annoying web-only interface of SoundCloud on the desktop.
 */

public class SoundApp {
    public static void main(String[] args) {

        SoundCloud.getInstance();

        /**
         * Perform a search
         */
        final List<Track> tracks = SoundCloud.getInstance().search("G-Eazy");
        System.out.println("Found " + tracks.size() + " results.");

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(Track track : tracks) {
                    System.out.println("Now Playing: " + track.stream_url());
                    PlugInManager.addPlugIn(
                            "com.sun.media.codec.audio.mp3.JavaDecoder",
                            new Format[]{new AudioFormat(AudioFormat.MPEGLAYER3), new AudioFormat(AudioFormat.MPEG)},
                            new Format[]{new AudioFormat(AudioFormat.LINEAR)},
                            PlugInManager.CODEC
                    );
                    try {

                        Player player = Manager.createPlayer(new MediaLocator(new URL(track.stream_url() + "?client_id=26933908e3bb4c32011c70341450961e")));
                        player.start();
                        Thread.sleep(track.duration());
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }


                }
            }
        }).run();

        /**tracks = SoundCloud.getInstance().search("C-Trox");
        System.out.println("Found " + tracks.size() + " results.");

        tracks = SoundCloud.getInstance().search("Eminem");
        System.out.println("Found " + tracks.size() + " results.");

        tracks = SoundCloud.getInstance().search("Logic");
        System.out.println("Found " + tracks.size() + " results.");*/

    }
}