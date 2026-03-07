package com.cybindev.cursos.domain;

import org.springframework.stereotype.Component;

@Component
public class CursosResponseMapper {

  public CursoResponseDTO toDto(Curso entity) {
    if (entity == null) {
      return null;
    }

    return new CursoResponseDTO(
        entity.getId(),
        entity.getTitulo(),
        entity.getPortal(),
        entity.getUrl(),
        entity.getAutor(),
        entity.getDescripcion(),
        entity.getPeriodo());
  }
}
