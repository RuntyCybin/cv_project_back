package com.cybindev.service;

import java.util.List;

public interface PersonaProjectsService<O, I> {
  O crearPersonaProject(I personaProject);

  O obtenerPersonaProjectPorId(Long id);

  List<?> obtenerProjectsPorPersonaId(Long personaId);

}
