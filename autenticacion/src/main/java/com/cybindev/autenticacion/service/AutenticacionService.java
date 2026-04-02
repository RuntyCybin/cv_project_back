package com.cybindev.autenticacion.service;

public interface AutenticacionService<O, I> {
  O crearAutenticacion(I autenticacion);

  O obtenerAutenticacionPorId(Long id);

  O obtenerAutenticacionPorLogin(String login);

  O obtenerAutenticacionPorLoginYPassword(I autenticacion);

}
