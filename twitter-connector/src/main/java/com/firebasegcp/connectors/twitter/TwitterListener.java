package com.firebasegcp.connectors.twitter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import org.apache.commons.configuration.Configuration;

import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.URLEntity;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Sets;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.twitter.hbc.twitter4j.handler.StatusStreamHandler;
import com.twitter.hbc.twitter4j.message.DisconnectMessage;
import com.twitter.hbc.twitter4j.message.StallWarningMessage;

final class TwitterListener implements StatusStreamHandler {

    private Configuration cfg;

    public TwitterListener(Configuration cfg) throws Throwable {
        super();
        this.cfg = cfg;
    }

    @Override
    public void onStatus(Status status) {

        if (!status.isRetweet()) {
            System.out.println(status.getUser().getName());
            System.out.println(status.getText());
        }

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