package com.cybindev.cursos.domain;

import org.springframework.stereotype.Component;

@Component
public class PersonaCursoRequestMapper {
  public PersonaCurso toEntity(PersonaCursoRequestDTO requestDTO) {
    if (requestDTO == null) {
      return null;
    }
    PersonaCurso entity = new PersonaCurso();
    entity.setPersonaId(requestDTO.personaId());
    entity.setCursoId(requestDTO.cursoId());
    return entity;
  }
}
