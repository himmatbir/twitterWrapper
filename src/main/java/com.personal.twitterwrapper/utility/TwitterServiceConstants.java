package com.personal.twitterwrapper.utility;

import java.util.HashMap;
import java.util.Map;

public class TwitterServiceConstants {

	//Twitter Wrapper Service Constants
	public static final String CLIENT_ID = "x-twtr-client-id";
	public static final String DEFAULT_TRENDS_LIMIT = "25";
	public static final String DEFAULT_TWEETS_LIMIT = "30";
	public static final int INDIA_WOEID = 23424848;
	public static final int WORLD_WOEID = 1;
	public static final String DEFAULT_COUNTRY = "India";
	public static final String UTF_ENCODING = "UTF-8";
	public static final String TRENDING_TOPICS = "trending_topics";
	public static final String STATUS_CODE = "status_code";
	public static final String TWEET_SEARCH_RESULTS = "search_results";
	public static final Map<String, Integer> COUNTRY_TO_WOEID_MAP;
	//Twitter APIs Keywords
	public static final String GRANT_TYPE = "grant_type";
	public static final String CLIENT_CREDENTIALS = "client_credentials";
	public static final String BASIC_AUTHORIZATION = "Basic ";
	public static final String BEARER_AUTHORIZATION = "Bearer ";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String LIMIT_COUNT = "count";
	public static final String SEARCH_PARAMS = "q";
	//Custom Error Codes
	public static final String SUCCESS_CODE = "1001";
	public static final String INTERNAL_SERVER_ERROR = "1002";
	public static final String AUTH_ERROR_CODE = "1003";

	static {
		COUNTRY_TO_WOEID_MAP = new HashMap<>();
		COUNTRY_TO_WOEID_MAP.put(DEFAULT_COUNTRY, INDIA_WOEID);
	}


}
