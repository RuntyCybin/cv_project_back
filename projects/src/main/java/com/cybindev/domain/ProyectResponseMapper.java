package com.cybindev.domain;

import org.springframework.stereotype.Component;

@Component
public class ProyectResponseMapper {

  public ProyectoResponseDTO toDto(Project entity) {
    if (entity == null) {
      return null;
    }

    return new ProyectoResponseDTO(
        entity.getId(),
        entity.getTitulo(),
        entity.getPeriodo(),
        entity.getDescripcion(),
        entity.getConsultora());
  }
}
