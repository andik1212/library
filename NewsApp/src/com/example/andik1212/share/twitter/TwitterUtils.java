package com.example.andik1212.share.twitter;

import android.content.SharedPreferences;
import oauth.signpost.OAuth;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * Created with IntelliJ IDEA.
 * User: Andik
 * Date: 28.01.13
 * Time: 2:27
 * To change this template use File | Settings | File Templates.
 */
public class TwitterUtils {

    public static boolean isAuthenticated(SharedPreferences prefs) {

        String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
        String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

        AccessToken a = new AccessToken(token,secret);
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(TwitterConstants.CONSUMER_KEY, TwitterConstants.CONSUMER_SECRET);
        twitter.setOAuthAccessToken(a);

        try {
            twitter.getAccountSettings();
            return true;
        } catch (TwitterException e) {
            return false;
        }
    }

    public static void sendTweet(SharedPreferences prefs,String msg) throws Exception {
        String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
        String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

        AccessToken a = new AccessToken(token,secret);
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(TwitterConstants.CONSUMER_KEY, TwitterConstants.CONSUMER_SECRET);
        twitter.setOAuthAccessToken(a);
        twitter.updateStatus(msg);
    }
}
