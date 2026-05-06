package com.cybindev.estudios.domain;

import org.springframework.stereotype.Component;

@Component
public class PersonaEstudioResponseMapper {
  public PersonaEstudioResponseDTO toDTO(PersonaEstudio entity) {
    if (entity == null) {
      return null;
    }
    return new PersonaEstudioResponseDTO(
        entity.getId(),
        entity.getPersonaId(),
        entity.getEstudioId());
  }
}
