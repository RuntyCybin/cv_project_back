package com.cybindev.cursos.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper
public interface CursosResponseMapper {

  @Mappings({
      // Map fields from entity to DTO
      @org.mapstruct.Mapping(target = "id", source = "entity.id"),
      @org.mapstruct.Mapping(target = "nombre", source = "entity.titulo"),
      @org.mapstruct.Mapping(target = "portal", source = "entity.portal"),
      @org.mapstruct.Mapping(target = "url", source = "entity.url"),
      @org.mapstruct.Mapping(target = "autor", source = "entity.autor"),
      @org.mapstruct.Mapping(target = "descripcion", source = "entity.descripcion"),
      @org.mapstruct.Mapping(target = "periodo", source = "entity.periodo")
  })
  CursoResponseDTO toDto(Curso entity);
}
