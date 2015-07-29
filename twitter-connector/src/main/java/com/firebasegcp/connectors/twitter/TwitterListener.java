package com.firebasegcp.connectors.twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.hbc.twitter4j.handler.StatusStreamHandler;
import com.twitter.hbc.twitter4j.message.DisconnectMessage;
import com.twitter.hbc.twitter4j.message.StallWarningMessage;
import org.apache.commons.configuration.Configuration;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

final class TwitterListener implements StatusStreamHandler {

    private Configuration cfg;

    public TwitterListener(Configuration cfg) throws Throwable {
        super();
        this.cfg = cfg;
    }

    @Override
    public void onStatus(Status status) {

        if (!status.isRetweet()) {
            try {
                Tweet tweet = new Tweet(status.getUser().getName(), status.getText());
                sendTweet(String.valueOf(status.getId()), tweet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Send PUT request to Firebase to create a new tweet there
     */
    private void sendTweet( String id, Tweet tweet ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = mapper.writeValueAsString( tweet );

        URL url = new URL("https://gds-2015.firebaseio.com/tweets/" + id + ".json");
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");
        httpCon.setRequestProperty("Content-Type", "application/json");
        OutputStreamWriter out = new OutputStreamWriter(
                httpCon.getOutputStream());
        out.write(jsonMessage);
        out.close();
        httpCon.getInputStream();
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
    }

    @Override
    public void onTrackLimitationNotice(int limit) {
    }

    @Override
    public void onScrubGeo(long user, long upToStatus) {
    }

    @Override
    public void onStallWarning(StallWarning warning) {
    }

    @Override
    public void onException(Exception e) {
    }

    @Override
    public void onDisconnectMessage(DisconnectMessage message) {
    }

    @Override
    public void onStallWarningMessage(StallWarningMessage warning) {
    }

    @Override
    public void onUnknownMessageType(String s) {
    }
}