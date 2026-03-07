package com.cybindev.persona.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

@Component
public class PersonaRequestMapper {
  private static final DateTimeFormatter DMY_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public Persona toPersona(PersonaRequestDTO personaRequestDTO) {
    if (personaRequestDTO == null) {
      return null;
    }

    Persona persona = new Persona();
    persona.setNombre(personaRequestDTO.nombre());
    persona.setApellidos(personaRequestDTO.apellidos());
    persona.setFecha_nacimiento(toLocalDate(personaRequestDTO.fecha_nacimiento()));
    persona.setTelefono(personaRequestDTO.telefono());
    persona.setEmail(personaRequestDTO.email());
    persona.setCalle(personaRequestDTO.calle());
    persona.setVia(personaRequestDTO.via());
    persona.setNumero_casa(personaRequestDTO.numero_casa());
    persona.setCodigo_postal(personaRequestDTO.codigo_postal());
    persona.setCiudad(personaRequestDTO.ciudad());
    persona.setProvincia(personaRequestDTO.provincia());
    persona.setPais(personaRequestDTO.pais());
    persona.setNacionalidad(personaRequestDTO.nacionalidad());
    return persona;
  }

  public LocalDate toLocalDate(String fecha_nacimiento) {
    if (fecha_nacimiento == null || fecha_nacimiento.isBlank()) {
      return null;
    }

    String fecha = fecha_nacimiento.trim();
    try {
      return LocalDate.parse(fecha, DMY_FORMATTER);
    } catch (DateTimeParseException ignored) {
      try {
        return LocalDate.parse(fecha);
      } catch (DateTimeParseException ex) {
        throw new IllegalArgumentException(
            "Formato de fecha invalido. Use dd/MM/yyyy o yyyy-MM-dd: " + fecha_nacimiento, ex);
      }
    }
  }
}
