package com.personal.twitterwrapper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.personal.twitterwrapper.config.AppProperties;
import com.personal.twitterwrapper.service.serviceimpl.TwitterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WrapperController {

	private static Logger logger = LoggerFactory.getLogger(WrapperController.class);

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private TwitterServiceImpl twitterService;

	/**
	 * Generic health check endpoint
	 *
	 * @return
	 */
	@RequestMapping(value = "/twitterWrapper/health", method = RequestMethod.GET)
	public ResponseEntity<ObjectNode> health() {
		logger.info("Executing /twitterWrapper/health");

		ObjectNode statusNode = new ObjectMapper().createObjectNode();
		statusNode.put("status", "running");

		return ResponseEntity.ok(statusNode);
	}

	/**
	 * returns trending topics for a given country (default India), and given limit or default - 25
	 *
	 * @param country
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/twitterWrapper/trendingTopics", method = RequestMethod.GET)
	public ResponseEntity<ObjectNode> getTrendingTopics(@RequestParam(value = "country", required = false) String country, @RequestParam(value = "limit", required = false) String limit) {
		logger.info("Trending topics request received - Executing /twitterWrapper/trendingTopics");
		ObjectNode statusNode = null;
		try {
			statusNode = twitterService.getTrendingTopicsPerCountry(country, limit);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(statusNode);
	}

	/**
	 * returns tweets for a topic, with a given limit or default - 50
	 *
	 * @param topic
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/twitterWrapper/tweetsPerTopic", method = RequestMethod.GET)
	public ResponseEntity<ObjectNode> getTweetsPerTopic(@RequestParam(value = "topic") String topic, @RequestParam(value = "limit", required = false) String limit) {
		logger.info("Tweets per topic request received for topic {} - Executing /twitterWrapper/tweetsPerTopic", topic);
		ObjectNode statusNode = null;
		try {
			statusNode = twitterService.getTweetsPerTopic(topic, limit);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(statusNode);
	}
}
