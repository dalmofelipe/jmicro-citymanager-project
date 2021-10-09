package com.citymanager.Project.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.citymanager.Project.dto.CreateProjectDTO;
import com.citymanager.Project.entities.ProjectEntity;
import com.citymanager.Project.repositories.ProjectRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
public class ProjectService {
	
	private ProjectRepository projectRepository;
	
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}
	
	public ProjectEntity create(@Validated @RequestBody CreateProjectDTO project) {
		ProjectEntity projectEntity = project.toEntity();
		return projectRepository.save(projectEntity);
	}
}
