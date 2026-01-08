package com.cybindev.cvproject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ExperienciaRequestMapper {

  @Mapping(target = "puesto", source = "puesto")
  @Mapping(target = "empresa", source = "empresa")
  @Mapping(target = "descripcion", source = "descripcion")
  @Mapping(target = "periodo", source = "periodo", qualifiedByName = "normalizePeriodo")
  Experiencia convertDTOToExperiencia(ExperienciaRequestDTO experienciaRequestDTO);

  @Named("normalizePeriodo")
  default String normalizePeriodo(String rawPeriodo) {
    if (rawPeriodo == null) {
      return null;
    }

    Matcher matcher = Pattern.compile("(\\d{4}-\\d{2})").matcher(rawPeriodo);
    String fechaInicio = matcher.find() ? matcher.group(1) : null;
    String fechaFin = matcher.find() ? matcher.group(1) : null;

    if (fechaInicio != null && fechaFin != null) {
      return fechaInicio + " - " + fechaFin;
    }

    return rawPeriodo.trim();
  }
}
