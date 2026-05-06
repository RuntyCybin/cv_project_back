package com.cybindev.domain;

import org.springframework.stereotype.Component;

@Component
public class PersonaProyectoRequestMapper {
  public PersonaProyecto toEntity(PersonaProyectoRequestDTO requestDTO) {
    if (requestDTO == null) {
      return null;
    }
    PersonaProyecto entity = new PersonaProyecto();
    entity.setPersonaId(requestDTO.personaId());
    entity.setProyectoId(requestDTO.proyectoId());
    return entity;
  }
}
