package com.cybindev.project.domain;

import org.springframework.stereotype.Component;

@Component
public class PersonaProyectoResponseMapper {
  public PersonaProyectoResponseDTO toDTO(PersonaProyecto entity) {
    if (entity == null) {
      return null;
    }
    return new PersonaProyectoResponseDTO(
        entity.getId(),
        entity.getPersonaId(),
        entity.getProyectoId());
  }
}
