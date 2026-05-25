package com.cybindev.autenticacion.domain;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AutenticacionResponseMapper {
  @NonNull
  public AutenticacionResponseDTO toResponse(Autenticacion autenticacion) {
    return new AutenticacionResponseDTO(
        autenticacion.getId(),
        autenticacion.getLogin(),
        autenticacion.getPassword(),
        null);
  }

  @NonNull
  public AutenticacionResponseDTO toResponseConJwt(Autenticacion autenticacion, String jwt) {
    return new AutenticacionResponseDTO(
        autenticacion.getId(),
        autenticacion.getLogin(),
        null,
        jwt);
  }
}
