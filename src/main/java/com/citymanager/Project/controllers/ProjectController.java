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

        // orcamente este que deve ser de uma pasta condizente com a do projeto
        FolderEnum folderProject = projetoDto.getFolder();

    	if(!secretariat.getFolder().equals(folderProject)) {
            System.out.println("Deu ruim, O projeto não pertence a mesma pasta da secretaria!");
            return null;
        }

        return projectService.create(projetoDto);
    }

    @GetMapping
    public List<ProjectEntity> listProjects(@RequestParam(value = "approved", required = false) Boolean approved) {
        if(approved != null) {
            return projectService.listProjects(approved);
        }
        return projectService.listProjects();
    }

    // Um projeto só pode ser aprovado caso haja orçamento disponível para executá-lo, orçamento este que deve ser de
    // uma pasta condizente com a do projeto;
    @GetMapping("/{id}/aproved")
    public void aprove(@PathVariable Long id) {
        ProjectEntity project = projectService.getProject(id);
        if(project == null) {
            System.out.println("Projeto não encontrado!");
            return;
        }
        SecretariatDTO secretariatDTO = secretariatService.getSecretariat(project.getSecretariatID());
        if(secretariatDTO == null) {
            System.out.println("Secretaria do projeto não encontrado!");
            return;
        }

        List<FolderEnum> destinationsAvaliables = new ArrayList<>();
        destinationsAvaliables.add(secretariatDTO.getFolder());

        List<BudgetDTO> budgetAvaliables = budgetService.filterBudgetsIn(destinationsAvaliables);
        Float costProject = project.getCost();
        BudgetDTO budgetSelected = null;

        for(BudgetDTO budget : budgetAvaliables) {
            if((budget.getTotalAmount() - budget.getSpentAmount()) >= costProject) {
                budgetSelected = budget;
                break;
            }
        }
        if(budgetSelected == null) {
            System.out.println("deu ruim, nenhum orçamento disponível para aprovação desse projeto!");
            return;
        }

        ExpenseDTO expense = new ExpenseDTO();
        expense.setExpense(costProject);
        budgetService.expenseBudget(budgetSelected.getId(), expense);
        project.setApproved(true);
        projectService.create(project);
        System.out.printf("\nProjeto aprovado, no orçamento %d com o custo de %.2f\n", budgetSelected.getId(), costProject);
    }

    @GetMapping("/{id}/budget")
    public BudgetDTO getBudget(@PathVariable Long id) {
    	return budgetService.getBudget(id);
    }
}
