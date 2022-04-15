package com.example.capstonediscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CapstoneDiscoveryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoneDiscoveryserviceApplication.class, args);
	}

}
