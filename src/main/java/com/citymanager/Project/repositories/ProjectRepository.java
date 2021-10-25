package com.citymanager.Project.repositories;

import com.citymanager.Project.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    @Override
    Optional<ProjectEntity> findById(Long id);

    @Query("FROM ProjectEntity p WHERE p.approved = :approved ORDER BY id ASC")
    List<ProjectEntity> findApprovedParams(@Param(value = "approved") Boolean approved);
}
