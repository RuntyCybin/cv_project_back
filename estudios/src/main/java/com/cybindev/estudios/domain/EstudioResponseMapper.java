package com.cybindev.estudios.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface EstudioResponseMapper {

  @Mappings({
      // Map fields from entity to DTO
      @Mapping(target = "id", source = "entity.id"),
      @Mapping(target = "titulo", source = "entity.titulo"),
      @Mapping(target = "institucion", source = "entity.institucion"),
      @Mapping(target = "periodo", source = "entity.periodo"),
      @Mapping(target = "descripcion", source = "entity.descripcion"),
      @Mapping(target = "cursos", source = "entity.cursos")
  })
  EstudioResponseDTO toDto(Estudio entity);

}
