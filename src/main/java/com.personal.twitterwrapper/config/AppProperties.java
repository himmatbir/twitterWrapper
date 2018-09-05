package com.personal.twitterwrapper.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("com.personal.twitterwrapper")
public class AppProperties {
	private String twitterBaseUrl;
	private String trendsUrl;
	private String consumerKey;
	private String consumerSecret;
	private String searchUrl;
	private String tokenCreationUrl;
	private String whitelistedClientIds;

	public String getWhitelistedClientIds() {
		return whitelistedClientIds;
	}

	public void setWhitelistedClientIds(String whitelistedClientIds) {
		this.whitelistedClientIds = whitelistedClientIds;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}

	public String getConsumerKey() {

		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getTokenCreationUrl() {
		return tokenCreationUrl;
	}

	public void setTokenCreationUrl(String tokenCreationUrl) {
		this.tokenCreationUrl = tokenCreationUrl;
	}

	public String getTrendsUrl() {
		return trendsUrl;
	}

	public void setTrendsUrl(String trendsUrl) {
		this.trendsUrl = trendsUrl;
	}

	public String getTwitterBaseUrl() {
		return twitterBaseUrl;
	}

	public void setTwitterBaseUrl(String twitterBaseUrl) {
		this.twitterBaseUrl = twitterBaseUrl;
	}
}


