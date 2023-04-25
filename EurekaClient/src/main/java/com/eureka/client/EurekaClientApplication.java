package com.eureka.client;

import com.eureka.client.controller.AyushmanController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaClientApplication {
	private static final Logger LOG = LoggerFactory.
			getLogger(EurekaClientApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(EurekaClientApplication.class, args);
		LOG.info("Application started......");
	}

}
