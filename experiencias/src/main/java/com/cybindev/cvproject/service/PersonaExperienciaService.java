package com.cybindev.cvproject.service;

import java.util.List;

public interface PersonaExperienciaService<O, I> {
  O crearPersonaExperiencia(I personaExperiencia);

  O obtenerPersonaExperienciaPorId(Long id);

  List<?> obtenerExperienciasPorPersonaId(Long personaId);

}
