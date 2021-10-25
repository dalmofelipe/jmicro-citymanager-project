package com.citymanager.Project.entities;

import com.citymanager.Project.enums.FolderEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tb_project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "secretariat_id")
    private Long secretariatID;

    private Float cost;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private FolderEnum folder;

    private Boolean approved = false;
}
