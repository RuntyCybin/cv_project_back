package com.cybindev.estudios.domain;

import org.springframework.stereotype.Component;

@Component
public class EstudioResponseMapper {

  public EstudioResponseDTO toDto(Estudio entity) {
    if (entity == null) {
      return null;
    }

    return new EstudioResponseDTO(
        entity.getId(),
        entity.getTitulo(),
        entity.getInstitucion(),
        entity.getPeriodo(),
        entity.getDescripcion(),
        entity.getCursos());
  }
}
