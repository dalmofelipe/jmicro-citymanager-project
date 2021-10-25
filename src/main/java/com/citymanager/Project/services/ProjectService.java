package com.citymanager.Project.services;

import com.citymanager.Project.dto.CreateProjectDTO;
import com.citymanager.Project.entities.ProjectEntity;
import com.citymanager.Project.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
	
	private final ProjectRepository projectRepository;
	
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}
	
	public ProjectEntity create(CreateProjectDTO project) {
		ProjectEntity projectEntity = project.toEntity();
		return projectRepository.save(projectEntity);
	}

	public ProjectEntity create(ProjectEntity project) {
		return projectRepository.save(project);
	}

	public ProjectEntity getProject(Long id) {
		Optional<ProjectEntity> projectOpt = projectRepository.findById(id);

		if(projectOpt.isEmpty()){
			System.out.println("deu ruim, projeto não encontrado");
		}

		return projectOpt.get();
	}

	public List<ProjectEntity> listProjects() {
		return projectRepository.findAll();
	}

	public List<ProjectEntity> listProjects(Boolean approved) {
		return projectRepository.findApprovedParams(approved);
	}
}
