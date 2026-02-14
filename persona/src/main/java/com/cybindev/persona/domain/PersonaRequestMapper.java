package com.cybindev.persona.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PersonaRequestMapper {
  DateTimeFormatter DMY_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "createdAt", ignore = true),
      @Mapping(target = "updatedAt", ignore = true),
      @Mapping(target = "fecha_nacimiento", source = "fecha_nacimiento", qualifiedByName = "toLocalDate"),
      @Mapping(target = "telefono", source = "telefono"),
      @Mapping(target = "email", source = "email"),
      @Mapping(target = "calle", source = "calle"),
      @Mapping(target = "via", source = "via"),
      @Mapping(target = "numero_casa", source = "numero_casa"),
      @Mapping(target = "codigo_postal", source = "codigo_postal"),
      @Mapping(target = "ciudad", source = "ciudad"),
      @Mapping(target = "provincia", source = "provincia"),
      @Mapping(target = "pais", source = "pais"),
      @Mapping(target = "nacionalidad", source = "nacionalidad")
  })
  Persona toPersona(PersonaRequestDTO personaRequestDTO);

  @Named("toLocalDate")
  default LocalDate toLocalDate(String fecha_nacimiento) {
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
