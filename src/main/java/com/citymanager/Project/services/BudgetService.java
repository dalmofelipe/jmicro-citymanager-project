package com.citymanager.Project.services;

import com.citymanager.Project.dto.BudgetDTO;
import com.citymanager.Project.dto.ExpenseDTO;
import com.citymanager.Project.enums.FolderEnum;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetService {
	
	private final WebClient webClientBudgets;

	public BudgetService(WebClient webClientBudgets) {
		this.webClientBudgets = webClientBudgets;
	}
	
	public BudgetDTO getBudget(Long id) {
		String uri = "/budgets/" + id;
		
		return this.webClientBudgets
				.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(BudgetDTO.class)
				.block();
	}

	public List<BudgetDTO> filterBudgetsIn(List<FolderEnum> destinations) {
		String filters = listJoin(",", destinations);
		String uri = "/budgets?destinations=" + filters;

		Mono<List<BudgetDTO>> response = this.webClientBudgets.get()
				.uri(uri)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<BudgetDTO>>() {});

		List<BudgetDTO> readers = response.block();

		assert readers != null;
		return new ArrayList<>(readers);
	}

	public String listJoin(String delimiter, List<FolderEnum> list) {
		List<String> foldersStringList = list.stream()
				.map(FolderEnum::getValue)
				.collect(Collectors.toList());
		return String.join(delimiter, foldersStringList);
	}

	public void expenseBudget(Long id, ExpenseDTO expense) {
		String uri = "/budgets/" + id + "/expense";

		this.webClientBudgets
				.patch()
				.uri(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(expense), ExpenseDTO.class)
				.retrieve()
				.bodyToMono(Void.class)
				.block();
	}
}
