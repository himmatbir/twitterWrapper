package com.personal.twitterwrapper.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.personal.twitterwrapper.exception.SystemException;

public interface TwitterService {
	/**
	 * returns tweets for a given topic, controlled by limit
	 * If limit is not specified default limit is used
	 *
	 * @param topic
	 * @param limit
	 * @return
	 * @throws SystemException
	 */
	public ObjectNode getTweetsPerTopic(String topic, String limit) throws SystemException;

	/**
	 * returns trending topics for a country
	 * If limit is not specified default limit is used
	 *
	 * @param country
	 * @param limit
	 * @return
	 * @throws SystemException
	 */
	public ObjectNode getTrendingTopicsPerCountry(String country, String limit) throws SystemException;
}
