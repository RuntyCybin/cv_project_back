package com.cybindev.experiencia.service;

import java.util.List;

import com.cybindev.experiencia.domain.ExperienciaResponseDTO;

public interface PersonaExperienciaService<O, I> {
  O crearPersonaExperiencia(I personaExperiencia);

  O obtenerPersonaExperienciaPorId(Long id);

  List<ExperienciaResponseDTO> obtenerExperienciasPorPersonaId(Long personaId);

}
