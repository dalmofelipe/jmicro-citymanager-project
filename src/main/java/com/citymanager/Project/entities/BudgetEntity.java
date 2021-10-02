package com.citymanager.Project.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import com.citymanager.Project.enums.FolderEnum;
import com.citymanager.Project.enums.OriginEnum;

import lombok.Data;

@Entity
@Data
@Table(name = "tb_budget")
public class BudgetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long id;

    @Column(name = "total_amount")
    private Float totalAmount;

    @Column(name = "spent_amount")
    private Float spentAmount;

    @ElementCollection(targetClass = FolderEnum.class)
    @JoinTable(name = "tb_folders", joinColumns = @JoinColumn(name = "budget_id"))
    @Column(name = "possible_destinations", nullable = true)
    @Enumerated(EnumType.STRING)
    Collection<FolderEnum> possibleDestinations;

    @Enumerated(EnumType.STRING)
    private OriginEnum origin;

}
