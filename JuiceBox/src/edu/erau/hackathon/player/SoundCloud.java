package edu.erau.hackathon.player;

import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Http;
import com.soundcloud.api.Request;
import com.soundcloud.api.Token;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * HackRiddle hackathon 03/26/2016
 * --
 * Team Members
 * - @author Robert Duke
 * - @author John Martincic
 * - @author Ryan Newitt
 * - @author Andy Artze
 * <p/>
 * This application is designed to replace the annoying web-only interface of SoundCloud on the desktop.
 */

public class SoundCloud {
    private static SoundCloud ourInstance = new SoundCloud("26933908e3bb4c32011c70341450961e", "30c9b5c29ae30a8b29b5c4920943183c", "", "");

    public static SoundCloud getInstance() {
        return ourInstance;
    }

    private final String client_id, client_secret, username, password;

    private final File wrapper_certificate = new File("wrapper.ser");
    private Token token;
    private ApiWrapper wrapper;

    private SoundCloud(String client_id, String client_secret, String username, String password) {
        this.client_id = client_id;
        this.client_secret = client_secret;

        this.username = username;
        this.password = password;

        try {
            renewCertificate();
        } catch (Exception ex) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).severe("Could not renew certificate.");
            ex.printStackTrace();
        }
    }

    public void renewCertificate() throws Exception {
        this.wrapper = new ApiWrapper(
                this.client_id,
                this.client_secret,
                null,   /* redirect URI */
                null    /* token */
        );

        this.wrapper.toFile(this.wrapper_certificate);

        if (!this.username.isEmpty() && !this.password.isEmpty()) {
            this.token = this.wrapper.login(this.username, this.password);
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Got token from SoundCloud: " + token);
        }
    }

    public List<Track> search(String term) {
        List<Track> filteredTracks = new ArrayList<Track>();

        JSONParser parser = new JSONParser();

        String requestURL = String.format("https://api.soundcloud.com/tracks?client_id=%s&q=%s&limit=200", client_id, term);
        final Request resource_stream = Request.to(requestURL);

        HttpResponse resp = null;

        try {
            resp = this.wrapper.get(resource_stream);

            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                try {
                    Object obj = parser.parse(Http.formatJSON(Http.getString(resp)));

                    JSONArray array = (JSONArray) obj;

                    for(int x = 0; x < array.size(); x++) {
                        JSONObject arrayObject = ((JSONObject) array.get(x));

                        if(!arrayObject.get("kind").equals("track") || !arrayObject.get("streamable").equals(true)) {
                            continue;
                        }

                        Track track = new Track((String) arrayObject.get("title"), (String) arrayObject.get("stream_url"));

                        track.artist((String) ((JSONObject) arrayObject.get("user")).get("username"));
                        track.artwork((String) arrayObject.get("artwork_url"));
                        track.duration((Long) arrayObject.get("duration"));
                        track.id((Long) arrayObject.get("id"));

                        filteredTracks.add(track);
                    }
                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
            } else
                System.err.println("Invalid status received: " + resp.getStatusLine());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return filteredTracks;
    }
}
