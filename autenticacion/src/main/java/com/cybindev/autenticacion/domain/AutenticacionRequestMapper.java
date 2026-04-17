package com.cybindev.autenticacion.domain;

import org.springframework.stereotype.Component;

@Component
public class AutenticacionRequestMapper {
  public Autenticacion toAutenticacion(AutenticacionRequestDTO autenticacionRequestDTO) {
    Autenticacion autenticacion = new Autenticacion();
    autenticacion.setLogin(autenticacionRequestDTO.login());
    autenticacion.setPassword(autenticacionRequestDTO.password());
    return autenticacion;
  }
}
