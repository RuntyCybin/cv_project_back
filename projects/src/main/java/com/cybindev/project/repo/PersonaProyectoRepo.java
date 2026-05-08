package com.cybindev.project.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybindev.project.domain.PersonaProyecto;

public interface PersonaProyectoRepo extends JpaRepository<PersonaProyecto, Long> {

  List<PersonaProyecto> findByPersonaId(Long personaId);

}
