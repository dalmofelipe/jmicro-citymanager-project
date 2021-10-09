package com.citymanager.Project.dto;

import java.util.Collection;

import com.citymanager.Project.enums.FolderEnum;
import com.citymanager.Project.enums.OriginEnum;

import lombok.Data;

@Data
public class BudgetDTO {
    private Long id;
    private Float totalAmount;
    private Float spentAmount;
    Collection<FolderEnum> possibleDestinations;
    private OriginEnum origin;
}
