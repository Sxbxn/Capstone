package com.example.capstoneapigatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CapstoneApigatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoneApigatewayServiceApplication.class, args);
	}

//	@Bean
//	public HttpTraceRepository httpTraceRepository() {
//		return new InMemoryHttpTraceRepository();
//	}
}
