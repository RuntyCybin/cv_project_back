package com.cybindev.cvproject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExperienciaRequestMapper {

  default Experiencia convertDTOToExperiencia(ExperienciaRequestDTO experienciaRequestDTO) {
    if (experienciaRequestDTO == null) {
      return null;
    }

    Experiencia experiencia = new Experiencia();
    experiencia.setPuesto(experienciaRequestDTO.puesto());
    experiencia.setEmpresa(experienciaRequestDTO.empresa());
    experiencia.setDescripcion(experienciaRequestDTO.descripcion());
    experiencia.setPeriodo(normalizePeriodo(experienciaRequestDTO.periodo()));
    return experiencia;
  }

  private static String normalizePeriodo(String rawPeriodo) {
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
