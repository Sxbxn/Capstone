package com.example.capstoneconfigureserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CapstoneConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoneConfigServerApplication.class, args);
	}

}
