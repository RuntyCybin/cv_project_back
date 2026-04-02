package com.cybindev.autenticacion.domain;

import org.springframework.stereotype.Component;

@Component
public class PersonaAutenticacionRequestMapper {

  public PersonaAutenticacion toPersonaAutenticacion(PersonaAutenticacionRequestDTO request) {
    if (request == null) {
      return null;
    }
    PersonaAutenticacion personaAutenticacion = new PersonaAutenticacion();
    personaAutenticacion.setPersonaId(request.personaId());
    personaAutenticacion.setAutenticacionId(request.autenticacionId());
    return personaAutenticacion;
  }
}
