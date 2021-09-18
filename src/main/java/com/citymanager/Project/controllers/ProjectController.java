package com.citymanager.Project.controllers;

import com.citymanager.Project.entities.ProjectEntity;
import com.citymanager.Project.services.ProjectService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Um projeto deve pertencer à uma secretaria existente e que não esteja sob investigação,
    @PostMapping("/{id}/secretariat-id")
    public ProjectEntity create(@PathVariable Long secretariatId) {
        // 1. scretaria id, buscar pela secretaria e validar se ela não esta em investigação
        // 2. criar projeto, caso positivo
        return null;
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
}
