package com.cybindev.cvproject.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper
public interface ExperienciaResponseMapper {
  @Mappings({
      @Mapping(target = "puesto", source = "experiencia.puesto"),
      @Mapping(target = "empresa", source = "experiencia.empresa"),
      @Mapping(target = "descripcion", source = "experiencia.descripcion"),
      @Mapping(target = "fechaInicio", source = "experiencia.periodo", qualifiedByName = "mapFechaInicio"),
      @Mapping(target = "fechaFin", source = "experiencia.periodo", qualifiedByName = "mapFechaFinal")
  })
  ExperienciaResponseDTO toDto(Experiencia experiencia);

  @Named("mapFechaInicio")
  default String mapFechaInicio(String periodo) {
    // lógica para extraer la fecha de inicio del periodo
    String[] partesPeriodo = periodo.split(" - ");

    String[] fechaInicio = partesPeriodo[0].split("-");
    String mesInicio = fechaInicio[0];
    String anioInicio = fechaInicio[1];

    String[] fechaFinal = partesPeriodo[1].split("-");
    String mesFin = fechaFinal[0];
    String anioFin = fechaFinal[1];

    if (Integer.parseInt(mesInicio) < Integer.parseInt(mesFin)
        && Integer.parseInt(anioInicio) <= Integer.parseInt(anioFin)) {
      return partesPeriodo[0];
    }

    // en caso de error, retornar una fecha por defecto
    return new StringBuilder("01-1970").toString();
  }

  @Named("mapFechaFinal")
  default String mapFechaFinal(String periodo) {
    // lógica para extraer la fecha de inicio del periodo
    String[] partesPeriodo = periodo.split(" - ");

    String[] fechaInicio = partesPeriodo[0].split("-");
    String mesInicio = fechaInicio[0];
    String anioInicio = fechaInicio[1];

    String[] fechaFinal = partesPeriodo[1].split("-");
    String mesFin = fechaFinal[0];
    String anioFin = fechaFinal[1];

    if (Integer.parseInt(mesInicio) < Integer.parseInt(mesFin)
        && Integer.parseInt(anioInicio) <= Integer.parseInt(anioFin)) {
      return partesPeriodo[1];
    }

    // en caso de error, retornar una fecha por defecto
    return new StringBuilder("01-1970").toString();
  }
}
