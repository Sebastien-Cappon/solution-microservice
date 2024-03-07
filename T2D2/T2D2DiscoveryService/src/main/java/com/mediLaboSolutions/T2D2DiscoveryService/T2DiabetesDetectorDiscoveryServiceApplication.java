package com.mediLaboSolutions.T2D2DiscoveryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class T2DiabetesDetectorDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(T2DiabetesDetectorDiscoveryServiceApplication.class, args);
	}
}