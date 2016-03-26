package edu.erau.hackathon;

import edu.erau.hackathon.player.PlayerThread;
import edu.erau.hackathon.player.SoundCloud;
import edu.erau.hackathon.player.Track;
import javafx.application.Application;

import javax.media.Format;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rduke on 3/26/16.
 */
public class SoundBox extends GUI {
    public static PlayerThread playThread = new PlayerThread();
    public static List<Track> tracks = new ArrayList<Track>();


    public static void main(String[] args) throws Exception {

        SoundCloud.getInstance().renewCertificate();
        GUI.launch();

    }
}
