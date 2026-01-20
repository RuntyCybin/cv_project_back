package com.cybindev.cvproject.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface ExperienciaRequestMapper {

  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "createdAt", ignore = true),
      @Mapping(target = "updatedAt", ignore = true),
      @Mapping(target = "puesto", source = "dto.puesto"),
      @Mapping(target = "empresa", source = "dto.empresa"),
      @Mapping(target = "descripcion", source = "dto.descripcion"),
      @Mapping(target = "periodo", source = "dto.periodo")
  })
  Experiencia toEntity(ExperienciaRequestDTO dto);

}
