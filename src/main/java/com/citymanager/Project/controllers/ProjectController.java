package com.citymanager.Project.controllers;

import com.citymanager.Project.dto.ExpenseDTO;
import com.citymanager.Project.dto.SecretariatDTO;
import com.citymanager.Project.enums.FolderEnum;
import org.springframework.web.bind.annotation.*;

import com.citymanager.Project.dto.BudgetDTO;
import com.citymanager.Project.dto.CreateProjectDTO;
import com.citymanager.Project.entities.ProjectEntity;
import com.citymanager.Project.services.BudgetService;
import com.citymanager.Project.services.ProjectService;
import com.citymanager.Project.services.SecretariatService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	private final BudgetService budgetService;
    private final ProjectService projectService;
    private final SecretariatService secretariatService;

    public ProjectController(
    		BudgetService budgetService, 
    		ProjectService projectService, 
    		SecretariatService secretariatService) {
    	this.budgetService = budgetService;
        this.projectService = projectService;
        this.secretariatService = secretariatService;
    }

    // Um projeto deve pertencer à uma secretaria existente e que não esteja sob investigação,
    @PostMapping
    public ProjectEntity create(@RequestBody CreateProjectDTO projetoDto) {
        // 1. um projeto deve pertencer a uma secretaria existente e que nao esteja sob investigacao
        SecretariatDTO secretariat = secretariatService.getSecretariat(projetoDto.getSecretariatID());
    	if(secretariat == null) {
            System.out.println("Deu ruim, secretaria não encontrada!");
     		return null;
    	}
    	if(secretariat.getUnderInvestigation()) {
    		System.out.println("Deu ruim, secretaria esta sob investigação!");
    		return null;
    	}
        // 2. um projeto so pode ser aprovado caso haja orcamento disponivel para executa-lo
        // orcamente este que deve ser de uma pasta condizente com a do projeto
        FolderEnum folder = secretariat.getFolder();
    	List<FolderEnum> destinations = new ArrayList<>();
    	destinations.add(folder);

    	List<BudgetDTO> budgets = budgetService.filterBudgetsIn(destinations);
        Long idBudget = 0L;

    	for (BudgetDTO budget : budgets) {
            float totalAvailable = budget.getTotalAmount() - budget.getSpentAmount();

            // desconta o valor do projeto do primeiro orcamento entrado!
            if(totalAvailable > projetoDto.getCost()) {
                idBudget = budget.getId();
                break;
            } else  {
                System.out.printf("Deu ruim, o orçamento %d não possui saldo suficiente", budget.getId());
            }
        }

        // 3. o gasto com o projeto devera ser contabilizado do DB do MS de orcamento
        ExpenseDTO expenseDTO = new ExpenseDTO();
    	expenseDTO.setExpense(projetoDto.getCost());

        budgetService.expenseBudget(idBudget, expenseDTO);

        return projectService.create(projetoDto);
    }

    // Um projeto só pode ser aprovado caso haja orçamento disponível para executá-lo, orçamento este que deve ser de
    // uma pasta condizente com a do projeto;
    @GetMapping("/{id}/aproved")
    public void aprove() {
        // 1. modicar rota da api de secretaria para devolver um boolean informando se a pasta possui recursos para o projeto
        // ! aprovando a verba antes de confirmar a pasta !
        // 2. confirmar a pasta
        // 3 aprovar projeto
        return;
    }

    // O gasto com o projeto deverá ser contabilizado do DB do MS de Orçamento
    public void budgetExpense() {
        // 1. enviar PATH method o valor do projeto
        return;
    }
    
    @GetMapping("/{id}/budget")
    public BudgetDTO getBudget(@PathVariable Long id) {
    	return budgetService.getBudget(id);
    }
}
