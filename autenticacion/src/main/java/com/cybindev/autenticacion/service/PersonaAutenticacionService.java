package com.cybindev.autenticacion.service;

public interface PersonaAutenticacionService<O, I> {
  O crearPersonaAutenticacion(I personaAutenticacion);

  O obtenerPersonaAutenticacionPorId(Long id);

  O obtenerPersonaAutenticacionPorPersonaId(Long personaId);

  O obtenerPersonaAutenticacionPorAutenticacionId(Long autenticacionId);

}
