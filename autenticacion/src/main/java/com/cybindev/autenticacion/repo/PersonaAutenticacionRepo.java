package com.cybindev.autenticacion.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybindev.autenticacion.domain.PersonaAutenticacion;

public interface PersonaAutenticacionRepo extends JpaRepository<PersonaAutenticacion, Long> {
  Optional<PersonaAutenticacion> findByPersonaId(Long personaId);

  Optional<PersonaAutenticacion> findByAutenticacionId(Long autenticacionId);
}
