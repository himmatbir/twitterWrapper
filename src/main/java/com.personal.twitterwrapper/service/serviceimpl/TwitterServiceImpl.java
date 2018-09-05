package com.personal.twitterwrapper.service.serviceimpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.personal.twitterwrapper.config.AppProperties;
import com.personal.twitterwrapper.exception.SystemException;
import com.personal.twitterwrapper.service.TwitterService;
import com.personal.twitterwrapper.utility.TwitterServiceConstants;
import com.personal.twitterwrapper.utility.TwitterServiceUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class TwitterServiceImpl implements TwitterService {
	private static final Logger logger = LoggerFactory.getLogger(TwitterService.class);
	private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private AppProperties appProperties;

	/**
	 * returns tweets for a given topic, controlled by limit
	 * If limit is not specified default limit is used
	 *
	 * @param topic
	 * @param limit
	 * @return
	 * @throws SystemException
	 */
	@Override
	public ObjectNode getTweetsPerTopic(String topic, String limit) throws SystemException {
		limit = limit != null ? limit : TwitterServiceConstants.DEFAULT_TWEETS_LIMIT;
		logger.info("Fetching tweets for topic - {} , with limit - {}", topic, limit);

		HttpHeaders headers = TwitterServiceUtilities.getTwitterRequestHeaders(appProperties, restTemplate);

		ObjectNode result = mapper.createObjectNode();

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(
				TwitterServiceUtilities.getSearchUrl(appProperties)).queryParam(TwitterServiceConstants.SEARCH_PARAMS, topic).queryParam(TwitterServiceConstants.LIMIT_COUNT, limit);

		RequestEntity<JsonNode> request =
				new RequestEntity<>(headers, HttpMethod.GET,
						uriBuilder.build().encode().toUri());

		logger.info("Call Url " + uriBuilder.build().toUriString());

		try {

			ResponseEntity<JsonNode> response = restTemplate.exchange(request, JsonNode.class);
			logger.info("Response {} ", response);

			if (response.getStatusCode().is2xxSuccessful()) {
				result.put(TwitterServiceConstants.TWEET_SEARCH_RESULTS, response.getBody().toString());
				result.put(TwitterServiceConstants.STATUS_CODE, TwitterServiceConstants.SUCCESS_CODE);
			} else {
				//TODO : uncomment it
				//throw new SystemException("Trends fetch resulted in non success - "+response.getStatusCodeValue());
				logger.error("Tweets fetch resulted in non success - " + response.getStatusCodeValue());
				result.put(TwitterServiceConstants.STATUS_CODE, TwitterServiceConstants.INTERNAL_SERVER_ERROR);

			}
		} catch (Exception e) {
			//throw new SystemException("Error occured while getting trends", e); //TWITTER DEV ACCOUNT NOT YET APPROVED
			/**
			 * TODO:
			 * Ideally exception will be thrown, below logging and result object shall be removed later
			 */
			logger.error("Error occured while getting tweets - Sending empty response");
			result.put(TwitterServiceConstants.TWEET_SEARCH_RESULTS, "Fetch was unsuccessful possibly due to non availability of twitter dev acc approval");
			result.put(TwitterServiceConstants.STATUS_CODE, TwitterServiceConstants.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	/**
	 * returns trending topics for a country
	 * If limit is not specified default limit is used
	 *
	 * @param country
	 * @param limit
	 * @return
	 * @throws SystemException
	 */
	@Override
	public ObjectNode getTrendingTopicsPerCountry(String country, String limit) throws SystemException {

		limit = limit != null ? limit : TwitterServiceConstants.DEFAULT_TRENDS_LIMIT;
		country = country != null ? country : TwitterServiceConstants.DEFAULT_COUNTRY;

		logger.info("Fetching trending topics for country - {} , with limit - {}", country, limit);


		HttpHeaders headers = TwitterServiceUtilities.getTwitterRequestHeaders(appProperties, restTemplate);
		ObjectNode result = mapper.createObjectNode();

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(
				TwitterServiceUtilities.getTrendsUrlForCountry(country, appProperties)).queryParam(TwitterServiceConstants.LIMIT_COUNT, limit);

		RequestEntity<JsonNode> request =
				new RequestEntity<>(headers, HttpMethod.GET,
						uriBuilder.build().encode().toUri());

		logger.info("Call Url " + uriBuilder.build()
				.toUriString());

		try {

			ResponseEntity<JsonNode> response = restTemplate.exchange(request, JsonNode.class);
			logger.info("Response {} ", response);

			if (response.getStatusCode().is2xxSuccessful()) {
				result.put(TwitterServiceConstants.TRENDING_TOPICS, response.getBody().toString());
				result.put(TwitterServiceConstants.STATUS_CODE, TwitterServiceConstants.SUCCESS_CODE);
			} else {
				//TODO : uncomment it
				// throw new SystemException("Trends fetch resulted in non success - "+response.getStatusCodeValue());
				logger.error("Trends fetch resulted in non success - " + response.getStatusCodeValue());
				result.put(TwitterServiceConstants.STATUS_CODE, TwitterServiceConstants.INTERNAL_SERVER_ERROR);

			}
		} catch (Exception e) {
			//throw new SystemException("Error occured while getting trends", e); //TODO: uncomment this afetr TWITTER DEV ACCOUNT IS APPROVED
			logger.error("Error occured while getting trends - Sending empty response");
			result.put(TwitterServiceConstants.TRENDING_TOPICS, "Fetch was unsuccessful possibly due to non availability of twitter dev acc approval");
			result.put(TwitterServiceConstants.STATUS_CODE, TwitterServiceConstants.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
}
