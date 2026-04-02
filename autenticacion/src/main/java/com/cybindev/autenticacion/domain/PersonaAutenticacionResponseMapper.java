package com.cybindev.autenticacion.domain;

import org.springframework.stereotype.Component;

@Component
public class PersonaAutenticacionResponseMapper {
  public PersonaAutenticacionResponseDTO toPersonaAutenticacionResponseDTO(PersonaAutenticacion personaAutenticacion) {
    if (personaAutenticacion == null) {
      return null;
    }
    return new PersonaAutenticacionResponseDTO(
        personaAutenticacion.getId(),
        personaAutenticacion.getPersonaId(),
        personaAutenticacion.getAutenticacionId());
  }
}
