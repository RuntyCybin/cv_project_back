package com.cybindev.cursos.domain;

import org.springframework.stereotype.Component;

@Component
public class PersonaCursoResponseMapper {
  public PersonaCursoResponseDTO toDto(PersonaCurso entity) {
    if (entity == null) {
      return null;
    }
    return new PersonaCursoResponseDTO(
        entity.getId(),
        entity.getPersonaId(),
        entity.getCursoId());
  }
}
