package com.mediLaboSolutions.T2D2Practitioner.config;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A configuration class that activates the <code>httpExchanges</code> actuator.
 * Since Spring 3, <code>httpTrace</code> no longer exists and has been replaced
 * by <code>httpEchanges</code>, which is not activated by default. A bean is
 * required for it to function properly.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
@Configuration
public class ActuatorConfig {

	/**
	 * The Bean required to run <code>httpExchanges</code>. Don't worry, this bean
	 * is not catastrophic, unlike the eponymous Mister. Running gag... Done it during all the course.
	 * I guess nobody read my Javadoc :(
	 * 
	 * Hellooooo ! Is there anyone here ?!
	 * Echo ! echo() ! Dammit, Old PHP habits are back !
	 * 
	 * @return <code>InMemoryHttpExchangeRepository()</code>
	 */
	@Bean
	HttpExchangeRepository httpTraceRepository() {
		return new InMemoryHttpExchangeRepository();
	}
}