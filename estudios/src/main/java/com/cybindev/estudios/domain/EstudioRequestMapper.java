package com.cybindev.estudios.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface EstudioRequestMapper {

  @Mappings({
      // Ignore ID and timestamps for new entity creation
      // Map fields from DTO to entity
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "createdAt", ignore = true),
      @Mapping(target = "updatedAt", ignore = true),
      @Mapping(target = "titulo", source = "dto.titulo"),
      @Mapping(target = "institucion", source = "dto.institucion"),
      @Mapping(target = "periodo", source = "dto.periodo"),
      @Mapping(target = "descripcion", source = "dto.descripcion"),
      @Mapping(target = "cursos", source = "dto.cursos")
  })
  Estudio toEntity(EstudioRequestDTO dto);
}
