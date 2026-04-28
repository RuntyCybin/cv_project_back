package com.cybindev.cvproject.domain;

import org.springframework.stereotype.Component;

@Component
public class ExperienciaResponseMapper {
  public ExperienciaResponseDTO toDto(Experiencia experiencia) {
    if (experiencia == null) {
      return null;
    }

    return new ExperienciaResponseDTO(
        experiencia.getId(),
        experiencia.getPuesto(),
        experiencia.getEmpresa(),
        experiencia.getDescripcion(),
        mapFechaInicio(experiencia.getPeriodo()),
        mapFechaFinal(experiencia.getPeriodo()));
  }

  private String mapFechaInicio(String periodo) {
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

  private String mapFechaFinal(String periodo) {
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
