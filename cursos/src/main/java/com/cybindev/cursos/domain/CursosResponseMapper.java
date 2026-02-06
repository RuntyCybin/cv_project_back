package com.cybindev.cursos.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface CursosResponseMapper {

  @Mappings({
      // Map fields from entity to DTO
      @Mapping(target = "id", source = "entity.id"),
      @Mapping(target = "nombre", source = "entity.titulo"),
      @Mapping(target = "portal", source = "entity.portal"),
      @Mapping(target = "url", source = "entity.url"),
      @Mapping(target = "autor", source = "entity.autor"),
      @Mapping(target = "descripcion", source = "entity.descripcion"),
      @Mapping(target = "periodo", source = "entity.periodo")
  })
  CursoResponseDTO toDto(Curso entity);
}
