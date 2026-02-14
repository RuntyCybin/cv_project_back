package com.cybindev.persona.service;

import java.util.List;

public interface PersonaService<O, I> {
  O crearPersona(I persona);

  O obtenerPersonaPorId(Long id);

  List<O> listarPersona();

}
