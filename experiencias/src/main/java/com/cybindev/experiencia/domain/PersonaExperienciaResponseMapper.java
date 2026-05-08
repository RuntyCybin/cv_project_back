package com.cybindev.experiencia.domain;

import org.springframework.stereotype.Component;

@Component
public class PersonaExperienciaResponseMapper {
  public PersonaExperienciaResponseDTO toDTO(PersonaExperiencia entity) {
    if (entity == null) {
      return null;
    }
    return new PersonaExperienciaResponseDTO(
        entity.getId(),
        entity.getPersonaId(),
        entity.getExperienciaId());
  }
}
