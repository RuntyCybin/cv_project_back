package com.cybindev.domain;

import org.springframework.stereotype.Component;

@Component
public class ProyectoRequestMapper {

  public Project toEntity(ProyectoRequestDTO dto) {
    if (dto == null) {
      return null;
    }

    Project project = new Project();
    project.setTitulo(dto.titulo());
    project.setPeriodo(dto.periodo());
    project.setDescripcion(dto.descripcion());
    project.setConsultora(dto.consultora());
    return project;
  }
}
