package com.cybindev.cvproject.domain;

import org.springframework.stereotype.Component;

@Component
public class PersonaExperienciaRequestMapper {
  public PersonaExperiencia toEntity(PersonaExperienciaRequestDTO requestDTO) {
    if (requestDTO == null) {
      return null;
    }
    PersonaExperiencia entity = new PersonaExperiencia();
    entity.setPersonaId(requestDTO.personaId());
    entity.setExperienciaId(requestDTO.experienciaId());
    return entity;
  }
}
