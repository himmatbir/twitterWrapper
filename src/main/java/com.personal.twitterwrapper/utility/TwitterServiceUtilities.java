package com.personal.twitterwrapper.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.twitterwrapper.config.AppProperties;
import com.personal.twitterwrapper.exception.SystemException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class TwitterServiceUtilities {
	private static final Logger logger = LoggerFactory.getLogger(TwitterServiceUtilities.class);

	/**
	 * get twitter search url
	 *
	 * @param appProperties
	 * @return
	 */
	public static String getSearchUrl(AppProperties appProperties) {
		return appProperties.getTwitterBaseUrl() + appProperties.getSearchUrl();
	}

	/**
	 * get twitter trending topics url
	 *
	 * @param appProperties
	 * @return
	 */
	public static String getTrendsUrl(AppProperties appProperties) {
		return getTrendsUrl(TwitterServiceConstants.WORLD_WOEID, appProperties); //No place specified, get it for the world
	}

	/**
	 * get twitter trending topics url
	 *
	 * @param woeId
	 * @param appProperties
	 * @return
	 */
	public static String getTrendsUrl(int woeId, AppProperties appProperties) {
		return appProperties.getTwitterBaseUrl() + String.format(appProperties.getTrendsUrl(), woeId);
	}

	/**
	 * get twitter trending topics url for a country
	 *
	 * @param country
	 * @param appProperties
	 * @return
	 */
	public static String getTrendsUrlForCountry(String country, AppProperties appProperties) {
		return getTrendsUrl(TwitterServiceConstants.COUNTRY_TO_WOEID_MAP.getOrDefault(country, TwitterServiceConstants.WORLD_WOEID), appProperties); //if country is not in map, get it for world
	}

	/**
	 * twitter access token
	 *
	 * @param appProperties
	 * @param restTemplate
	 * @return
	 * @throws SystemException
	 */
	public static String getTwitterAccessToken(AppProperties appProperties, RestTemplate restTemplate) throws SystemException {
		String encodedSecrets = encodeKeys(appProperties.getConsumerKey(), appProperties.getConsumerSecret());
		String accessToken = null;

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, TwitterServiceConstants.BASIC_AUTHORIZATION + encodedSecrets);

		UriComponentsBuilder uriBuilder =
				UriComponentsBuilder.fromUriString(
						appProperties.getTwitterBaseUrl() + appProperties.getTokenCreationUrl())
						.queryParam(TwitterServiceConstants.GRANT_TYPE, TwitterServiceConstants.CLIENT_CREDENTIALS);

		try {

			ResponseEntity<JsonNode> response =
					restTemplate.exchange(uriBuilder.build().encode().toUriString(), HttpMethod.POST,
							null, JsonNode.class);
			JsonNode jsonNode = response.getBody();
			accessToken = jsonNode.get(TwitterServiceConstants.ACCESS_TOKEN).asText();
		} catch (Exception e) { //catching generic Exception as this logic willnot work due to non approval of twitter dev account
			//throw new SystemException("Exception occured while generating access token for twitter", e); //commented it as twitter dev account access not yet approved to be able to generate tokens
			logger.error("Exception occured while generating access token for twitter");
			accessToken = "DUMMY_TOKEN";
		}
		return accessToken;
	}

	/**
	 * Encodes the consumer key and secret to create the basic authorization key
	 *
	 * @param consumerKey
	 * @param consumerSecret
	 * @return
	 */
	private static String encodeKeys(String consumerKey, String consumerSecret) {
		try {
			String encodedConsumerKey = URLEncoder.encode(consumerKey, TwitterServiceConstants.UTF_ENCODING);
			String encodedConsumerSecret = URLEncoder.encode(consumerSecret, TwitterServiceConstants.UTF_ENCODING);
			String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
			byte[] encodedBytes = Base64.encodeBase64(fullKey.getBytes());
			return new String(encodedBytes);
		} catch (UnsupportedEncodingException e) {
			return new String("NO_KEY");
		}
	}

	/**
	 * get HTTP Headers
	 *
	 * @param appProperties
	 * @param restTemplate
	 * @return
	 * @throws SystemException
	 */
	public static HttpHeaders getTwitterRequestHeaders(AppProperties appProperties, RestTemplate restTemplate) throws SystemException {
		String bearer;
		try {
			bearer = TwitterServiceConstants.BEARER_AUTHORIZATION + getTwitterAccessToken(appProperties, restTemplate);
		} catch (SystemException e) {
			throw new SystemException("Error occured while generating twitter access token ", e);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, bearer);
		return headers;
	}
}
