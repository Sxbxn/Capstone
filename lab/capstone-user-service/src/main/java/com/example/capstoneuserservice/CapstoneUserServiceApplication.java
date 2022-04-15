package com.example.capstoneuserservice;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CapstoneUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoneUserServiceApplication.class, args);
	}

	@Bean // psd 변환
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}
