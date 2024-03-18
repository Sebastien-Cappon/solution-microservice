package com.mediLaboSolutions.T2D2Practitioner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mediLaboSolutions.T2D2Practitioner.logger.HandlerInterceptorImpl;

/**
 * A configuration class that implements <code>WebMvcConfigurer</code>, a native
 * Spring MVC class. It contains a single method that set up the interceptor
 * needed to listen to API requests and responses.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {

	/**
	 * An override method that set up the interceptor needed to listen to API
	 * requests and responses.
	 * 
	 * @return <code>void</code>
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HandlerInterceptorImpl());
	}
}