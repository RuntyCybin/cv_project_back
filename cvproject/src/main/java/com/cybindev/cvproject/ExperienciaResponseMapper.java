package com.cybindev.cvproject;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ExperienciaResponseMapper {

  @Mapping(target = "fechaInicio", source = "periodo", qualifiedByName = "mapFechaInicio")
  @Mapping(target = "fechaFin", source = "periodo", qualifiedByName = "mapFechaFin")
  ExperienciaResponseDTO convertExperienciaToDTO(Experiencia experiencia);

  @Named("mapFechaInicio")
  default String mapFechaInicio(String periodo) {
    if (periodo == null || !periodo.contains("-")) {
      return null;
    }
    return periodo.split("-")[0].trim();
  }

  @Named("mapFechaFin")
  default String mapFechaFin(String periodo) {
    if (periodo == null || !periodo.contains("-")) {
      return null;
    }
    return periodo.split("-")[1].trim();
  }
}
