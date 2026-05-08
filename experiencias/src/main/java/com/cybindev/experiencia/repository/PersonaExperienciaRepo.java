package com.cybindev.experiencia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybindev.experiencia.domain.PersonaExperiencia;

public interface PersonaExperienciaRepo extends JpaRepository<PersonaExperiencia, Long> {
  List<PersonaExperiencia> findByPersonaId(Long personaId);

}
