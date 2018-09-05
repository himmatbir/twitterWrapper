package com.personal.twitterwrapper.config;

import com.personal.twitterwrapper.interceptor.TwitterWrapperInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private TwitterWrapperInterceptor twitterWrapperInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(twitterWrapperInterceptor)
				.addPathPatterns("/**");

	}

}
