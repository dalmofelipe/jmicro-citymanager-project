package com.citymanager.Project.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	@Bean
	public WebClient webClientBudgets() {
		
		return WebClient.builder()
		        .baseUrl("http://localhost:8081")
		        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		        .build();	
	}
	
	@Bean
	public WebClient webClientSecretariat() {
		
		return WebClient.builder()
		        .baseUrl("http://localhost:8082")
		        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		        .build();	
	}
}
