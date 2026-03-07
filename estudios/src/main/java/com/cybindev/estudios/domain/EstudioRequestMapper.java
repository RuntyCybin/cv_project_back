package com.cybindev.estudios.domain;

import org.springframework.stereotype.Component;

@Component
public class EstudioRequestMapper {

  public Estudio toEntity(EstudioRequestDTO dto) {
    if (dto == null) {
      return null;
    }

    Estudio estudio = new Estudio();
    estudio.setTitulo(dto.titulo());
    estudio.setInstitucion(dto.institucion());
    estudio.setPeriodo(dto.periodo());
    estudio.setDescripcion(dto.descripcion());
    estudio.setCursos(dto.cursos());
    return estudio;
  }
}
