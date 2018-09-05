package com.personal.twitterwrapper.interceptor;

import com.personal.twitterwrapper.config.AppProperties;
import com.personal.twitterwrapper.utility.TwitterServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TwitterWrapperInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(TwitterWrapperInterceptor.class);
	@Autowired
	private AppProperties appProperties;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
							 Object handler)
			throws IOException {
		String clientId = request.getHeader(TwitterServiceConstants.CLIENT_ID);

		if (clientId == null || !appProperties.getWhitelistedClientIds().contains(clientId)) {
			logger.info("Access denied! Client id {} not whitelisted!", clientId);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		return true;
	}
}
