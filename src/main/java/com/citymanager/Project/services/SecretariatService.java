package com.citymanager.Project.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.citymanager.Project.dto.SecretariatDTO;

@Service
public class SecretariatService {

	private WebClient webClientSecretariat;
	
	public SecretariatService(WebClient webClientSecretariat) {
		this.webClientSecretariat = webClientSecretariat;
	}
	
	public SecretariatDTO getSecretariat(Long id) {
		
		String uri = new StringBuilder("/secretariats/").append(id).toString();
		
		return this.webClientSecretariat
				.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(SecretariatDTO.class)
				.block();
	}
}
