package com.cybindev.cvproject.service;

import java.util.List;

import com.cybindev.cvproject.domain.ExperienciaResponseDTO;

public interface PersonaExperienciaService<O, I> {
  O crearPersonaExperiencia(I personaExperiencia);

  O obtenerPersonaExperienciaPorId(Long id);

  List<ExperienciaResponseDTO> obtenerExperienciasPorPersonaId(Long personaId);

}
