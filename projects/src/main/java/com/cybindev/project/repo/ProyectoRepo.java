package com.cybindev.project.repo;

import com.cybindev.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepo extends JpaRepository<Project, Long> {
}
