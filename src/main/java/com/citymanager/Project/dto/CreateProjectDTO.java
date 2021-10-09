package com.citymanager.Project.dto;

import com.citymanager.Project.entities.ProjectEntity;
import com.citymanager.Project.enums.FolderEnum;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CreateProjectDTO {

	private Long secretariatID;
	private Float cost;
	private String title;
	private String description;
	private FolderEnum folder;

	public ProjectEntity toEntity() {
		ProjectEntity projectEntity = new ProjectEntity();
		BeanUtils.copyProperties(this, projectEntity);
		return projectEntity;
	}
}
