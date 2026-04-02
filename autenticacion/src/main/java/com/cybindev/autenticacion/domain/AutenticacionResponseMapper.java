package com.cybindev.autenticacion.domain;

import org.springframework.stereotype.Component;

@Component
public class AutenticacionResponseMapper {
  public AutenticacionResponseDTO toResponse(Autenticacion autenticacion) {
    return new AutenticacionResponseDTO(
        autenticacion.getId(),
        autenticacion.getLogin(),
        autenticacion.getPassword());
  }
}
