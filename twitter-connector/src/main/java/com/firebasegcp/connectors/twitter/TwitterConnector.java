package com.firebasegcp.connectors.twitter;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.EnvironmentConfiguration;

import twitter4j.StatusListener;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.twitter.hbc.twitter4j.Twitter4jStatusClient;

public class TwitterConnector {

    
    private static Configuration cfg = new EnvironmentConfiguration();

    
    public static void oauth(String consumerKey, String consumerSecret, String token, String secret, String hashtag)
            throws Throwable {

        System.out.println( "Listening to hashtag " + hashtag );

        // Create an appropriately sized blocking queue
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(1000);

        // Define our endpoint: By default, delimited=length is set (we need
        // this for our processor)
        // and stall warnings are on.
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        String[] hashtagsToMonitor = new String[] { hashtag };
        
        List<String> terms = Lists.newArrayList(hashtagsToMonitor);
        endpoint.trackTerms(terms);
        
        Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);

        // Create a new BasicClient. By default gzip is enabled.
        BasicClient client = new ClientBuilder().hosts(Constants.STREAM_HOST).endpoint(endpoint).authentication(auth)
                .processor(new StringDelimitedProcessor(queue)).build();

        // Create an executor service which will spawn threads to do the actual
        // work of parsing the incoming messages and
        // calling the listeners on each message
        int numProcessingThreads = 4;
        ExecutorService service = Executors.newFixedThreadPool(numProcessingThreads);

        // Wrap our BasicClient with the twitter4j client
        StatusListener listener = new TwitterListener(cfg);
        Twitter4jStatusClient t4jClient = new Twitter4jStatusClient(client, queue, Lists.newArrayList(listener),
                service);

        // Establish a connection
        t4jClient.connect();
        for (int threads = 0; threads < numProcessingThreads; threads++) {
            // This must be called once per processing thread
            t4jClient.process();
        }

        Thread.sleep(600000);

        t4jClient.stop();
    }

    public static void main(String[] args) throws Throwable {
        try {

            String consumerKey;
            String consumerSecret;
            String token;
            String secret;
            String hashtag;

            if (args.length == 5) {
                consumerKey = args[0];
                consumerSecret = args[1];
                token = args[2];
                secret = args[3];
                hashtag = args[4];
            } else {
                consumerKey = cfg.getString("TWITTER_CONSUMER_KEY");
                consumerSecret = cfg.getString("TWITTER_CONSUMER_SECRET");
                token = cfg.getString("TWITTER_ACCESS_TOKEN");
                secret = cfg.getString("TWITTER_ACCESS_TOKEN_SECRET");
                hashtag = cfg.getString("TWITTER_HASHTAG");
            }
            
            TwitterConnector.oauth(consumerKey, consumerSecret, token, secret, hashtag);
            
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
