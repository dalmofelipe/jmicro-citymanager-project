package com.citymanager.Project.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.citymanager.Project.entities.BudgetEntity;

@Service
public class ProjectService {
	
	private WebClient webClientBudgets;
	private WebClient webClientSecretariat;
	
	public ProjectService(WebClient webClientBudgets, WebClient webClientSecretariat) {
		this.webClientBudgets = webClientBudgets;
		this.webClientSecretariat = webClientSecretariat;
	}
	
	public BudgetEntity getBudget(Long id) {
		
		String uri = new StringBuilder("/budgets/")
				.append(id)
				.toString();
		
		return this.webClientBudgets
				.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(BudgetEntity.class)
				.block();
	}

}
