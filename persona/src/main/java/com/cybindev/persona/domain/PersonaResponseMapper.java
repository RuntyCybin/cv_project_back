package com.cybindev.persona.domain;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PersonaResponseMapper {

  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "nombre", source = "nombre"),
      @Mapping(target = "apellidos", source = "apellidos"),
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
  PersonaResponseDTO toDTO(Persona persona);

  @Named("toLocalDate")
  default LocalDate toLocalDate(String fecha_nacimiento) {
    return LocalDate.parse(fecha_nacimiento);
  }
}
