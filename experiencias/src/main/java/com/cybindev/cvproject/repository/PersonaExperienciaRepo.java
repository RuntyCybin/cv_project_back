package com.cybindev.cvproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybindev.cvproject.domain.PersonaExperiencia;

public interface PersonaExperienciaRepo extends JpaRepository<PersonaExperiencia, Long> {
  List<PersonaExperiencia> findByPersonaId(Long personaId);

}
