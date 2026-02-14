package com.cybindev.cvproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybindev.cvproject.domain.Experiencia;

@Repository
public interface ExperienciaRepo extends JpaRepository<Experiencia, Long> {

}
