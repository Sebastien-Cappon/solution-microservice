package com.mediLaboSolutions.T2D2DiscoveryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The main class of the application. It contains only the run method. This
 * project is a SpringBoot Application. This annotation is a merged of
 * <code>@EnableAutoConfiguration</code>, <code>@ComponentScan</code> and
 * <code>@Configuration</code> with their default configuration.
 *
 * @singularity This edge microservice contains only a main class. All the
 *              configuration of the service discovery is in the
 *              <code>application.properties</code> file. Why ? Because it can
 *              be externalized in a GitHub repository, edited and pushed
 *              on-the-fly thanks to <code>Spring Cloud
 *              Config</code> (not implemented in this project) and the actuator
 *              <code>/refresh</code>.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class T2DiabetesDetectorDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(T2DiabetesDetectorDiscoveryServiceApplication.class, args);
	}
}