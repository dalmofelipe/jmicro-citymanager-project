package com.citymanager.Project.dto;

import com.citymanager.Project.enums.FolderEnum;

import lombok.Data;

@Data
public class SecretariatDTO {
    private Long id;
    private FolderEnum folder;
    private String secretary;
    private Float populationGrade;
    private Boolean underInvestigation;
}
