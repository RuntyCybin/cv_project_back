package com.cybindev.estudios.domain;

import org.springframework.stereotype.Component;

@Component
public class PersonaEstudioRequestMapper {
  public PersonaEstudio toEntity(PersonaEstudioRequestDTO requestDTO) {
    if (requestDTO == null) {
      return null;
    }
    PersonaEstudio entity = new PersonaEstudio();
    entity.setPersonaId(requestDTO.personaId());
    entity.setEstudioId(requestDTO.estudioId());
    return entity;
  }
}
