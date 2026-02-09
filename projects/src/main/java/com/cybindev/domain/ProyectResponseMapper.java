package com.cybindev.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface ProyectResponseMapper {

  @Mappings({
          @Mapping(target = "id", source = "entity.id"),
          @Mapping(target = "titulo", source = "entity.titulo"),
          @Mapping(target = "periodo", source = "entity.periodo"),
          @Mapping(target = "descripcion", source = "entity.descripcion"),
          @Mapping(target = "consultora", source = "entity.consultora")
  })
  ProyectoResponseDTO toDto(Project entity);
}
