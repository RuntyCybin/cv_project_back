package com.cybindev.cursos.domain;

import org.springframework.stereotype.Component;

@Component
public class CursosRequestMapper {

  public Curso toEntity(CursoRequestDTO dto) {
    if (dto == null) {
      return null;
    }

    Curso curso = new Curso();
    curso.setTitulo(dto.nombre());
    curso.setPortal(dto.portal());
    curso.setUrl(dto.url());
    curso.setAutor(dto.autor());
    curso.setDescripcion(dto.descripcion());
    curso.setPeriodo(dto.periodo());
    return curso;
  }
}
