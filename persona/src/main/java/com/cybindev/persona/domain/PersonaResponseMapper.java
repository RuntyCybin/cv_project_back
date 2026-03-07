package com.cybindev.persona.domain;

import org.springframework.stereotype.Component;

@Component
public class PersonaResponseMapper {

  public PersonaResponseDTO toDTO(Persona persona) {
    if (persona == null) {
      return null;
    }

    return new PersonaResponseDTO(
        persona.getId(),
        persona.getNombre(),
        persona.getApellidos(),
        persona.getFecha_nacimiento(),
        persona.getTelefono(),
        persona.getEmail(),
        persona.getCalle(),
        persona.getVia(),
        persona.getNumero_casa(),
        persona.getCodigo_postal(),
        persona.getCiudad(),
        persona.getProvincia(),
        persona.getPais(),
        persona.getNacionalidad());
  }
}
