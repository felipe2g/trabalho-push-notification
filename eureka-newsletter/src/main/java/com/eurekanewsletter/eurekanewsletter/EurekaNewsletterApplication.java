package com.eurekanewsletter.eurekanewsletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaNewsletterApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaNewsletterApplication.class, args);
	}

}
