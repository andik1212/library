package com.example.andik1212.share.twitter;

/**
 * Created with IntelliJ IDEA.
 * User: Andik
 * Date: 22.01.13
 * Time: 14:47
 * To change this template use File | Settings | File Templates.
 */
public class TwitterConstants {

    public static final String CONSUMER_KEY = "tA0Q1yUOgc2HL74WggHeiA";
    public static final String CONSUMER_SECRET= "IYLv7m0G8nh5fHd2YjIq3YRaEENlRHOw9NB6IcIPl4";

    public static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
    public static final String ACCESS_URL = "https://api.twitter.com/oauth/access_token";
    public static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";

//    public static final String OAUTH_CALLBACK_SCHEME	= "appfortwitter";
    public static final String OAUTH_CALLBACK_SCHEME	= "xchaplintwitter";
    public static final String OAUTH_CALLBACK_HOST	= "callback";
    public static final String OAUTH_CALLBACK_URL	=  OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
}
