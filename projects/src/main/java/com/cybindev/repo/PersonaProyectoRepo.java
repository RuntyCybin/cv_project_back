package com.cybindev.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybindev.domain.PersonaProyecto;

public interface PersonaProyectoRepo extends JpaRepository<PersonaProyecto, Long> {

  List<PersonaProyecto> findByPersonaId(Long personaId);

}
