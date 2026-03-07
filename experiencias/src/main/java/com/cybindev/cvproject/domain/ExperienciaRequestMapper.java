package com.cybindev.cvproject.domain;

import org.springframework.stereotype.Component;

@Component
public class ExperienciaRequestMapper {

  public Experiencia toEntity(ExperienciaRequestDTO dto) {
    if (dto == null) {
      return null;
    }

    Experiencia experiencia = new Experiencia();
    experiencia.setPuesto(dto.puesto());
    experiencia.setEmpresa(dto.empresa());
    experiencia.setDescripcion(dto.descripcion());
    experiencia.setPeriodo(dto.periodo());
    return experiencia;
  }
}
