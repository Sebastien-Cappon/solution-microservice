package com.mediLaboSolutions.T2D2Diabetes.config;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;

public class ActuatorConfig {
	/**
	 * The Bean required to run <code>httpExchanges</code>. Don't worry, this bean
	 * is not catastrophic, unlike the eponymous Mister. Running gag... Did someone
	 * read the doc ? I know you don't ! Maybe for the first one of this huge
	 * project, not for the three...
	 * 
	 * @return <code>InMemoryHttpExchangeRepository()</code>
	 */
	@Bean
	HttpExchangeRepository httpTraceRepository() {
		return new InMemoryHttpExchangeRepository();
	}
}