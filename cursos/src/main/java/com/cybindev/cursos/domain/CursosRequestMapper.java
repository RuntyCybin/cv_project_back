package com.cybindev.cursos.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface CursosRequestMapper {

  @Mappings({
      // Define your field mappings here
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "titulo", source = "dto.nombre"),
      @Mapping(target = "portal", source = "dto.portal"),
      @Mapping(target = "url", source = "dto.url"),
      @Mapping(target = "autor", source = "dto.autor"),
      @Mapping(target = "descripcion", source = "dto.descripcion"),
      @Mapping(target = "periodo", source = "dto.periodo"),
      @Mapping(target = "createdAt", ignore = true),
      @Mapping(target = "updatedAt", ignore = true)
  })
  Curso toEntity(CursoRequestDTO dto);

}
