package com.cybindev.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface ProyectoRequestMapper {

  @Mappings({
          @Mapping(target = "id", ignore = true),
          @Mapping(target = "titulo", source = "dto.titulo"),
          @Mapping(target = "periodo", source = "dto.periodo"),
          @Mapping(target = "descripcion", source = "dto.descripcion"),
          @Mapping(target = "consultora", source = "dto.consultora"),
          @Mapping(target = "createdAt", ignore = true),
          @Mapping(target = "updatedAt", ignore = true)
  })
  Project toEntity(ProyectoRequestDTO dto);
}
